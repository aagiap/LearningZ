package com.project.learningz.controller;


import com.project.learningz.entity.AiFeedBack;
import com.project.learningz.service.AiService;
import com.project.learningz.service.QnAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("api")
public class AIController {

    @Autowired
    private AiService aiService;

    @Autowired
    private QnAService qnAService;

    @GetMapping("/ask")
    public String showForm() {
        AiFeedBack aiFeedBack = new AiFeedBack();
        return "common/chat-with-ai"; // Trả về trang HTML Thymeleaf
    }

    @PostMapping("/ask")
    public String askQuestion(@RequestParam("question") String question,
                              @RequestParam(value = "image", required = false) MultipartFile image,
                              Model model,
                              @RequestHeader(value = "Referer", defaultValue = "/") String referer,
                              RedirectAttributes redirectAttributes) {
        String answer = qnAService.getAnswer(question, image);
        // Đưa dữ liệu vào RedirectAttributes
        redirectAttributes.addFlashAttribute("question", question);
        redirectAttributes.addFlashAttribute("answer", answer);
        return "redirect:" + referer;
    }

    @PostMapping("/feedback")
    public String feedBack(@ModelAttribute AiFeedBack aiFeedBack, Model model,
                           @RequestHeader(value = "Referer", defaultValue = "/") String referer,
                           RedirectAttributes redirectAttributes) {
        aiService.save(aiFeedBack);
        redirectAttributes.addFlashAttribute("message", "Feedback successfully!");
        return "redirect:" + referer;
    }




//    @PostMapping("/ask")
//    public String askQuestion(@RequestParam("question") String question, Model model) {
//        String answer = qnAService.getAnswer(question);
//        model.addAttribute("question", question);
//        model.addAttribute("answer", answer);
//        return "/chat-with-ai"; // Trả về trang HTML cùng với câu trả lời
//    }

    //    @PostMapping("ask")
//    public ResponseEntity<String> askQuestion(@RequestBody Map<String,String> payload){
//String question = payload.get("question");
//String answer = qnAService.getAnswer(question);
//        return ResponseEntity.ok(answer);
//    }
}
