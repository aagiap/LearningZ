package com.project.learningz.controller;

import com.project.learningz.entity.Course;
import com.project.learningz.entity.User;
import com.project.learningz.entity.Grade;
import com.project.learningz.repository.CourseRepository;
import com.project.learningz.repository.UserRepository;
import com.project.learningz.service.GradeService;

import com.project.learningz.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/home")
    public String homePage(Model model, HttpSession session) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        String avatarUrl = "/static/image/AvartaDefault.jpg";

        if (principal instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) principal;
            String email = oAuth2User.getAttribute("email");
            User user = userService.findByEmail(email);
            if (user != null) {
                avatarUrl = user.getAvtUrl() != null ? user.getAvtUrl() : avatarUrl;
                username = user.getUsername();
            }
        } else {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(username);
            if (user == null) {
                user = userService.findByEmail(username);
            }
            if (user != null) {
                avatarUrl = user.getAvtUrl() != null ? user.getAvtUrl() : avatarUrl;
            }
        }

        Integer countUpdate = (Integer) session.getAttribute("countUpdate");
        Integer idReload = (Integer) session.getAttribute("idReload");
        if (countUpdate != null && idReload != null) {
            User user = userService.findById(idReload);
            if (user != null) {
                model.addAttribute("username", user.getUsername());
                model.addAttribute("avatarUrl", user.getAvtUrl());
            }
            session.invalidate();
        }

        List<Grade> grades = gradeService.getAllGrades();
        List<Course> courses = courseRepository.findAll();

        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("grades", grades);
        model.addAttribute("courses", courses);

        return "home/home";
    }
}
