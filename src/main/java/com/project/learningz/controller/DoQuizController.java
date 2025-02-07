package com.project.learningz.controller;

import com.project.learningz.dto.QuizJoinToGradeDTO;
import com.project.learningz.dto.QuizSubmitionDTO;
import com.project.learningz.dto.QuizSubmitionListDTO;
import com.project.learningz.entity.QuestionBank;
import com.project.learningz.entity.Quiz;
import com.project.learningz.service.QuizQuestionBankService;
import com.project.learningz.service.QuizReviewService;
import com.project.learningz.service.QuizService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/StartQuiz")
    public String startQuiz(@RequestParam("quizId") Integer quizId, Model model,HttpSession session) {
        QuizJoinToGradeDTO quizJoinToGradeDTO = quizService.getQuizJoinToGradeDTOById(quizId);
        model.addAttribute("quiz", quizJoinToGradeDTO);
        Quiz quiz = quizService.getQuizById(quizId);
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
        model.addAttribute("questionBankList", questionBankList);
        return "/quiz/DoQuiz";
    }

    @PostMapping("/CheckProgress")
    public String checkProgress(@ModelAttribute QuizSubmitionListDTO quizSubmitionListDTO, Model model,HttpSession session){
        int answeredQuestions = quizReviewService.countAnsweredQuestions(quizSubmitionListDTO.getAnswers());
        int totalQuestions = quizReviewService.countTotalQuestions(quizSubmitionListDTO.getAnswers());
        if(answeredQuestions == totalQuestions){
            session.setAttribute("quizSubmitionListDTO", quizSubmitionListDTO);
            model.addAttribute("warningTitle", "Score Exam ?");
            model.addAttribute("warningMessage", "You have answered "+ answeredQuestions +" /" + totalQuestions);
            return "/quiz/QuizProgressWarning";
        }else if (answeredQuestions < totalQuestions){
            session.setAttribute("quizSubmitionListDTO", quizSubmitionListDTO);
            model.addAttribute("warningTitle", "Score Exam ?");
            model.addAttribute("warningMessage", "You have answered "+ answeredQuestions +" /" + totalQuestions);
            return "/quiz/QuizProgressWarning";
        }else {
            session.setAttribute("quizSubmitionListDTO", quizSubmitionListDTO);
            model.addAttribute("warningTitle", "Score Exam ?");
            model.addAttribute("warningMessage", "You have not answered any question");
            return "/quiz/QuizProgressWarning";
        }
    }

    @PostMapping("/SubmitQuiz")
    public String submitQuiz(Model model, HttpSession session) {
        QuizSubmitionListDTO quizSubmitionListDTO = (QuizSubmitionListDTO) session.getAttribute("quizSubmitionListDTO");
        int correctAnswers = quizReviewService.countCorrectAnswers(quizSubmitionListDTO.getAnswers());
        int totalQuestions = quizReviewService.countTotalQuestions(quizSubmitionListDTO.getAnswers());
        float score = quizReviewService.calculateScore(totalQuestions, correctAnswers);
        model.addAttribute("score", score);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("totalQuestions", totalQuestions);
        session.setAttribute("quizSubmitionListDTO", quizSubmitionListDTO);
        return "/quiz/QuizResult";
    }

    @GetMapping("/QuizReview")
    public String quizReview(Model model, HttpSession session) {
        QuizSubmitionListDTO quizSubmitionListDTO = (QuizSubmitionListDTO) session.getAttribute("quizSubmitionListDTO");
        List<QuizSubmitionDTO> quizSubmitionList = quizReviewService.setWrongSelections(quizSubmitionListDTO.getAnswers());
        List<String> resultQuestions = quizReviewService.getResultQuestion(quizSubmitionListDTO.getAnswers());
        model.addAttribute("resultQuestions", resultQuestions);
        model.addAttribute("quizSubmitionList", quizSubmitionList);
        return "/quiz/QuizReview";
    }


}
