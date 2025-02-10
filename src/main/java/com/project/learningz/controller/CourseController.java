package com.project.learningz.controller;

import com.project.learningz.dto.CourseReviewDTO;
import com.project.learningz.entity.Chapter;
import com.project.learningz.entity.Course;
import com.project.learningz.entity.Lesson;
import com.project.learningz.service.*;
import com.project.learningz.utils.PageWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final UsersCourseService usersCourseService;
    private final UserService userService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private LessonService lessonService;


    @GetMapping("")
    public String viewCourse(Model model,
                             @RequestParam(name = "keyword", defaultValue = "") String keyword,
                             @RequestParam(name = "gradeId", defaultValue = "-1") int gradeId,
                             @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                             @RequestParam(name = "pageSize", defaultValue = "8") int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Course> pageCourse = courseService.getCoursesPagingByKeywordNGradeId(gradeId, keyword, pageable);
        Map<Integer, Double> averageRatings = usersCourseService.getAverageRatingByCourse();

        PageWrapper<Course> response = new PageWrapper<>(pageCourse, "/course");
        model.addAttribute("courses", pageCourse.getContent());
        model.addAttribute("averageRatings", averageRatings);
        model.addAttribute("page", response);
        model.addAttribute("keyword", keyword);
        model.addAttribute("gradeId", gradeId);

        return "course/course_list";
    }

    @GetMapping("/details/{id}")
    public String viewCourseDetails(@PathVariable("id") Integer id, Model model,
                                    @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                    @AuthenticationPrincipal OAuth2User userOAuth2) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);

        Map<Integer, Double> averageRatings = usersCourseService.getAverageRatingByCourse();
        model.addAttribute("averageRatings", averageRatings);

        int numberOfStudents = usersCourseService.numberOfStudentsInCourse(id);
        model.addAttribute("numberOfStudents", numberOfStudents);

        int lessonCount = courseService.getLessonCountByCourseId(id);
        model.addAttribute("lessonCount", lessonCount);

        List<CourseReviewDTO> reviews = usersCourseService.getCourseReviews(id);
        model.addAttribute("reviews", reviews);

        // Kiểm tra user và userOAuth2
        String username = null;

        if (user != null) {
            username = user.getUsername();
            model.addAttribute("user", user);
        } else if (userOAuth2 != null) {
            username = userOAuth2.getAttribute("name");
            model.addAttribute("user", userOAuth2);
        }

        boolean isEnrolled = (username != null) && usersCourseService.checkUserEnrolled(username, id);
        model.addAttribute("isEnrolled", isEnrolled);
        model.addAttribute("username", username);

        String avt = userService.getAvtByUsername(username);
        model.addAttribute("avt", avt);

        return "course/course_details";
    }

    @PostMapping("/comment")
    public String postComment(@RequestParam int courseId, @RequestParam int rating, @RequestParam String comment,
                              @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                              @AuthenticationPrincipal OAuth2User userOAuth2) {
        String username = null;

        if (user != null) {
            username = user.getUsername();
        } else if (userOAuth2 != null) {
            username = userOAuth2.getAttribute("name");
        }
        if (username != null) {
            usersCourseService.addOrUpdateReview(username, courseId, rating, comment);
        }

        return "redirect:/course/details/" + courseId;
    }

    @GetMapping("chapterLessonList")
    public String viewChapterLesson(@RequestParam int courseId,Model model){
        List<Chapter> chapters = lessonService.getChaptersByCourseId(courseId);
        Course course = courseService.getCourseById(courseId);
        model.addAttribute("course",course);
        model.addAttribute("chapters",chapters);
        return "/course/chapter-lesson-list";
    }

    @GetMapping("/lesson")
    public String viewLessonInChapter(@RequestParam int lessonId,@RequestParam int chapterId,Model model){
        Chapter chapter = chapterService.getChapterById(chapterId);
        Lesson lesson = lessonService.getLessonById(lessonId);
        model.addAttribute("lesson",lesson);
        model.addAttribute("chapter",chapter);
        return "/course/lesson-detail";
    }

}
