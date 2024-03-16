package com.casino.blackjack.config;

import com.casino.blackjack.model.enumerated.UserRoleEnum;
import com.casino.blackjack.repo.UserRepository;
import com.casino.blackjack.service.BlackjackUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class SecurityConfig {

//    private final String rememberMeKey;

//    public SecurityConfig(@Value("${user.remember-me-key}") String rememberMeKey) {
//        this.rememberMeKey = rememberMeKey;
//    }

    @Bean
    public PasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http // .csrf(AbstractHttpConfigurer::disable)
                // defines which pages will be authorized
                .authorizeHttpRequests((auth) -> {
                    auth
                            // allow access to all static locations defined in StaticResourceLocation enum class
                            // (images, css, js, webjars, etc.)
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            // the URLs below are available for all users - logged in and anonymous
                            .requestMatchers(
                                    "/",
                                    "/index",
                                    "/home",
                                    "/users/logout",
                                    "/users/login",
                                    "/auth/register",
                                    "/users/login-error").permitAll()
                            .anyRequest()
                            .authenticated();
                })
//                .formLogin(form -> {
//                    form
//                            .loginPage("/users/login")
//                            .loginProcessingUrl("/users/login")
//                            .failureForwardUrl("/users/login-error")
//                            // where to go after login (use true arg if we want to go there, otherwise go to prev page)
//                            .defaultSuccessUrl("/" /*,true*/) // arg alwaysUse: true
//                            // the names of the "username" and "password" input fields in the custom login form
//                            .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
//                            .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
//                            .permitAll();
//                })
//                .logout(logout -> {
//                    logout
//                            .logoutUrl("/users/logout")
//                            // go to home page after logout
//                            .logoutSuccessUrl("/")
//                            .deleteCookies("JSESSIONID")
//                            .clearAuthentication(true)
//                            .invalidateHttpSession(true)
//                            .permitAll();
//                })
                .securityContext(context -> {
                    context.securityContextRepository(securityContextRepository());
                })
//                .rememberMe(mem -> {
//                    mem
//                            .key(rememberMeKey)
//                            .tokenValiditySeconds(3600) // an hour
//                            .rememberMeParameter("remember-me-par")
//                            .rememberMeCookieName("remember-me-cookie");
//                    // https://docs.spring.io/spring-security/reference/servlet/authentication/rememberme.html
//                })
                .build();
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository) {
        return new BlackjackUserDetailsService(userRepository);
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

}
