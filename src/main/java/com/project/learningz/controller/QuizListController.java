package com.project.learningz.controller;


import com.project.learningz.constant.CourseStatus;
import com.project.learningz.entity.Course;
import com.project.learningz.entity.Quiz;
import com.project.learningz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
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

    @Autowired
    private LessonService lessonService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private UserService userService;

    @Autowired
    private UsersCourseService usersCourseService;

    @Autowired
    private QuizResultService quizResultService;


    @GetMapping("course")
    public String getQuizzesInCourse(@RequestParam int courseId, Model model,
                                     @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                     @AuthenticationPrincipal OAuth2User userOAuth2) {


        Course course = courseService.getCourseById(courseId);
        if(course.getCourseStatus() != CourseStatus.ACTIVE){
            return "redirect:/course";
        }
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


        boolean checkConditionFeedBack = usersCourseService.checkConditionFeedback(userId, course.getId());
        List<String> completionStatus = lessonService.isLessonCompleted(userId, courseId);
        String progress = usersCourseService.progressStatus(userId, courseId);

        List<Quiz> quizzes = quizService.getQuizzesByCourseId(courseId);
        LinkedHashMap<Quiz, String> quizInfores = new LinkedHashMap<>();
        //HashMap<Quiz, String> quizInfores = new HashMap<>();
        for (Quiz quiz : quizzes) {
            quizInfores.put(quiz, quizResultService.isPass(userId, quiz.getId()));
        }


        model.addAttribute("quizInfores", quizInfores);
        model.addAttribute("progress", progress);
        model.addAttribute("checkConditionFeedBack", checkConditionFeedBack);
        model.addAttribute("completionStatus", completionStatus);
        model.addAttribute("course", course);
        return "quiz/quizzes-course";
    }


}
