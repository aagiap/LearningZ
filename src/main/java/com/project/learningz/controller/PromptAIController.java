package com.project.learningz.controller;


import com.project.learningz.service.QnAService;
import com.project.learningz.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Controller
@RequestMapping("/super_admin")
public class PromptAIController {

    @Autowired
    private QnAService qnAService;

    @Autowired
    private UserManagementService userManagementService;


    private static final String FILE_PATH = "document/PromptAI.txt";


    @GetMapping("/edit-prompt")
    public String showEditPromptPage(Model model,
                                     @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                     @AuthenticationPrincipal OAuth2User userOAuth2) {
        String username = null;

        if (user != null) {
            username = user.getUsername();
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userManagementService.findUserNameByEmail(email);
        }
        String avatarUrl = userManagementService.getAvtByUsername(username);
        if (avatarUrl == null) {
            avatarUrl = "/image/AvartaDefault.jpg";
        }
        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        try {
            String content = qnAService.readFixedTextFile();
            model.addAttribute("content", content);
        } catch (IOException e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        return "/user_management/editPrompt";
    }

    // Lưu nội dung mới vào file
    @PostMapping("/save-prompt")
    public String savePrompt(@RequestParam("content") String content, Model model,
                             @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                             @AuthenticationPrincipal OAuth2User userOAuth2) {
        String username = null;

        if (user != null) {
            username = user.getUsername();
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userManagementService.findUserNameByEmail(email);
        }
        String avatarUrl = userManagementService.getAvtByUsername(username);
        if (avatarUrl == null) {
            avatarUrl = "/image/AvartaDefault.jpg";
        }
        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        try {
            Files.writeString(Path.of(FILE_PATH), content, StandardOpenOption.TRUNCATE_EXISTING);
            model.addAttribute("message", "Save changes successful!");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Error!");
        }
        model.addAttribute("content", content);
        return "/user_management/editPrompt";
    }
}
