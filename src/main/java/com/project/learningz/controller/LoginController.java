package com.project.learningz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Model model,
                            @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }
        return "/auth/login";
    }
    @GetMapping("/home")
    public String homePage(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "/home/home";
    }
}


