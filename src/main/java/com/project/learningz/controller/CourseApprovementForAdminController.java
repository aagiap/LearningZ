package com.project.learningz.controller;

import com.project.learningz.constant.CourseStatus;
import com.project.learningz.dto.TopCourseDTO;
import com.project.learningz.entity.Course;
import com.project.learningz.service.CourseService;
import com.project.learningz.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/courses")
public class CourseApprovementForAdminController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserManagementService userManagementService;

    @GetMapping("/view")
    public String viewCourses(@RequestParam(value = "status", required = false) String statusStr,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "sort", defaultValue = "id") String sortField,
                              @RequestParam(value = "order", defaultValue = "asc") String order,
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

        List<Course> courses;
        CourseStatus status;
        try {
            status = CourseStatus.valueOf(statusStr.toUpperCase());
            courses = courseService.searchCourseByStatusAndKeyword(status, keyword, sortField, order);
        } catch (IllegalArgumentException e) {
            courses = courseService.getAllCoursesByKeyword(keyword, sortField, order);
        }

        // Gửi dữ liệu tới giao diện
        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("courses", courses);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortField", sortField);
        model.addAttribute("order", order);
        model.addAttribute("status", statusStr);

        return "user_management/course_approve";
    }

    @GetMapping("/dashboard")
    public String showDashboard(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
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

        Integer sumOfActiveCourses = courseService.sumOfCourseByStatus(CourseStatus.ACTIVE);
        Integer sumOfInactiveCourses = courseService.sumOfCourseByStatus(CourseStatus.INACTIVE);
        Integer sumOfPendingCourses = courseService.sumOfCourseByStatus(CourseStatus.PENDING);
        Integer sumOfRejectedCourses = courseService.sumOfCourseByStatus(CourseStatus.REJECTED);
        Integer totalCourses = courseService.getAllCourses().size();
        List<TopCourseDTO> courses = courseService.getTop5Courses();

        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("sumOfActiveCourses", sumOfActiveCourses);
        model.addAttribute("sumOfInactiveCourses", sumOfInactiveCourses);
        model.addAttribute("sumOfPendingCourses", sumOfPendingCourses);
        model.addAttribute("sumOfRejectedCourses", sumOfRejectedCourses);
        model.addAttribute("totalCourses", totalCourses);
        model.addAttribute("courses", courses);
        return "user_management/course_dashboard";
    }

    @PostMapping("/approve")
    public String approveCourse(
            @RequestParam("id") Integer id,
            @RequestParam(value = "status", required = false, defaultValue = "") String status,
            @RequestParam(value = "order", required = false, defaultValue = "asc") String order,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "feedbackInput", required = false) String feedbackInput,
            @RequestParam("action") String action,
            RedirectAttributes redirectAttributes) {
        try {
            if ("approve".equalsIgnoreCase(action)) {
                courseService.approveCourse(id);
                redirectAttributes.addFlashAttribute("successMessage", "Course has been approved successfully!");
            } else if ("reject".equalsIgnoreCase(action)) {
                Course course = courseService.getCourseById(id);
                course.setNote(feedbackInput);
                courseService.rejectCourse(id);
                courseService.saveCourse(course);
                redirectAttributes.addFlashAttribute("successMessage", "Course has been rejected successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Invalid action!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error processing request: " + e.getMessage());
        }

        return "redirect:/admin/courses/view?status=" + status +
                "&sort=" + sort +
                "&order=" + order +
                "&keyword=" + keyword;
    }


    @GetMapping("/detail/{id}")
    public String showDetailCourse(@PathVariable Integer id,
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

        Course course = courseService.getCourseById(id);

        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("course", course);
        return "user_management/course_detail";
    }

}
