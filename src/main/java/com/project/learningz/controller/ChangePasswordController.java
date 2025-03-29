package com.project.learningz.controller;

import com.project.learningz.entity.User;
import com.project.learningz.repository.UserManagementRepository;
import com.project.learningz.service.UserManagementService;
import com.project.learningz.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/home")
public class ChangePasswordController {

    @Autowired
    UserService userService;

    @Autowired
    UserManagementRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserManagementService userManagementService;

    @GetMapping("/change_password")
    public String changePasswordForm(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                     @AuthenticationPrincipal OAuth2User userOAuth2,
                                     Model model) {
        String username = null;
        if (user != null) {
            username = user.getUsername();
            model.addAttribute("user", user);
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userManagementService.findUserNameByEmail(email);
            model.addAttribute("user", userOAuth2);
        }
        String avatarUrl = userManagementService.getAvtByUsername(username);
        if (avatarUrl == null) {
            avatarUrl = "/image/AvartaDefault.jpg";
        }
        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        System.out.println(username + " " + avatarUrl);
        return "profile/change_password";
    }

    @PostMapping("/change_password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 HttpSession session, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof OAuth2User) {
            model.addAttribute("error", "Google accounts cannot change passwords.");
            String email = ((OAuth2User) principal).getAttribute("email");
            User user = userService.findByEmail(email);
            model.addAttribute("user", user);
            model.addAttribute("avatarUrl", user.getAvtUrl());
            model.addAttribute("username", user.getUsername());
            return "profile/change_password";
        } else {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            model.addAttribute("error", "No user found");
            return "/profile/change_password";
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            model.addAttribute("error", "Incorrect current password");
            model.addAttribute("username", username);
            model.addAttribute("avatarUrl", user.getAvtUrl());
            model.addAttribute("user", user);
            return "/profile/change_password";
        }

        if (newPassword.length() < 6) {
            model.addAttribute("error", "Password must be at least 6 characters long");
            model.addAttribute("username", username);
            model.addAttribute("avatarUrl", user.getAvtUrl());
            model.addAttribute("user", user);
            return "/profile/change_password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New passwords do not match");
            model.addAttribute("username", username);
            model.addAttribute("avatarUrl", user.getAvtUrl());
            model.addAttribute("user", user);
            return "/profile/change_password";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
//        session.setAttribute("user", user);
        userRepository.save(user);

        model.addAttribute("message", "Password changed successfully");
        return "redirect:/home";
    }


}
