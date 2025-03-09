package com.project.learningz.config;

import com.project.learningz.constant.UserStatus;
import com.project.learningz.entity.User;
import com.project.learningz.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String username = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
            System.out.println("username: " + username);
        } else if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
            String email = oauthUser.getAttribute("email");
            username = userService.findUserNameByEmail(email);
            System.out.println("username google: " + username);
        }

        User userLogin = userService.findByUsername(username);
        System.out.println("User status: " + userLogin.getUserStatus());
        if (userLogin != null && userLogin.getUserStatus() == UserStatus.BANNED) {
            System.out.println("moc 1");
            request.getSession().removeAttribute("prevPage");
            response.sendRedirect(request.getContextPath() + "/error/banned-message");
            return;
        }
        String redirectURL = determineRedirectUrl(authentication, request);
        System.out.println("redirectURL:" + redirectURL);
        System.out.println("moc 2");
        response.sendRedirect(redirectURL);
    }

    private String determineRedirectUrl(Authentication authentication, HttpServletRequest request) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectURL = "/home";

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_ADMIN_USER_MANAGER")) {
                redirectURL = "/admin/users/dashboard";
                break;
            } else if (role.equals("ROLE_ADMIN_COURSE_MANAGER")) {
                redirectURL = "/admin/courses/dashboard";
                break;
            }else if (role.equals("ROLE_ADMIN")) {
                redirectURL = "/admin/dashboard";
                break;
            } else if (role.equals("ROLE_TEACHER")) {
                redirectURL = "/teacher";
                break;
            } else if (role.equals("ROLE_MARKETING_TEAM")) {
                redirectURL = "/marketer/dashboard";
                break;
            } else if (role.equals("ROLE_STUDENT") || role.equals("ROLE_VIP_STUDENT")) {
                String prevPage = (String) request.getSession().getAttribute("prevPage");
                System.out.println("prevPage:" + prevPage);
                if (prevPage != null) {
                    request.getSession().removeAttribute("prevPage");
                    if (!prevPage.contains("/login")) {
                        redirectURL = prevPage;
                    }
                    break;
                }
            }
        }
        return redirectURL;
    }
}

