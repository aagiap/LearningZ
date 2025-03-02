package com.project.learningz.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
    @GetMapping
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return "error/401";
            }

            if(statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error/403";
            }

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            }

            if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/500";
            }
        }

        return "error/default";
    }
    @GetMapping("/banned-message")
    public String bannedMessage(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication,
                                Model model) {
        model.addAttribute("title", "You are banned");
        model.addAttribute("message", "You are banned, please contact admin at LearningZ@gmail.com to unban.");
        request.getSession().invalidate();
        return "auth/message";
    }
}
