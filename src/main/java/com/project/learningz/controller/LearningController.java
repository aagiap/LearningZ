package com.project.learningz.controller;


import com.project.learningz.constant.CourseStatus;
import com.project.learningz.entity.*;
import com.project.learningz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/learning")
public class LearningController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private UsersCourseService usersCourseService;

    @Autowired
    private QuizResultService quizResultService;

    @Autowired
    private PdfService pdfService;


    @GetMapping("chapterLessonList")
    public String viewChapterLesson(@RequestParam int courseId, Model model,
                                    @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                    @AuthenticationPrincipal OAuth2User userOAuth2) {
        List<Chapter> chapters = lessonService.getChaptersByCourseId(courseId);
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
        boolean checkConditionFeedBack = usersCourseService.checkConditionFeedback(userId, course.getId());
        List<String> completionStatus = lessonService.isLessonCompleted(userId, courseId);
        String progress = usersCourseService.progressStatus(userId, courseId);
        List<Course> courses = courseService.findCoursesByGradeId(course.getGrade().getId());


        List<Integer> lessonOffsets = new ArrayList<>(); // Danh sách tổng số bài học trước mỗi chương
        int totalLessonsBefore = 0;

        for (Chapter chapter : chapters) {
            lessonOffsets.add(totalLessonsBefore); // Lưu lại tổng số bài học trước chương hiện tại
            totalLessonsBefore += chapter.getLessons().size();
        }


model.addAttribute("lessonOffsets", lessonOffsets);
        Integer minScoreToPass = quizResultService.getMinScoreToPass();
        model.addAttribute("minScoreToPass", minScoreToPass);
        model.addAttribute("username", username);
        String avatarUrl = userService.getAvtByUsername(username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("courses", courses);
        model.addAttribute("progress", progress);
        model.addAttribute("checkConditionFeedBack", checkConditionFeedBack);
        model.addAttribute("completionStatus", completionStatus);
        model.addAttribute("course", course);
        model.addAttribute("chapters", chapters);
        return "/course/chapter-lesson-list";
    }

    @GetMapping("/lesson")
    public String viewLessonInChapter(@RequestParam int lessonId, @RequestParam int chapterId, Model model,
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

        Chapter chapter = chapterService.getChapterById(chapterId);
        Course course = courseService.getCourseById(chapter.getCourse().getId());
        if(course.getCourseStatus() != CourseStatus.ACTIVE){
            return "redirect:/course";
        }
        List<Chapter> chapters = lessonService.getChaptersByCourseId(chapter.getCourse().getId());
        Integer firstLessonIdOfPreviousChapter = lessonService.getFirstLessonIdOfPreviousChapter(chapter,chapters);
        Integer firstLessonIdOfNextChapter = lessonService.getFirstLessonIdOfNextChapter(chapter,chapters);
        Lesson lesson = lessonService.getLessonById(lessonId);
        List<Quiz> quizzes = lesson.getQuizzes();
        HashMap<Quiz, String> quizInfores = new HashMap<>();
        for (Quiz quiz : quizzes) {
            quizInfores.put(quiz, quizResultService.isPass(userId, quiz.getId()));
        }
        model.addAttribute("username", username);

        String avatarUrl = userService.getAvtByUsername(username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("firstLessonIdOfPreviousChapter", firstLessonIdOfPreviousChapter);
        model.addAttribute("firstLessonIdOfNextChapter", firstLessonIdOfNextChapter);
        model.addAttribute("chapters", chapters);
        model.addAttribute("quizInfores", quizInfores);
        model.addAttribute("lesson", lesson);
        model.addAttribute("chapter", chapter);
        return "/course/lesson-detail";
    }


    @GetMapping("/video")
    public String video(@RequestParam Integer videoId, Model model,
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

        Video video = videoService.findByVideoId(videoId);
        Chapter chapter = video.getLesson().getChapter();
        Course course = courseService.getCourseById(chapter.getCourse().getId());
        if(course.getCourseStatus() != CourseStatus.ACTIVE){
            return "redirect:/course";
        }
        List<Chapter> chapters = lessonService.getChaptersByCourseId(chapter.getCourse().getId());
        Lesson lesson = lessonService.getLessonById(video.getLesson().getId());

        model.addAttribute("username", username);

        String avatarUrl = userService.getAvtByUsername(username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("video", video);
        model.addAttribute("chapters", chapters);
        model.addAttribute("lesson", lesson);
        model.addAttribute("chapter", chapter);
        return "/course/video";
    }
    @GetMapping("/pdf")
    public String pdf(@RequestParam Integer pdfId, Model model,
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

        PDF pdf = pdfService.getPdfById(pdfId);
        Chapter chapter = pdf.getLesson().getChapter();
        Course course = courseService.getCourseById(chapter.getCourse().getId());
        if(course.getCourseStatus() != CourseStatus.ACTIVE){
            return "redirect:/course";
        }
        List<Chapter> chapters = lessonService.getChaptersByCourseId(chapter.getCourse().getId());
        Lesson lesson = lessonService.getLessonById(pdf.getLesson().getId());

        model.addAttribute("username", username);

        String avatarUrl = userService.getAvtByUsername(username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("pdf", pdf);
        model.addAttribute("chapters", chapters);
        model.addAttribute("lesson", lesson);
        model.addAttribute("chapter", chapter);
        return "/course/pdf";
    }
}
