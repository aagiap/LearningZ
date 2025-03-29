package com.project.learningz.controller;

import com.project.learningz.constant.CourseStatus;
import com.project.learningz.constant.Role;
import com.project.learningz.dto.CourseReviewDTO;
import com.project.learningz.entity.*;
import com.project.learningz.service.*;
import com.project.learningz.utils.PageWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.Collections;
import java.util.HashMap;
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
    @Autowired
    private QuizResultService quizResultService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private SubjectService subjectService;


    @GetMapping("")
    public String viewCourse(Model model,
                             @RequestParam(name = "keyword", defaultValue = "") String keyword,
                             @RequestParam(name = "gradeId", defaultValue = "-1") int gradeId,
                             @RequestParam(name = "subjectId", defaultValue = "-1") int subjectId,
                             @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                             @RequestParam(name = "pageSize", defaultValue = "4") int pageSize,
                             @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                             @AuthenticationPrincipal OAuth2User userOAuth2) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("title").ascending());
        Page<Course> pageCourse = courseService.getCoursesPaging(gradeId, subjectId, keyword, pageable);
        Map<Integer, Double> averageRatings = usersCourseService.getAverageRatingByCourse();

        PageWrapper<Course> response = new PageWrapper<>(pageCourse, "/course");
        model.addAttribute("courses", pageCourse.getContent());
        model.addAttribute("averageRatings", averageRatings);
        model.addAttribute("page", response);
        model.addAttribute("keyword", keyword);
        model.addAttribute("gradeId", gradeId);
        model.addAttribute("subjectId", subjectId);


        List<Grade> grades = gradeService.getAllGrades();
        model.addAttribute("grades", grades);

        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        //lấy avtUrl và username
        String username = null;
        if (user != null) {
            username = user.getUsername();
            model.addAttribute("user", user);
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userService.findUserNameByEmail(email);
            model.addAttribute("user", userOAuth2);
        }
        model.addAttribute("username", username);
        String avatarUrl = userService.getAvtByUsername(username);
        model.addAttribute("avatarUrl", avatarUrl);

        return "course/course_list";
    }

    @GetMapping("/details/{id}")
    public String viewCourseDetails(@PathVariable("id") Integer courseId, Model model,
                                    @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                    @AuthenticationPrincipal OAuth2User userOAuth2) {
        Course course = courseService.getCourseById(courseId);
        if(course.getCourseStatus() != CourseStatus.ACTIVE){
            return "redirect:/course";
        }
        model.addAttribute("course", course);

        Map<Integer, Double> averageRatings = usersCourseService.getAverageRatingByCourse();
        model.addAttribute("averageRatings", averageRatings);

        int numberOfStudents = usersCourseService.numberOfStudentsInCourse(courseId);
        model.addAttribute("numberOfStudents", numberOfStudents);

        int lessonCount = courseService.getLessonCountByCourseId(courseId);
        model.addAttribute("lessonCount", lessonCount);

        List<CourseReviewDTO> reviews = usersCourseService.getCourseReviews(courseId);
        model.addAttribute("reviews", reviews);

        List<Grade> grades = gradeService.getAllGrades();
        model.addAttribute("grades", grades);

        // Kiểm tra user và userOAuth2
        String username = null;

        if (user != null) {
            username = user.getUsername();
            model.addAttribute("user", user);
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userService.findUserNameByEmail(email);
            model.addAttribute("user", userOAuth2);
        }

        boolean isEnrolled = (username != null) && usersCourseService.checkUserEnrolled(username, courseId);
        Integer userId = userService.getUserIdByUsername(username);
        boolean checkConditionFeedBack = usersCourseService.checkConditionFeedback(userId, course.getId());

        boolean checkIsFeeback = usersCourseService.checkIsFeeback(userId, course.getId());
        model.addAttribute("checkIsFeeback", checkIsFeeback);

        model.addAttribute("checkConditionFeedBack", checkConditionFeedBack);

        CourseReviewDTO userFeedback = usersCourseService.getUserFeedback(userId, courseId);
        model.addAttribute("userFeedback", userFeedback);

        model.addAttribute("isEnrolled", isEnrolled);
        model.addAttribute("username", username);

        String avatarUrl = userService.getAvtByUsername(username);
        model.addAttribute("avatarUrl", avatarUrl);

        boolean isNormalStudent = userService.isNormalStudent(userId, Role.STUDENT);
        model.addAttribute("isNormalStudent", isNormalStudent);

        int numberOfFeedbacks = usersCourseService.countReviewByCourseId(courseId);
        model.addAttribute("numberOfFeedbacks", numberOfFeedbacks);

        Integer numberOfVideos = courseService.numberOfVideos(courseId);
        Integer numberOfPDFs = courseService.numberOfPDFs(courseId);
        Integer numberOfChapters = courseService.numberOfChapter(courseId);
        model.addAttribute("numberOfChapters", numberOfChapters);
        model.addAttribute("numberOfVideos", numberOfVideos);
        model.addAttribute("numberOfPDFs", numberOfPDFs);
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
            String email = userOAuth2.getAttribute("email");
            username = userService.findUserNameByEmail(email);
        }
        if (username != null) {
            usersCourseService.addOrUpdateReview(username, courseId, rating, comment);
        }

        return "redirect:/course/details/" + courseId;
    }

    @PutMapping("/feedback/clear")
    public ResponseEntity<String> clearFeedback(@RequestParam("userId") Integer userId,
                                                @RequestParam("courseId") Integer courseId) {
        boolean cleared = usersCourseService.clearFeedback(userId, courseId);
        if (cleared) {
            return ResponseEntity.ok("Feedback deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot clear feedback!");
        }
    }


    @PostMapping("/comment/edit")
    public String updateFeedback(@RequestParam("courseId") Integer courseId,
                                 @RequestParam(value = "rating", required = false) Integer rating,
                                 @RequestParam(value = "comment", required = false) String comment,
                                 @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                 @AuthenticationPrincipal OAuth2User userOAuth2,
                                 RedirectAttributes redirectAttributes) {

        String username = null;
        if (user != null) {
            username = user.getUsername();
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userService.findUserNameByEmail(email);
        }
        Integer userId = userService.getUserIdByUsername(username);

        usersCourseService.updateFeedback(userId, courseId, rating, comment);


        return "redirect:/course/details/" + courseId;
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> enrollCourse(@RequestParam("courseId") Integer courseId,
                                          @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                          @AuthenticationPrincipal OAuth2User userOAuth2) {
        String username = null;

        if (user != null) {
            username = user.getUsername();
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userService.findUserNameByEmail(email);
        }

        Integer userId = userService.getUserIdByUsername(username);
        Role role = userService.getRoleById(userId);

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error1", "You must be logged in to enroll in a course."));
        }

        if (role == Role.STUDENT) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of(
                            "error2", "Please register for VIP membership before enrolling in a course.",
                            "action", "Click OK to go to VIP Membership register page."
                    ));
        }


        if (role == Role.ADMIN || role == Role.ADMIN_COURSE_MANAGER || role == Role.ADMIN_STUDENT_MANAGER || role == Role.MARKETING_TEAM || role == Role.TEACHER) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("error3", "Register for a course only available with STUDENT"));
        }

        usersCourseService.enrollCourse(userId, courseId);
        return ResponseEntity.ok(Collections.singletonMap("success", "Enrollment successful!"));
    }


}
