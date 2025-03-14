package com.project.learningz.controller;

import com.project.learningz.dto.QuestionDetailDTO;
import com.project.learningz.dto.QuizDetailDTO;
import com.project.learningz.dto.QuizJoinToGradeDTO;
import com.project.learningz.entity.Grade;
import com.project.learningz.entity.Lesson;
import com.project.learningz.entity.Quiz;
import com.project.learningz.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/teacher")
@Controller
public class QuizzezForTeacherController {

    @Autowired
    QuestionExpertService questionService;
    @Autowired
    QuizService quizService;
    @Autowired
    private UserManagementService userManagementService;

    @GetMapping("/quizzes")
    public String getAllQuizzes(@RequestParam(value = "lessonId", required = false) Integer lessonId,
                                @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                @AuthenticationPrincipal OAuth2User userOAuth2,
                                Model model) {
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

        List<QuizDetailDTO> quizzes = quizService.getQuizzesByLessonId(lessonId);
        System.out.println(quizzes.size());

        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("quizzes", quizzes);

        return "teacherPage/quizzesList";
    }

    @GetMapping("/questions/{quizId}")
    public String getAllQuestions(@PathVariable Integer quizId,
                                  @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                  @AuthenticationPrincipal OAuth2User userOAuth2,
                                  Model model) {
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

        System.out.println(quizId);

        List<QuestionDetailDTO> questions = questionService.filterQuestionByQuizId(quizId);
        System.out.println(questions.size());

        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("questions", questions);

        return "teacherPage/questionsOfQuiz";
    }


}
