package com.project.learningz.controller;

import com.project.learningz.entity.User;
import com.project.learningz.service.UserRegisterAccountService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class RegisterAccountController {
    @Autowired
    private UserRegisterAccountService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "/auth/RegisterAccount";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user,
                               @RequestParam("confirm_password") String confirmPassword ,
    Model model, HttpSession session) {
        Logger logger = LoggerFactory.getLogger(RegisterAccountController.class);
        try {
            if (userService.checkExistUsername(user.getUsername())) {
                model.addAttribute("message", "Username is exists!");
                return "/auth/RegisterAccount";
            }
            if (userService.checkExistEmail(user.getEmail())) {
                model.addAttribute("message", "Email is exists!");
                return "/auth/RegisterAccount";
            }
            if(!user.getPassword().equals(confirmPassword)){
                model.addAttribute("message", "Passwords do not match!");
                return "/auth/RegisterAccount";
            }
            String codeSystem = userService.randomCode5number();
            session.setAttribute("codeGenerationTime", LocalDateTime.now());
            userService.sendVerificationEmail(user, codeSystem);
            session.setAttribute("codeSystem", codeSystem);
            session.setAttribute("user", user);
            return "redirect:/verify";
        } catch (Exception e) {
            logger.error("Error during registration", e);
            model.addAttribute("message", "Something went wrong! Please try again..");
            return "/auth/RegisterAccount";
        }

    }

    @GetMapping("/verify")
    public String showVerifyForm() {
        return "/auth/VerificationCodeRegisterAccount";
    }

    @PostMapping("/verify")
    public String verifyUser(@RequestParam("code") String code, HttpSession session,
                             Model model) {
        try {
            String codeSystem = (String) session.getAttribute("codeSystem");
            User user = (User) session.getAttribute("user");
            LocalDateTime codeGenerationTime = (LocalDateTime) session.getAttribute("codeGenerationTime");
            if(userService.isCodeExpired(codeGenerationTime)){
                model.addAttribute("message", "Verification code has expired!");
                return "/auth/VerificationCodeRegisterAccount";
            }
            if (code.equals(codeSystem)) {
                userService.register(user);
                return "/auth/login";
            } else {
                model.addAttribute("message", "Incorrect confirmation code!");
                return "/auth/VerificationCodeRegisterAccount";
            }
        } catch (Exception e) {
            model.addAttribute("message", "Something went wrong! Please try again..");
            return "/auth/VerificationCodeRegisterAccount";
        }
    }
    @GetMapping("/resend")
    public String showResendForm(Model model,HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            String codeSystem = userService.randomCode5number();
            session.setAttribute("codeGenerationTime", LocalDateTime.now());
            userService.sendVerificationEmail(user, codeSystem);
            session.setAttribute("codeSystem", codeSystem);
        } catch (Exception e) {
            model.addAttribute("message", "Something went wrong! Please try again..");
        }
        return "/auth/VerificationCodeRegisterAccount";
    }

    @PostMapping("/resend")
    public String resendCode(@RequestParam("code") String code,HttpSession session, Model model) {
        try {
            User user = (User) session.getAttribute("user");
            LocalDateTime codeGenerationTime = (LocalDateTime) session.getAttribute("codeGenerationTime");
            String codeSystem = (String) session.getAttribute("codeSystem");
            if (userService.isCodeExpired(codeGenerationTime)) {
                model.addAttribute("message", "Verification code has expired!");
                return "/auth/VerificationCodeRegisterAccount";
            }
            if (code.equals(codeSystem)) {
                userService.register(user);
                return "/auth/RegisterSuccess";
            } else {
                model.addAttribute("message", "Incorrect confirmation code!");
                return "/auth/VerificationCodeRegisterAccount";
            }

        } catch (Exception e) {
            model.addAttribute("message", "Something went wrong! Please try again..");
            return "/auth/VerificationCodeRegisterAccount";
        }
    }


    }
