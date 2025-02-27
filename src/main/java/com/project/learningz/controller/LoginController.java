package com.project.learningz.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request,
                            @RequestParam(value = "error", required = false) String error) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }

        String referrer = request.getHeader("Referer");
        if (referrer != null) {
            request.getSession().setAttribute("prevPage", referrer);
        }

        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }
        return "/auth/login";
    }
}


