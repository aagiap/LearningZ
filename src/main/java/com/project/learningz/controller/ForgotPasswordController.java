package com.project.learningz.controller;

import com.project.learningz.entity.User;
import com.project.learningz.service.EmailService;
import com.project.learningz.service.UserRegisterAccountService;
import com.project.learningz.service.UserService;
import com.project.learningz.utils.Utility;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Controller

public class ForgotPasswordController {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRegisterAccountService userRegisterAccountServiceService;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "auth/forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, HttpSession session, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            session.setAttribute("tokenGenerationTime", LocalDateTime.now());
            emailService.sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return "auth/forgot_password_form";
    }


    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model, HttpSession session) {
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "auth/message";
        }

        LocalDateTime tokenGenerationTime = (LocalDateTime) session.getAttribute("tokenGenerationTime");
        if(userRegisterAccountServiceService.isCodeExpired(tokenGenerationTime)){
            model.addAttribute("message", "Reset Password Link has expired! Try again.");
            return "/auth/forgot_password_form";
        }

        return "auth/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "auth/message";
        } else {
            userService.updatePassword(user, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "auth/message";
    }

}

