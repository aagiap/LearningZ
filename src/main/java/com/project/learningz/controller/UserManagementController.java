package com.project.learningz.controller;

import com.project.learningz.constant.Role;
import com.project.learningz.entity.User;
import com.project.learningz.repository.UserManagementRepository;
import com.project.learningz.service.UserManagementService;
import com.project.learningz.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users")

public class UserManagementController {
    @Autowired
    private UserManagementService userManagementService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserManagementRepository userManagementRepository;
    @Autowired
    private UserService userService;


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

        return "user_management/user_list";
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
        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("sumOfUsers", sumOfUsers);
        model.addAttribute("users", usersLatest);
        model.addAttribute("sumOfSuperAdmin", sumOfSuperAdmin);
        model.addAttribute("sumOfVipUsers", sumOfVipUsers);
        model.addAttribute("sumOfCasualUsers", sumOfCasualUsers);
        model.addAttribute("sumOfTeacherUsers", sumOfTeacherUsers);
        model.addAttribute("sumOfMarketerUsers", sumOfMarketerUsers);
        model.addAttribute("sumOfAdminCourses", sumOfAdminCourses);
        model.addAttribute("sumOfAdminUsers", sumOfAdminUsers);
        return "user_management/user_dashboard";
    }

    @PostMapping("/ban")
    public String banUser(
            @RequestParam(value = "id") Integer id,
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

        return "redirect:/admin/users/userlist_role?role=" + (role != null ? role : "") +
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
        return "user_management/user_create";
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
            return "redirect:/admin/users/userlist_role?role=" + (role != null ? role : "") +
                    "&sort=" + sort +
                    "&order=" + order +
                    "&keyword=" + (keyword != null ? keyword : "");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/users/create";
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
                    return "redirect:/admin/users/" + user.getId();
                }
                updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            } else {
                updatedUser.setPassword(userOpt.get().getPassword());
            }
            userManagementService.updateUser(updatedUser);
        }
        return "redirect:/admin/users/userlist_role?role=" + (role != null ? role : "") +
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
            return "user_management/user_detail";
        } else {
            model.addAttribute("error", "User not found");
            return "redirect:/admin/users/dashboard";
        }
    }

    @PutMapping("/ban")
    public ResponseEntity<String> banUser(@RequestParam("userId") Integer userId) {
        boolean banned = userService.banUserById(userId);
        User user = userService.getUserById(userId);
        if (banned) {
            return ResponseEntity.ok("Banned User " + user.getUsername() + " successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot banned this User!");
        }
    }

}
