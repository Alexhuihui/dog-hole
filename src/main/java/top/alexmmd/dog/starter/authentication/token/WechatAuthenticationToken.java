package top.alexmmd.dog.starter.authentication.token;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

/**
 * @author wangyonghui
 * @date 2022年12月16日 09:10:00
 */
public class WechatAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;

    private String code;

    public WechatAuthenticationToken(String code) {
        super(null);
        this.principal = null;
        this.code = code;
        setAuthenticated(false);
    }

    public WechatAuthenticationToken(Object principal, String code) {
        super(null);
        this.principal = principal;
        this.code = code;
        setAuthenticated(false);
    }

    public WechatAuthenticationToken(Object principal,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    public static WechatAuthenticationToken unauthenticated(String wechatCode) {
        return new WechatAuthenticationToken(wechatCode);
    }

    public static WechatAuthenticationToken authenticated(Object principal,
            Collection<? extends GrantedAuthority> authorities) {
        return new WechatAuthenticationToken(principal, authorities);
    }

    public String getCode() {
        return this.code;
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
