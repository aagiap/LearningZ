package com.project.learningz.controller;


import com.project.learningz.dto.QuizJoinToGradeDTO;
import com.project.learningz.service.CourseService;
import com.project.learningz.service.GradeService;
import com.project.learningz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/QuizList")
public class QuizListController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private CourseService courseService;

    @GetMapping
    public String getQuizzes(
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String grade,
            Model model) {
        List<String> courses = courseService.findDistinctSubject();
        List<String> grades = gradeService.findDistinctGradeName();
        List<QuizJoinToGradeDTO> quizzes = quizService.getQuizzesWithFilter(subject, grade);

        model.addAttribute("courses", courses);
        model.addAttribute("grades", grades);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("selectedSubject", subject);
        model.addAttribute("selectedGrade", grade);
        return "quiz/QuizList";
    }


}
