package com.project.learningz.service;


import com.project.learningz.config.CustomOAuth2User;
import com.project.learningz.constant.Role;
import com.project.learningz.entity.User;
import com.project.learningz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{

    private final UserRepository userRepository;
    private final LoginService customUserDetailsService;

    /*@Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        Set<GrantedAuthority> authorities = new HashSet<>();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        } else {
            Role role = user.getRole();
            String roleStr = "ROLE_" + role.toString();
            authorities.add(new SimpleGrantedAuthority(roleStr));
        }
        customUserDetailsService.processOAuthPostLogin(oAuth2User);
        return new CustomOAuth2User(oAuth2User, authorities);
    }*/
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        customUserDetailsService.processOAuthPostLogin(oAuth2User);
        User user = userRepository.findByEmail(email);
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (user != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        }

        return new CustomOAuth2User(oAuth2User, authorities);
    }
}
