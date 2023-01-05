package top.alexmmd.dog.starter.authorization;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.alexmmd.common.security.jwt.JwtHelper;
import top.alexmmd.dog.domain.vo.UserVO;

/**
 * @author wangyonghui
 * @date 2023年01月05日 10:49:00
 */
@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token
        final String token = header.split(" ")[1].trim();

        // Get user and set it on the spring security context
        String decode = null;
        try {
            decode = JwtHelper.decode(token);
        } catch (Exception e) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            SecurityContextHolder.setContext(context);
            log.error("验证jwt失败", e);
            throw new BadCredentialsException("token校验失败");
        }
        UserVO userVO = JSONUtil.toBean(decode, UserVO.class);

        UsernamePasswordAuthenticationToken
                authentication = UsernamePasswordAuthenticationToken.authenticated(
                userVO, null,
                userVO.getRoleList()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

}
