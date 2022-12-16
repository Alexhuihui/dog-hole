package top.alexmmd.dog.starter.authorization;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

/**
 * @author wangyonghui
 * @date 2022年12月13日 10:40:00
 */
@Slf4j
public class CustomAuthorizationManager implements
        AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier,
            RequestAuthorizationContext requestAuthorizationContext) {
        Collection<? extends GrantedAuthority> authorities = authenticationSupplier.get()
                .getAuthorities();
        Map<String, String> variables = requestAuthorizationContext.getVariables();
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        log.info("进入动态权限管理");
        boolean isGranted = true;
        return new AuthorizationDecision(isGranted);
    }
}
