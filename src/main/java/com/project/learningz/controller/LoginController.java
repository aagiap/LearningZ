package com.project.learningz.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import com.project.learningz.service.MonthlyStatisticService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private RequestCache requestCache;

    @Autowired
    private MonthlyStatisticService monthlyStatisticService;

    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request,
                            @RequestParam(value = "error", required = false) String error) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            monthlyStatisticService.updateMonthlyStatistic();
            return "redirect:/home";
        }

        SavedRequest savedRequest = requestCache.getRequest(request, null);
        System.out.println("savedRequest:" + savedRequest);

        String referrer = request.getHeader("Referer");
        System.out.println("referrer: " + referrer);
        if (savedRequest != null && !savedRequest.getRedirectUrl().contains("/error")
                && !savedRequest.getRedirectUrl().contains("/ws")
                && !savedRequest.getRedirectUrl().contains("/favicon.ico")) {
            request.getSession().setAttribute("prevPage", savedRequest.getRedirectUrl());
        } else if (referrer != null && !referrer.contains("/login") && !referrer.contains("/error")
                && !referrer.contains("/register") && !referrer.contains("/verify") && !referrer.contains("/resend")
                && !referrer.contains("/forgot_password") && !referrer.contains("/reset_password")) {
            request.getSession().setAttribute("prevPage", referrer);
        }

        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }
        return "/auth/login";
    }
}


