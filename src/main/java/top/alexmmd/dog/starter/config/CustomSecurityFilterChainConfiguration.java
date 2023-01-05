package top.alexmmd.dog.starter.config;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import top.alexmmd.common.base.http.response.BaseResponse;
import top.alexmmd.dog.starter.authentication.CustomAuthenticationFilterConfigurer;
import top.alexmmd.dog.starter.authorization.CustomAuthorizationManager;
import top.alexmmd.dog.starter.authorization.JwtTokenFilter;

/**
 * @author wangyonghui
 * @date 2022年11月30日 16:59:00
 */
@Configuration
@Slf4j
public class CustomSecurityFilterChainConfiguration {

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, JwtTokenFilter jwtTokenFilter)
            throws Exception {
        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Set permissions on endpoints
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .anyRequest()
                                .access(new CustomAuthorizationManager())
                ).apply(new CustomAuthenticationFilterConfigurer<>())
                .loginProcessingUrl("/process")
                .successForwardUrl("/login/success")
                .failureForwardUrl("/login/failure");

        // Add JWT token filter
        http.addFilterBefore(
                jwtTokenFilter,
                AuthorizationFilter.class
        );

        // 配置异常处理
        http.exceptionHandling((exceptionHandling) ->
                exceptionHandling.accessDeniedHandler((request, response, accessDeniedException) ->
                        {
                            response.setCharacterEncoding("UTF-8");
                            response.setContentType("application/json");
                            response.getWriter().println(JSONUtil.toJsonStr(new BaseResponse(
                                    HttpStatus.FORBIDDEN.value(),
                                    accessDeniedException.getMessage())));
                            response.getWriter().flush();
                        })
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setCharacterEncoding("UTF-8");
                            response.setContentType("application/json");
                            response.getWriter().println(JSONUtil.toJsonStr(new BaseResponse(
                                    HttpStatus.UNAUTHORIZED.value(),
                                    authException.getMessage())));
                            response.getWriter().flush();
                        }));
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
