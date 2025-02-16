package com.project.learningz.controller;

import com.project.learningz.entity.User;
import com.project.learningz.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public String searchUsers(@RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "sort", defaultValue = "id") String sortField,
                              @RequestParam(value = "order", defaultValue = "asc") String order,
                              Model model) {
        List<User> users;
        if (keyword != null && !keyword.trim().isEmpty()) {
            users = userManagementService.searchUsersSorted(keyword, sortField, order);
        } else {
            users = userManagementService.getAllUsersSorted(sortField, order);
        }
        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortField", sortField);
        model.addAttribute("order", order);
        return "user_management/user_list";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam(value = "id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            userManagementService.deleteUserById(id);
            redirectAttributes.addFlashAttribute("successMessage", "User has deleted successfully!");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/create")
    public String createUser(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("email") String email,
                             @RequestParam("phone") String phone,
                             @RequestParam("role") String role,
                             RedirectAttributes redirectAttributes) {
        try {
            userManagementService.createUser(username, email, password, phone, role);
            redirectAttributes.addFlashAttribute("successMessage", "User has created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/users";
    }


    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = userManagementService.getUserById(user.getId());
        if (userOpt.isPresent()) {
            User updatedUser = userOpt.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPhoneNum(user.getPhoneNum());
            updatedUser.setRole(user.getRole());
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
        return "redirect:/admin/users";
    }


    @GetMapping("/{id}")
    public String showUserDetail(@PathVariable Integer id, Model model) {
        Optional<User> userOp = userManagementService.getUserById(id);
        if (userOp.isPresent()) {
            User user = userOp.get();
            model.addAttribute("user", user);
            return "user_management/user_detail";
        } else {
            model.addAttribute("error", "User not found");
            return "redirect:/admin/users";
        }
    }


}
