package com.project.learningz.service;

import com.project.learningz.constant.Role;
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

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = findUserByUsernameOrEmail(usernameOrEmail);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(String.valueOf(user.getRole()))
                .build();
    }

    public void processOAuthPostLogin(OAuth2User oAuth2User) {
        String googleId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        User user = userRepository.findByEmail(email);

        if (user == null) {
            user = new User();
            user.setUsername(name);
            user.setEmail(email);
            user.setGoogleId(googleId);
            user.setPassword("OAUTH2_DEFAULT_PASSWORD");
            user.setRole(Role.STUDENT);
            userRepository.save(user);
        } else if (user.getGoogleId() == null) {
            user.setGoogleId(googleId);
            userRepository.save(user);
        }

        if (user.getAvtUrl() == null || user.getAvtUrl().isEmpty()) {
            updateProfilePicture(user, oAuth2User.getAttribute("picture"));
        }

        UserDetails userDetails = createOAuthUserDetails(user);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        );
    }


    private User findUserByUsernameOrEmail(String usernameOrEmail) {
        User user = userRepository.findByUsername(usernameOrEmail);
        if (user == null) {
            user = userRepository.findByEmail(usernameOrEmail);
        }
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
                .username(user.getEmail())
                .password("")
                .roles(String.valueOf(user.getRole()))
                .build();
    }
}
