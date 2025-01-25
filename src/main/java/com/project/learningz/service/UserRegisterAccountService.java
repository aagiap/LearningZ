package com.project.learningz.service;


import com.project.learningz.constant.Role;
import com.project.learningz.entity.User;
import com.project.learningz.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class UserRegisterAccountService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LocalDateTime codeGenerationTime;


    public boolean checkExistEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public void register(User user) throws MessagingException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.STUDENT);
        user.setAvtUrl("images/default-avatar.png");
        userRepository.save(user);
    }

    public void sendVerificationEmail(User user, String code) throws MessagingException {
        String subject = "Verification code from LearningZ";
        String content = "Dear " + user.getUsername() + ",<br>"
                + "Your verification code is:<br>"
                + "<h3>" + code + "</h3>"
                + "Thank !<br>";
        emailService.sendVerificationEmail(user.getEmail(), subject, content);
    }

    public String randomCode5number() {
        int code = (int) Math.floor(((Math.random() * 89999) + 10000));
        return String.valueOf(code);
    }

    public boolean isCodeExpired(LocalDateTime codeGenerationTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(codeGenerationTime, now);
        return duration.toMinutes() >= 3;
    }


    public boolean checkExistUsername(String username) {
        return userRepository.findByUsername(username) != null;
    }



}

