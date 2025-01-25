package com.project.learningz.service;

import com.project.learningz.entity.User;
import com.project.learningz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = findUserByUsernameOrEmail(usernameOrEmail);
        if (user.getPassword() == null) {
            return createOAuthUserDetails(user);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

    public void processOAuthPostLogin(OAuth2User oAuth2User) {
        String googleId = oAuth2User.getAttribute("sub");
        User user = userRepository.findByGoogleId(googleId).orElseGet(() -> createNewUser(oAuth2User));

        updateProfilePicture(user, oAuth2User.getAttribute("picture"));
        UserDetails userDetails = createOAuthUserDetails(user);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        );
    }

    private User findUserByUsernameOrEmail(String usernameOrEmail) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByUsername(usernameOrEmail))
                .orElseGet(() -> userRepository.findByEmail(usernameOrEmail));
    }

    private User createNewUser(OAuth2User oAuth2User) {
        User user = new User();
        user.setUsername(oAuth2User.getAttribute("name"));
        user.setEmail(oAuth2User.getAttribute("email"));
        user.setGoogleId(oAuth2User.getAttribute("sub"));
        user.setPassword("OAUTH2_DEFAULT_PASSWORD");
        userRepository.save(user);
        return user;
    }

    private void updateProfilePicture(User user, String pictureUrl) {
        if (pictureUrl != null) {
            user.setAvtUrl(pictureUrl);
            userRepository.save(user);
        }
    }

    private UserDetails createOAuthUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password("")
                .roles("USER")
                .build();
    }
}
