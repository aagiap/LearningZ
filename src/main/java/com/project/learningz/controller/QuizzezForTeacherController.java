package com.project.learningz.controller;

import com.project.learningz.dto.QuestionDetailDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/teacher")
@Controller
public class QuizzezForTeacherController {

    @Autowired
    QuestionExpertService questionService;
    @Autowired
    GradeService gradeService;
    @Autowired
    QuizService quizService;
    @Autowired
    ChapterService chapterService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    LessonService lessonService;
    @Autowired
    CourseService courseService;
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

        List<Quiz> quizzes = quizService.getQuizzesByLessonId(lessonId);
        Lesson lesson = lessonService.getLessonById(lessonId);

        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("lesson", lesson);

        return "teacherPage/quizzesList";
    }

//    public String getAllQuizzes(@RequestParam(value = "keyword", required = false) String keyword,
//                                @RequestParam(defaultValue = "0") int page,
//                                @RequestParam(defaultValue = "10") int size,
//                                @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
//                                @AuthenticationPrincipal OAuth2User userOAuth2,
//                                Model model) {
//        String username = null;
//
//        if (user != null) {
//            username = user.getUsername();
//        } else if (userOAuth2 != null) {
//            String email = userOAuth2.getAttribute("email");
//            username = userManagementService.findUserNameByEmail(email);
//        }
//        String avatarUrl = userManagementService.getAvtByUsername(username);
//        if (avatarUrl == null) {
//            avatarUrl = "/image/AvartaDefault.jpg";
//        }
//
//        Page<QuestionDetailDTO> questions;
//        if (keyword != null && !keyword.trim().isEmpty()) {
//            questions = questionService.getAllQuestionByKeyword(keyword, page, size);
//        } else {
//            questions = questionService.getAllQuestion(page, size);
//        }
//
//        List<String> chapters = chapterService.getAllChaptersInQuestions();
//        List<String> lessons = lessonService.getAllLessonInQuestions();
//        List<String> grades = gradeService.getAllGradeInQuestions();
//        List<String> courses = courseService.getAllCourseInQuestions();
//        List<String> subjects = subjectService.getAllSubjectInQuestions();
//
//        model.addAttribute("questions", questions.getContent());
//        model.addAttribute("chapters", chapters);
//        model.addAttribute("lessons", lessons);
//        model.addAttribute("grades", grades);
//        model.addAttribute("courses", courses);
//        model.addAttribute("subjects", subjects);
//        model.addAttribute("username", username);
//        model.addAttribute("avatarUrl", avatarUrl);
//
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", questions.getTotalPages());
//        return "teacherPage/questionList";
//    }

//    @PostMapping("/questionList")
//    public String getQuestionByFilter(@RequestParam(required = false) String grade,
//                                      @RequestParam(required = false) String subject,
//                                      @RequestParam(required = false) String course,
//                                      @RequestParam(required = false) String chapter,
//                                      @RequestParam(required = false) String lesson,
//                                      @RequestParam(defaultValue = "0") int page,
//                                      @RequestParam(defaultValue = "10") int size,
//                                      @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
//                                      @AuthenticationPrincipal OAuth2User userOAuth2,
//                                      Model model,
//                                      HttpServletRequest request) {
//        String username = null;
//
//        if (user != null) {
//            username = user.getUsername();
//        } else if (userOAuth2 != null) {
//            String email = userOAuth2.getAttribute("email");
//            username = userManagementService.findUserNameByEmail(email);
//        }
//        String avatarUrl = userManagementService.getAvtByUsername(username);
//        if (avatarUrl == null) {
//            avatarUrl = "/image/AvartaDefault.jpg";
//        }
//
//        Page<QuestionDetailDTO> questions = questionService.filterQuestion(grade, subject, course, chapter, lesson, page, size);
//        model.addAttribute("questions", questions.getContent());
//        model.addAttribute("selectedGrade", grade);
//        model.addAttribute("selectedSubject", subject);
//        model.addAttribute("selectedCourse", course);
//        model.addAttribute("selectedChapter", chapter);
//        model.addAttribute("selectedLesson", lesson);
//        model.addAttribute("chapters", chapterService.getAllChaptersInQuestions());
//        model.addAttribute("lessons", lessonService.getAllLessonInQuestions());
//        model.addAttribute("grades", gradeService.getAllGradeInQuestions());
//        model.addAttribute("courses", courseService.getAllCourseInQuestions());
//        model.addAttribute("subjects", subjectService.getAllSubjectInQuestions());
//        model.addAttribute("username", username);
//        model.addAttribute("avatarUrl", avatarUrl);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", questions.getTotalPages());
//
//        // Giữ lại dữ liệu bộ lọc để khi chuyển trang không bị mất
//        request.setAttribute("selectedGrade", grade);
//        request.setAttribute("selectedSubject", subject);
//        request.setAttribute("selectedCourse", course);
//        request.setAttribute("selectedChapter", chapter);
//        request.setAttribute("selectedLesson", lesson);
//        return "teacherPage/questionList";
//    }
}
