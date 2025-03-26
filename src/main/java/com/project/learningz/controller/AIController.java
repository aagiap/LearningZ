package com.project.learningz.controller;


import com.project.learningz.service.QnAService;
import com.project.learningz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class AIController {


    @Autowired
    private QnAService qnAService;

    @Autowired
    private UserService userService;


    @PostMapping("/ask")
    public ResponseEntity<String> askQuestion(@RequestBody Map<String, Object> payload,
                                              @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                              @AuthenticationPrincipal OAuth2User userOAuth2, Model model) {
        String question = (String) payload.get("question");
        String image = (String) payload.get("image");
        String username = null;
        Integer userId = null;
        if (user != null) {
            username = user.getUsername();
            userId = userService.getUserIdByUsername(username);
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userService.findUserNameByEmail(email);
            userId = userService.getUserIdByUsername(username);
        }
        String aiResponseChoosen = qnAService.getDataFromAiResponse(question);
        String dataForAnswer = qnAService.getFunctionData(aiResponseChoosen, userId);
        // Extract chat history if available
        List<Map<String, String>> chatHistory = null;
        if (payload.containsKey("chatHistory")) {
            chatHistory = (List<Map<String, String>>) payload.get("chatHistory");
        }

        String answer = qnAService.getAnswerWithHistory(question, chatHistory, dataForAnswer, image);
        return ResponseEntity.ok(answer);
    }


}
