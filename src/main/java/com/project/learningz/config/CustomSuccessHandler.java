package com.project.learningz.config;

import com.project.learningz.constant.UserStatus;
import com.project.learningz.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectURL = "/home";

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_ADMIN")) {
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
                if (prevPage != null) {
                    request.getSession().removeAttribute("prevPage");
                    if (!prevPage.contains("/login")) {
                        redirectURL = prevPage;
                        System.out.println("prevPage:" + prevPage);
                    }
                    break;
                }
            }
        }
        System.out.println("url:" + redirectURL);
        response.sendRedirect(redirectURL);
    }
}

