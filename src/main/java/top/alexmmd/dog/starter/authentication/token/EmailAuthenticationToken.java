package top.alexmmd.dog.starter.authentication.token;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

/**
 * @author wangyonghui
 * @date 2022年12月16日 09:10:00
 */
public class EmailAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;

    private String email;

    private String code;

    public EmailAuthenticationToken(String code) {
        super(null);
        this.principal = null;
        this.code = code;
        setAuthenticated(false);
    }

    public EmailAuthenticationToken(String email, String code) {
        super(null);
        this.principal = null;
        this.email = email;
        this.code = code;
        setAuthenticated(false);
    }

    public EmailAuthenticationToken(Object principal,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    public static EmailAuthenticationToken unauthenticated(String email, String code) {
        return new EmailAuthenticationToken(email, code);
    }

    public static EmailAuthenticationToken authenticated(Object principal,
            Collection<? extends GrantedAuthority> authorities) {
        return new EmailAuthenticationToken(principal, authorities);
    }

    public String getCode() {
        return this.code;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

}
