package top.alexmmd.dog.starter.config;

import static org.springframework.security.config.Customizer.withDefaults;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import top.alexmmd.dog.starter.authentication.CustomAuthenticationFilterConfigurer;
import top.alexmmd.dog.starter.authorization.CustomAuthorizationManager;

/**
 * @author wangyonghui
 * @date 2022年11月30日 16:59:00
 */
@Configuration
@Slf4j
public class CustomSecurityFilterChainConfiguration {

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors(withDefaults())
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .anyRequest()
                                .access(new CustomAuthorizationManager())
                )
                .apply(new CustomAuthenticationFilterConfigurer<>())
                .loginProcessingUrl("/process")
                .successForwardUrl("/login/success")
                .failureForwardUrl("/login/failure");
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("wyh")
                .password("wyh")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}
