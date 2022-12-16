package top.alexmmd.dog.starter.authentication;

import java.util.Arrays;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import top.alexmmd.dog.starter.authentication.provider.WechatAuthenticationProvider;

/**
 * @author wangyonghui
 * @date 2022年12月15日 11:27:00
 */
public class CustomAuthenticationFilterConfigurer<H extends HttpSecurityBuilder<H>> extends
        AbstractHttpConfigurer<CustomAuthenticationFilterConfigurer<H>, H> {

    private CustomAuthenticationFilter authFilter;

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;

    private SavedRequestAwareAuthenticationSuccessHandler defaultSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();

    private AuthenticationSuccessHandler successHandler = this.defaultSuccessHandler;

    private LoginUrlAuthenticationEntryPoint authenticationEntryPoint;

    private boolean customLoginPage;

    private String loginPage;

    private String loginProcessingUrl;

    private AuthenticationFailureHandler failureHandler;

    private boolean permitAll;

    private String failureUrl;

    /**
     * Creates a new instance with minimal defaults
     */
    public CustomAuthenticationFilterConfigurer() {
        setLoginPage("/login");
        this.authFilter = new CustomAuthenticationFilter();
    }

    public final CustomAuthenticationFilterConfigurer<H> defaultSuccessUrl(
            String defaultSuccessUrl) {
        return defaultSuccessUrl(defaultSuccessUrl, false);
    }

    public final CustomAuthenticationFilterConfigurer<H> defaultSuccessUrl(String defaultSuccessUrl,
            boolean alwaysUse) {
        SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl(defaultSuccessUrl);
        handler.setAlwaysUseDefaultTargetUrl(alwaysUse);
        this.defaultSuccessHandler = handler;
        return successHandler(handler);
    }

    public CustomAuthenticationFilterConfigurer<H> loginProcessingUrl(String loginProcessingUrl) {
        this.loginProcessingUrl = loginProcessingUrl;
        this.authFilter.setRequiresAuthenticationRequestMatcher(
                createLoginProcessingUrlMatcher(loginProcessingUrl));
        return getSelf();
    }

    public CustomAuthenticationFilterConfigurer<H> securityContextRepository(
            SecurityContextRepository securityContextRepository) {
        this.authFilter.setSecurityContextRepository(securityContextRepository);
        return getSelf();
    }

    public RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, "POST");
    }

    public final CustomAuthenticationFilterConfigurer<H> authenticationDetailsSource(
            AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        this.authenticationDetailsSource = authenticationDetailsSource;
        return getSelf();
    }

    public final CustomAuthenticationFilterConfigurer<H> successHandler(
            AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
        return getSelf();
    }

    public final CustomAuthenticationFilterConfigurer<H> permitAll() {
        return permitAll(true);
    }

    public final CustomAuthenticationFilterConfigurer<H> permitAll(boolean permitAll) {
        this.permitAll = permitAll;
        return getSelf();
    }

    public final CustomAuthenticationFilterConfigurer<H> failureUrl(
            String authenticationFailureUrl) {
        CustomAuthenticationFilterConfigurer<H> result = failureHandler(
                new SimpleUrlAuthenticationFailureHandler(authenticationFailureUrl));
        this.failureUrl = authenticationFailureUrl;
        return result;
    }

    public final CustomAuthenticationFilterConfigurer<H> failureHandler(
            AuthenticationFailureHandler authenticationFailureHandler) {
        this.failureUrl = null;
        this.failureHandler = authenticationFailureHandler;
        return getSelf();
    }

    public CustomAuthenticationFilterConfigurer<H> failureForwardUrl(String forwardUrl) {
        failureHandler(new ForwardAuthenticationFailureHandler(forwardUrl));
        return getSelf();
    }

    public CustomAuthenticationFilterConfigurer<H> successForwardUrl(String forwardUrl) {
        successHandler(new ForwardAuthenticationSuccessHandler(forwardUrl));
        return getSelf();
    }

    @Override
    public void init(H http) throws Exception {
        registerDefaultAuthenticationEntryPoint(http);
        initProvider(http);
    }

    private void initProvider(H http) {
        ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
        // 初始化 Provider
        WechatAuthenticationProvider wechatAuthenticationProvider = new WechatAuthenticationProvider();
        http.authenticationProvider(wechatAuthenticationProvider);
    }

    @SuppressWarnings("unchecked")
    protected final void registerDefaultAuthenticationEntryPoint(H http) {
        registerAuthenticationEntryPoint(http, this.authenticationEntryPoint);
    }

    @SuppressWarnings("unchecked")
    protected final void registerAuthenticationEntryPoint(H http,
            AuthenticationEntryPoint authenticationEntryPoint) {
        ExceptionHandlingConfigurer<H> exceptionHandling = http.getConfigurer(
                ExceptionHandlingConfigurer.class);
        if (exceptionHandling == null) {
            return;
        }
        exceptionHandling.defaultAuthenticationEntryPointFor(postProcess(authenticationEntryPoint),
                getAuthenticationEntryPointMatcher(http));
    }

    protected final RequestMatcher getAuthenticationEntryPointMatcher(H http) {
        ContentNegotiationStrategy contentNegotiationStrategy = http.getSharedObject(
                ContentNegotiationStrategy.class);
        if (contentNegotiationStrategy == null) {
            contentNegotiationStrategy = new HeaderContentNegotiationStrategy();
        }
        MediaTypeRequestMatcher mediaMatcher = new MediaTypeRequestMatcher(
                contentNegotiationStrategy,
                MediaType.APPLICATION_XHTML_XML, new MediaType("image", "*"), MediaType.TEXT_HTML,
                MediaType.TEXT_PLAIN);
        mediaMatcher.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
        RequestMatcher notXRequestedWith = new NegatedRequestMatcher(
                new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest"));
        return new AndRequestMatcher(Arrays.asList(notXRequestedWith, mediaMatcher));
    }

    @Override
    public void configure(H http) throws Exception {
        PortMapper portMapper = http.getSharedObject(PortMapper.class);
        if (portMapper != null) {
            this.authenticationEntryPoint.setPortMapper(portMapper);
        }
        RequestCache requestCache = http.getSharedObject(RequestCache.class);
        if (requestCache != null) {
            this.defaultSuccessHandler.setRequestCache(requestCache);
        }
        this.authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        this.authFilter.setAuthenticationSuccessHandler(this.successHandler);
        this.authFilter.setAuthenticationFailureHandler(this.failureHandler);
        if (this.authenticationDetailsSource != null) {
            this.authFilter.setAuthenticationDetailsSource(this.authenticationDetailsSource);
        }
        CustomAuthenticationFilter filter = postProcess(this.authFilter);
        http.addFilterBefore(filter, LogoutFilter.class);
    }

    protected CustomAuthenticationFilterConfigurer<H> loginPage(String loginPage) {
        setLoginPage(loginPage);
        this.customLoginPage = true;
        return getSelf();
    }

    public final boolean isCustomLoginPage() {
        return this.customLoginPage;
    }

    /**
     * Gets the Authentication Filter
     *
     * @return the Authentication Filter
     */
    protected final CustomAuthenticationFilter getAuthenticationFilter() {
        return this.authFilter;
    }

    /**
     * Sets the Authentication Filter
     *
     * @param authFilter the Authentication Filter
     */
    protected final void setAuthenticationFilter(CustomAuthenticationFilter authFilter) {
        this.authFilter = authFilter;
    }

    protected final String getLoginPage() {
        return this.loginPage;
    }

    protected final AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return this.authenticationEntryPoint;
    }

    protected final String getLoginProcessingUrl() {
        return this.loginProcessingUrl;
    }

    protected final String getFailureUrl() {
        return this.failureUrl;
    }

    private void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
        this.authenticationEntryPoint = new LoginUrlAuthenticationEntryPoint(loginPage);
    }

    private CustomAuthenticationFilterConfigurer<H> getSelf() {
        return this;
    }

}
