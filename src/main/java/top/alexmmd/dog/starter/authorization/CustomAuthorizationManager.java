package top.alexmmd.dog.starter.authorization;

import static top.alexmmd.dog.starter.authorization.AuthContext.ANONYMOUS_USER;

import cn.hutool.core.util.StrUtil;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author wangyonghui
 * @date 2022年12月13日 10:40:00
 */
@Slf4j
public class CustomAuthorizationManager implements
        AuthorizationManager<RequestAuthorizationContext> {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(
            "/login/**");

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier,
            RequestAuthorizationContext requestAuthorizationContext) {
        log.info("进入动态权限管理");
        Collection<? extends GrantedAuthority> authorities = authenticationSupplier.get()
                .getAuthorities();
        Map<String, String> variables = requestAuthorizationContext.getVariables();
        HttpServletRequest request = requestAuthorizationContext.getRequest();

        if (!requiresAuthorization(request)) {
            return success();
        }

        if (StrUtil.equals(ANONYMOUS_USER, AuthContext.getName())) {
            return denied();
        }

        return success();
    }

    private boolean requiresAuthorization(HttpServletRequest request) {
        return !DEFAULT_ANT_PATH_REQUEST_MATCHER.matches(request);
    }

    private AuthorizationDecision success() {
        return new AuthorizationDecision(Boolean.TRUE);
    }

    private AuthorizationDecision denied() {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(context);
        return new AuthorizationDecision(Boolean.FALSE);
    }

}
