package top.alexmmd.dog.starter.authentication.provider;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import top.alexmmd.dog.domain.dto.WechatUserInfoDTO;
import top.alexmmd.dog.domain.vo.UserVO;
import top.alexmmd.dog.service.IUserService;
import top.alexmmd.dog.service.IWechatService;
import top.alexmmd.dog.starter.authentication.token.WechatAuthenticationToken;

/**
 * @author wangyonghui
 * @date 2022年12月16日 09:09:00
 */
public class WechatAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        WechatAuthenticationToken wechatAuthenticationToken = (WechatAuthenticationToken) authentication;
        String code = wechatAuthenticationToken.getCode();
        // 通过code查询微信用户信息
        IWechatService wechatService = SpringUtil.getBean(IWechatService.class);
        WechatUserInfoDTO wechatUserInfoDTO = wechatService.wechatLogin(code);
        if (ObjectUtil.isNull(wechatUserInfoDTO)) {
            throw new AuthenticationServiceException("查询微信用户信息失败");
        }
        // 通过微信用户信息查询本系统的用户信息
        IUserService userService = SpringUtil.getBean(IUserService.class);
        UserVO userVO = userService.queryUserByAccount(wechatUserInfoDTO);
        if (ObjectUtil.isNull(userVO)) {
            throw new AuthenticationServiceException("根据openId查询用户信息失败");
        }
        return createSuccessAuthentication(userVO, wechatAuthenticationToken);
    }

    protected Authentication createSuccessAuthentication(UserVO userVO,
            Authentication authentication) {
        WechatAuthenticationToken result = WechatAuthenticationToken.authenticated(userVO,
                userVO.getRoleList()
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WechatAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
