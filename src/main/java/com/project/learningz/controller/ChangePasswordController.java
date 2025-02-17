package com.project.learningz.controller;

import com.project.learningz.entity.User;
import com.project.learningz.repository.UserManagementRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Optional;

@Controller
@SessionAttributes("user")
public class ChangePasswordController {

    @Autowired
    UserManagementRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/home/change_password")
    public String changePasswordForm(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        String username;
        if (principal instanceof OAuth2User) {
            OAuth2User oauthUser = (OAuth2User) principal;
            email = oauthUser.getAttribute("email");
            User user = userRepository.findByEmail(email);
            if (user != null) {
                username = user.getUsername();
            } else {
                username = oauthUser.getAttribute("name");
            }
        } else {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        model.addAttribute("username", username);
        return "profile/change_password";
    }

    @PostMapping("/home/change_password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 HttpSession session, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof OAuth2User) {
            model.addAttribute("error", "Google accounts cannot change passwords.");
            return "profile/change_password";
        } else {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }

        Optional<User> tempUser = userRepository.findByUsername(username);
        if (tempUser.isEmpty()) {
            model.addAttribute("error", "No user found");
            return "/profile/change_password";
        }

        User user = tempUser.get();

        if (newPassword.length() < 6) {
            model.addAttribute("error", "Password must be at least 6 characters long");
            return "/profile/change_password";
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            model.addAttribute("error", "Incorrect old password");
            return "/profile/change_password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New passwords do not match");
            return "/profile/change_password";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        session.setAttribute("user", user);
        userRepository.save(user);

        model.addAttribute("message", "Password changed successfully");
        return "redirect:/home";
    }


}
