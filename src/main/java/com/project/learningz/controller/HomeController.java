package com.project.learningz.controller;

import com.project.learningz.entity.*;
import com.project.learningz.repository.CourseRepository;
import com.project.learningz.service.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private GradeService gradeService;
    @Autowired
    private SliderService sliderService;
    @Autowired
    private UsersCourseService usersCourseService;
    @Autowired
    private VipPackageService vipPackageService;
    @Autowired
    private UserService userService;

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

        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        List<User> topTeachers = userService.getTopTeachers();
        model.addAttribute("topTeachers", topTeachers);
        Map<Integer, Double> averageRatings = usersCourseService.getAverageRatingByCourse();
        model.addAttribute("averageRatings", averageRatings);
        List<Slider> sliders = sliderService.getAllSliders();
        model.addAttribute("sliders", sliders);
        List<Grade> grades = gradeService.getAllGrades();
        model.addAttribute("grades", grades);
        List<Course> topCourses = usersCourseService.getTopCourses();
        model.addAttribute("topCourses", topCourses);
        List<VipPackage> packages = vipPackageService.getAllVipPackages();
        model.addAttribute("vipPackages", packages);

        return "home/home";
    }
    @GetMapping("/about")
    public String aboutPage(Model model) {
        List<Grade> grades = gradeService.getAllGrades();
        model.addAttribute("grades", grades);
        return "home/about";
    }
}
