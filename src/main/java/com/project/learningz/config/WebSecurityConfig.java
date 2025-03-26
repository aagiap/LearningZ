package com.project.learningz.config;

import com.project.learningz.service.CustomOAuth2UserService;
import com.project.learningz.service.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

import java.time.LocalDateTime;

@Configuration
public class WebSecurityConfig {
    private static final String[] STATIC_RESOURCE = {"/css/**", "/font/**", "/js/**", "/image/**","/vendor/**"};
    private final LoginService customUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;

    public WebSecurityConfig(LoginService customUserDetailsService, CustomOAuth2UserService customOAuth2UserService) {
        this.customUserDetailsService = customUserDetailsService;
        this.customOAuth2UserService = customOAuth2UserService;
    }
    @Bean
    public RequestCache requestCache() {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setRequestMatcher(new NegatedRequestMatcher(new AntPathRequestMatcher("/error/**")));
        return requestCache;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    LocalDateTime codeGenerationTime() {
        return LocalDateTime.now();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, CustomSuccessHandler customSuccessHandler) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers(STATIC_RESOURCE).permitAll()
                                .requestMatchers("/", "/login").permitAll()
                                .requestMatchers("/home/**").authenticated()
                                .requestMatchers("/comments/**").authenticated()
                                .requestMatchers("/admin/**").hasAnyRole("ADMIN", "ADMIN_USER_MANAGER", "ADMIN_COURSE_MANAGER")
                                .requestMatchers("/super_admin/**").hasAnyRole("ADMIN")
                                .requestMatchers("/marketer/**").hasRole("MARKETING_TEAM")
                                .requestMatchers("/teacher/**").hasRole("TEACHER")
                                .requestMatchers("/forgot_password").permitAll()
                                .requestMatchers("/reset_password").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/verify").permitAll()
                                .requestMatchers("/resend").permitAll()
                                .requestMatchers("/vip-packages").permitAll()
                                .requestMatchers("/learning/**").hasAnyRole("VIP_STUDENT", "TEACHER", "ADMIN", "MARKETING_TEAM", "ADMIN_USER_MANAGER", "ADMIN_COURSE_MANAGER")
                                .requestMatchers("/Exam/**").hasAnyRole("VIP_STUDENT", "TEACHER", "ADMIN", "MARKETING_TEAM", "ADMIN_USER_MANAGER", "ADMIN_COURSE_MANAGER")
                                .requestMatchers("/course/**").permitAll()
                                .requestMatchers("/post/**").permitAll()
                                .requestMatchers("/api/**").permitAll()
                                .anyRequest().authenticated()
                ).formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(customSuccessHandler)
                        .failureUrl("/login?error=true")
                        .permitAll()

                ).oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                );
        return http.build();
    }




}
