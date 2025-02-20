package com.project.learningz.config;

import com.project.learningz.service.CustomOAuth2UserService;
import com.project.learningz.service.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.time.LocalDateTime;

@Configuration
public class WebSecurityConfig {
    private static final String[] STATIC_RESOURCE = {"/css/**", "/font/**", "/js/**", "/image/**"};
    private final LoginService customUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;

    public WebSecurityConfig(LoginService customUserDetailsService, CustomOAuth2UserService customOAuth2UserService) {
        this.customUserDetailsService = customUserDetailsService;
        this.customOAuth2UserService = customOAuth2UserService;
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
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/marketing/**").hasRole("MARKETING_TEAM")
                                .requestMatchers("/expert/**").hasRole("EXPERT")
                                .requestMatchers("/forgot_password").permitAll()
                                .requestMatchers("/reset_password").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/verify").permitAll()
                                .requestMatchers("/resend").permitAll()
                                .requestMatchers("/learning/**").hasAnyRole("VIP_STUDENT", "EXPERT", "ADMIN", "MARKETING_TEAM")
                                .requestMatchers("/course/**").permitAll()
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
    //old OAuth2Service, cannot user authorize, update at CustomOuth2User
    /*public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return new DefaultOAuth2UserService() {
            @Override
            public OAuth2User loadUser(OAuth2UserRequest userRequest) {
                OAuth2User oAuth2User = super.loadUser(userRequest);
                customUserDetailsService.processOAuthPostLogin(oAuth2User);
                return oAuth2User;
            }
        };
    }*/

}
