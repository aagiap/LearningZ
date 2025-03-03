package com.project.learningz.controller;

import com.project.learningz.dto.QuizJoinToGradeDTO;
import com.project.learningz.dto.QuizSubmitionDTO;
import com.project.learningz.dto.QuizSubmitionListDTO;
import com.project.learningz.entity.Course;
import com.project.learningz.entity.QuestionBank;
import com.project.learningz.entity.Quiz;
import com.project.learningz.entity.QuizResult;
import com.project.learningz.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/Exam")
public class DoQuizController {
    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizQuestionBankService quizQuestionBankService;

    @Autowired
    private QuizReviewService quizReviewService;

    @Autowired
    private QuizResultService quizResultService;

    @Autowired
    private UserService userService;

    @Autowired
    private UsersCourseService usersCourseService;

    @GetMapping("/StartQuiz")
    public String startQuiz(@RequestParam("quizId") Integer quizId, Model model,HttpSession session,
                            @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                            @AuthenticationPrincipal OAuth2User userOAuth2) {
        String username = null;
        if (user != null) {
            username = user.getUsername();
            model.addAttribute("user", user);
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userService.findUserNameByEmail(email);
            model.addAttribute("user", userOAuth2);
        }
        Integer userId = userService.getUserIdByUsername(username);
        Quiz quiz = quizService.getQuizById(quizId);
        Course course = quiz.getLesson().getChapter().getCourse();
        Integer courseId = quiz.getLesson().getChapter().getCourse().getId();
        Boolean isEnrolled = usersCourseService.checkUserEnrolled(username, courseId);
        if (!isEnrolled) {
            model.addAttribute("quiz", quiz);
            model.addAttribute("course", course);
            return "quiz/quiz-warning";
        }
        model.addAttribute("username", username);

        String avatarUrl = userService.getAvtByUsername(username);
        model.addAttribute("avatarUrl", avatarUrl);
        QuizJoinToGradeDTO quizJoinToGradeDTO = quizService.getQuizJoinToGradeDTOById(quizId);
        QuizResult quizResult = quizResultService.findQuizResultsByQuizIdAndUserId(userId,quizId);
        model.addAttribute("quizResult", quizResult);
        model.addAttribute("quiz", quizJoinToGradeDTO);
        session.setAttribute("quiz", quiz);
        return "/quiz/StartQuiz";
    }

    @GetMapping("/DoQuiz")
    public String doQuiz(@RequestParam("quizId") Integer quizId, Model model, HttpSession session) {
        Quiz quiz = quizService.getQuizById(quizId);
        List<QuestionBank> questionBankList = quizQuestionBankService.findQuestionBankByQuizId(quizId);
        int timeLimitSeconds = quiz.getTimeLimit() * 60;
        model.addAttribute("quiz", quiz);
        model.addAttribute("timeLimitSeconds", timeLimitSeconds);
        session.setAttribute("quiz", quiz);
        if(session.getAttribute("questionBankList")!=null){
            model.addAttribute("questionBankList", (List<QuestionBank>) session.getAttribute("questionBankList"));
        }else{
            model.addAttribute("questionBankList", questionBankList);
            session.setAttribute("questionBankList", questionBankList);
        }
        return "/quiz/DoQuiz";
    }

    @PostMapping("/CheckProgress")
    public String checkProgress(@ModelAttribute QuizSubmitionListDTO quizSubmitionListDTO, Model model,HttpSession session){
        int answeredQuestions = quizReviewService.countAnsweredQuestions(quizSubmitionListDTO.getAnswers());
        int totalQuestions = quizReviewService.countTotalQuestions(quizSubmitionListDTO.getAnswers());
        List<QuizSubmitionDTO> quizSubmitionList = quizReviewService.setWrongSelections(quizSubmitionListDTO.getAnswers());
        model.addAttribute("quizSubmitionList", quizSubmitionList);
        if(answeredQuestions == totalQuestions){
            session.setAttribute("quizSubmitionListDTO", quizSubmitionListDTO);
            model.addAttribute("warningTitle", "Score Exam ?");
            model.addAttribute("warningMessage", "You have answered "+ answeredQuestions +" / " + totalQuestions);
            return "/quiz/QuizProgressWarning";
        }else if (answeredQuestions < totalQuestions){
            session.setAttribute("quizSubmitionListDTO", quizSubmitionListDTO);
            model.addAttribute("warningTitle", "Score Exam ?");
            model.addAttribute("warningMessage", "You have answered "+ answeredQuestions +" / " + totalQuestions);
            return "/quiz/QuizProgressWarning";
        }else {
            session.setAttribute("quizSubmitionListDTO", quizSubmitionListDTO);
            model.addAttribute("warningTitle", "Score Exam ?");
            model.addAttribute("warningMessage", "You have not answered any question");
            return "/quiz/QuizProgressWarning";
        }
    }

    @PostMapping("/SubmitQuiz")
    public String submitQuiz(Model model, HttpSession session,
                             @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                             @AuthenticationPrincipal OAuth2User userOAuth2) {
        QuizSubmitionListDTO quizSubmitionListDTO = (QuizSubmitionListDTO) session.getAttribute("quizSubmitionListDTO");
        Quiz quiz = (Quiz) session.getAttribute("quiz");
        int correctAnswers = quizReviewService.countCorrectAnswers(quizSubmitionListDTO.getAnswers());
        int totalQuestions = quizReviewService.countTotalQuestions(quizSubmitionListDTO.getAnswers());
        float score = quizReviewService.calculateScore(totalQuestions, correctAnswers);

        String username = null;
        if (user != null) {
            username = user.getUsername();
            model.addAttribute("user", user);
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userService.findUserNameByEmail(email);
            model.addAttribute("user", userOAuth2);
        }
        Integer userId = userService.getUserIdByUsername(username);
        model.addAttribute("username", username);

        String avatarUrl = userService.getAvtByUsername(username);
        model.addAttribute("avatarUrl", avatarUrl);
        quizResultService.saveResult(userId, quiz.getId(), score);

        session.setAttribute("quizSubmitionListDTO", quizSubmitionListDTO);
        model.addAttribute("score", score);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("totalQuestions", totalQuestions);
        return "/quiz/QuizResult";
    }

    @PostMapping("/SubmitQuizByTime")
    public String submitQuizByTime(@ModelAttribute QuizSubmitionListDTO quizSubmitionListDTO,Model model, HttpSession session,
                                   @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                   @AuthenticationPrincipal OAuth2User userOAuth2) {
        Quiz quiz = (Quiz) session.getAttribute("quiz");
        int correctAnswers = quizReviewService.countCorrectAnswers(quizSubmitionListDTO.getAnswers());
        int totalQuestions = quizReviewService.countTotalQuestions(quizSubmitionListDTO.getAnswers());
        float score = quizReviewService.calculateScore(totalQuestions, correctAnswers);

        String username = null;
        if (user != null) {
            username = user.getUsername();
            model.addAttribute("user", user);
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userService.findUserNameByEmail(email);
            model.addAttribute("user", userOAuth2);
        }
        Integer userId = userService.getUserIdByUsername(username);
        quizResultService.saveResult(userId, quiz.getId(), score);
        model.addAttribute("username", username);

        String avatarUrl = userService.getAvtByUsername(username);
        model.addAttribute("avatarUrl", avatarUrl);

        model.addAttribute("score", score);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("totalQuestions", totalQuestions);
        session.setAttribute("quizSubmitionListDTO", quizSubmitionListDTO);
        return "/quiz/QuizResult";
    }

    @GetMapping("/QuizReview")
    public String quizReview(Model model, HttpSession session,
                             @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                             @AuthenticationPrincipal OAuth2User userOAuth2) {
        String username = null;
        if (user != null) {
            username = user.getUsername();
            model.addAttribute("user", user);
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userService.findUserNameByEmail(email);
            model.addAttribute("user", userOAuth2);
        }
        Integer userId = userService.getUserIdByUsername(username);
        model.addAttribute("username", username);

        String avatarUrl = userService.getAvtByUsername(username);
        model.addAttribute("avatarUrl", avatarUrl);

        QuizSubmitionListDTO quizSubmitionListDTO = (QuizSubmitionListDTO) session.getAttribute("quizSubmitionListDTO");
        List<QuizSubmitionDTO> quizSubmitionList = quizReviewService.setWrongSelections(quizSubmitionListDTO.getAnswers());
        List<String> resultQuestions = quizReviewService.getResultQuestion(quizSubmitionListDTO.getAnswers());
        Quiz quiz = (Quiz) session.getAttribute("quiz");
        List<QuestionBank> questionBankList = (List<QuestionBank>) session.getAttribute("questionBankList");
//        session.invalidate();
        int correctAnswers = quizReviewService.countCorrectAnswers(quizSubmitionListDTO.getAnswers());
        int totalQuestions = quizReviewService.countTotalQuestions(quizSubmitionListDTO.getAnswers());
        float score = quizReviewService.calculateScore(totalQuestions, correctAnswers);
        model.addAttribute("questionBankList",questionBankList);
        model.addAttribute("quiz", quiz);
        model.addAttribute("score", score);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("resultQuestions", resultQuestions);
        model.addAttribute("quizSubmitionList", quizSubmitionList);
        return "/quiz/QuizReview";
    }

}
