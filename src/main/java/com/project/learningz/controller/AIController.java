package com.project.learningz.controller;


import com.project.learningz.entity.AiFeedBack;
import com.project.learningz.service.AiService;
import com.project.learningz.service.QnAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class AIController {

    @Autowired
    private AiService aiService;

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

    @PostMapping("/feedback")
    public String feedBack(@ModelAttribute AiFeedBack aiFeedBack, Model model,
                           @RequestHeader(value = "Referer", defaultValue = "/") String referer,
                           RedirectAttributes redirectAttributes) {
        aiService.save(aiFeedBack);
        redirectAttributes.addFlashAttribute("message", "Feedback successfully!");
        return "redirect:" + referer;
    }


}
