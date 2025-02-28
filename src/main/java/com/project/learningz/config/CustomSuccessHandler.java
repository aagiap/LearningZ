package com.project.learningz.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    private final RequestCache requestCache = new HttpSessionRequestCache();

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
            } else if (role.equals("ROLE_EXPERT")) {
                redirectURL = "/expert";
                break;
            } else if (role.equals("ROLE_MARKETING_TEAM")) {
                redirectURL = "/marketer/dashboard";
                break;
            } else if (role.equals("ROLE_STUDENT") || role.equals("ROLE_VIP_STUDENT")) {
                String prevPage = (String) request.getSession().getAttribute("prevPage");
                SavedRequest savedRequest = requestCache.getRequest(request, response);

                if (savedRequest != null) {
                    response.sendRedirect(savedRequest.getRedirectUrl());
                    return;
                }

                if (prevPage != null) {
                    request.getSession().removeAttribute("prevPage");
                    if (!prevPage.contains("/login")) {
                        redirectURL = prevPage;
                    }
                    break;
                }
            }
        }
        response.sendRedirect(redirectURL);
    }
}

