package com.project.learningz.controller;

import com.learningz.entity.User;
import com.learningz.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired UserRepository userRepository;

    @Autowired PasswordEncoder passwordEncoder;

//    @GetMapping("/encrypt-passwords")
//    public String encryptPasswords() {
//        List<User> users = userRepository.findAll();
//        for (User user : users) {
//            String password = user.getPassword();
//            if (!password.startsWith("$2a$")) { // Kiểm tra nếu chưa mã hóa
//                String encryptedPassword = passwordEncoder.encode(password);
//                user.setPassword(encryptedPassword);
//                userRepository.save(user);
//            }
//        }
//        return "/auth/success";
//    }

    @GetMapping("/create")
    public String create(HttpSession session) {
        String username = "User Two";
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            session.setAttribute("user", user);
            return "redirect:/change_password";
        } else {
            return "redirect:/error";
        }
    }


    @GetMapping("/change_password")
    public String showChangePasswordForm() {
        return "/auth/change_password";
    }

    @PostMapping("/change_password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 HttpSession session, Model model) {
        User tempUser = (User) session.getAttribute("user");
        if(tempUser != null) {
            if(!passwordEncoder.matches(oldPassword, tempUser.getPassword())) {
                    model.addAttribute("error", "Incorrect old password");
                    return "/auth/change_password";
            }
            if(!newPassword.equals(confirmPassword)) {
                model.addAttribute("error", "New passwords do not match");
                return "/auth/change_password";
            }
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            tempUser.setPassword(encodedNewPassword);
            session.setAttribute("user", tempUser);
            userRepository.save(tempUser);
            model.addAttribute("message", "Password changed successfully");
            return "redirect:/homepage?passwordChanged=true";
        }
        model.addAttribute("error", "No user found");
        return "/auth/change_password";
    }

}
