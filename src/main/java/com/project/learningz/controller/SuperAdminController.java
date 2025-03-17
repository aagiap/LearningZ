package com.project.learningz.controller;

import com.project.learningz.constant.CourseStatus;
import com.project.learningz.constant.Role;
import com.project.learningz.dto.TopCourseDTO;
import com.project.learningz.entity.Course;
import com.project.learningz.entity.User;
import com.project.learningz.repository.CourseRepository;
import com.project.learningz.service.CourseService;
import com.project.learningz.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/super_admin")
public class SuperAdminController {

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/userlist_role")
    public String getUsers(@RequestParam(value = "role", required = false) String roleStr,
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

        List<User> users;
        Role role;
        try {
            role = Role.valueOf(roleStr.toUpperCase());
            users = userManagementService.searchUsersSorted(role, keyword, sortField, order);
        } catch (IllegalArgumentException e) {
            users = userManagementService.getAllUsersByKeyword(keyword, sortField, order);
        }


        // Gửi dữ liệu tới giao diện
        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortField", sortField);
        model.addAttribute("order", order);
        model.addAttribute("role", roleStr);

        return "user_management/admin_user_list";
    }

    @PostMapping("/ban")
    public String banUser(@RequestParam(value = "id") Integer id,
                          @RequestParam(value = "role", required = false) String role,
                          @RequestParam(value = "sort", required = false, defaultValue = "role") String sort,
                          @RequestParam(value = "order", required = false, defaultValue = "asc") String order,
                          @RequestParam(value = "keyword", required = false) String keyword,
                          RedirectAttributes redirectAttributes) {
        try {
            userManagementService.banUserById(id);
            redirectAttributes.addFlashAttribute("successMessage", "User has been banned successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/super_admin/userlist_role?role=" + (role != null ? role : "") +
                "&sort=" + sort +
                "&order=" + order +
                "&keyword=" + (keyword != null ? keyword : "");
    }


    @GetMapping("/create")
    public String showCreateForm(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                 @AuthenticationPrincipal OAuth2User userOAuth2,
                                 Model model) {
        String usernameCurrent = null;

        if (user != null) {
            usernameCurrent = user.getUsername();
        } else if (userOAuth2 != null) {
            String emailCurrent = userOAuth2.getAttribute("email");
            usernameCurrent = userManagementService.findUserNameByEmail(emailCurrent);
        }
        String avatarUrl = userManagementService.getAvtByUsername(usernameCurrent);
        if (avatarUrl == null) {
            avatarUrl = "/image/AvartaDefault.jpg";
        }
        model.addAttribute("username", usernameCurrent);
        model.addAttribute("avatarUrl", avatarUrl);
        return "user_management/admin_create_user";
    }

    @PostMapping("/create")
    public String createUser(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("email") String email,
                             @RequestParam("phone") String phone,
                             @RequestParam("role") String role,
                             @RequestParam(value = "sort", required = false, defaultValue = "role") String sort,
                             @RequestParam(value = "order", required = false, defaultValue = "asc") String order,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             RedirectAttributes redirectAttributes) {
        try {
            userManagementService.createUser(username, email, password, phone, role);
            redirectAttributes.addFlashAttribute("successMessage", "User has created successfully!");
            return "redirect:/super_admin/userlist_role?role=" + (role != null ? role : "") +
                    "&sort=" + sort +
                    "&order=" + order +
                    "&keyword=" + (keyword != null ? keyword : "");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/super_admin/create";
        }
    }


    @PostMapping("/update")
    public String updateUser(@RequestParam(value = "role", required = false) String role,
                             @RequestParam(value = "sort", required = false, defaultValue = "role") String sort,
                             @RequestParam(value = "order", required = false, defaultValue = "asc") String order,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             @ModelAttribute User user,
                             RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = userManagementService.getUserById(user.getId());
        if (userOpt.isPresent()) {
            User updatedUser = userOpt.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPhoneNum(user.getPhoneNum());
            updatedUser.setRole(user.getRole());
            updatedUser.setUserStatus(user.getUserStatus());
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                if (user.getPassword().length() < 6) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Password must be at least 6 characters");
                    return "redirect:/super_admin/" + user.getId();
                }
                updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            } else {
                updatedUser.setPassword(userOpt.get().getPassword());
            }
            userManagementService.updateUser(updatedUser);
        }
        return "redirect:/super_admin/userlist_role?role=" + (role != null ? role : "") +
                "&sort=" + sort +
                "&order=" + order +
                "&keyword=" + (keyword != null ? keyword : "");
    }


    @GetMapping("/{id}")
    public String showUserDetail(@PathVariable Integer id,
                                 @AuthenticationPrincipal org.springframework.security.core.userdetails.User userCurrent,
                                 @AuthenticationPrincipal OAuth2User userOAuth2,
                                 Model model) {

        String username = null;

        if (userCurrent != null) {
            username = userCurrent.getUsername();
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userManagementService.findUserNameByEmail(email);
        }
        String avatarUrl = userManagementService.getAvtByUsername(username);
        if (avatarUrl == null) {
            avatarUrl = "/image/AvartaDefault.jpg";
        }
        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);

        Optional<User> userOp = userManagementService.getUserById(id);
        if (userOp.isPresent()) {
            User user = userOp.get();
            model.addAttribute("user", user);
            return "user_management/admin_user_detail";
        } else {
            model.addAttribute("error", "User not found");
            return "redirect:/super_admin/dashboard";
        }
    }

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

        return "user_management/admin_course_list";
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

        long sumOfUsers = userManagementService.getNumberOfUsers();
        long sumOfSuperAdmin = userManagementService.getNumberOfAdminSuperUsers();
        long sumOfAdminUsers = userManagementService.getNumberOfAdminUserManageUsers();
        long sumOfAdminCourses = userManagementService.getNumberOfAdminCourseManageUsers();
        long sumOfVipUsers = userManagementService.getNumberOfVipUsers();
        long sumOfCasualUsers = userManagementService.getNumberOfCasualStudentUsers();
        long sumOfTeacherUsers = userManagementService.getNumberOfTeacherUsers();
        long sumOfMarketerUsers = userManagementService.getNumberOfMarketerUsers();
        List<User> usersLatest = userManagementService.getTenLatestUsers();

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
        model.addAttribute("sumOfUsers", sumOfUsers);
        model.addAttribute("users", usersLatest);
        model.addAttribute("sumOfSuperAdmin", sumOfSuperAdmin);
        model.addAttribute("sumOfVipUsers", sumOfVipUsers);
        model.addAttribute("sumOfCasualUsers", sumOfCasualUsers);
        model.addAttribute("sumOfTeacherUsers", sumOfTeacherUsers);
        model.addAttribute("sumOfMarketerUsers", sumOfMarketerUsers);
        model.addAttribute("sumOfAdminCourses", sumOfAdminCourses);
        model.addAttribute("sumOfAdminUsers", sumOfAdminUsers);
        return "user_management/admin_manage";
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
        System.out.println(feedbackInput);
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

        return "redirect:/super_admin/view?status=" + status +
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
        return "user_management/admin_course_detail";
    }

}
