package com.project.learningz.controller;


import com.project.learningz.service.QnAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class AIController {


    @Autowired
    private QnAService qnAService;


    @PostMapping("/ask")
    public ResponseEntity<String> askQuestion(@RequestBody Map<String, Object> payload) {
        String question = (String) payload.get("question");

        // Extract chat history if available
        List<Map<String, String>> chatHistory = null;
        if (payload.containsKey("chatHistory")) {
            chatHistory = (List<Map<String, String>>) payload.get("chatHistory");
        }

        String answer = qnAService.getAnswerWithHistory(question, chatHistory);
        return ResponseEntity.ok(answer);
    }




}
