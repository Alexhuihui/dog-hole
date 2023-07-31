package top.alexmmd.dog.starter.authentication.provider;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import top.alexmmd.dog.domain.vo.UserVO;
import top.alexmmd.dog.service.IUserService;
import top.alexmmd.dog.starter.authentication.token.EmailAuthenticationToken;

/**
 * @author wangyonghui
 * @date 2022年12月16日 09:09:00
 */
@Slf4j
public class EmailAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        EmailAuthenticationToken emailAuthenticationToken = (EmailAuthenticationToken) authentication;
        String code = emailAuthenticationToken.getCode();
        String email = emailAuthenticationToken.getEmail();
        log.info("用户{}登录{}", email, code);
        // 通过微信用户信息查询本系统的用户信息
        IUserService userService = SpringUtil.getBean(IUserService.class);
        UserVO userVO = userService.queryUserByAccount(email);
        if (ObjectUtil.isNull(userVO)) {
            userService.emailRegister(email);
            userVO = userService.queryUserByAccount(email);
        }
        return createSuccessAuthentication(userVO, emailAuthenticationToken);
    }

    protected Authentication createSuccessAuthentication(UserVO userVO,
            Authentication authentication) {
        EmailAuthenticationToken result = EmailAuthenticationToken.authenticated(userVO,
                userVO.getRoleList()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()));
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
