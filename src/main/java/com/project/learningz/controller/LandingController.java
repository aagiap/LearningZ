package com.project.learningz.controller;

import com.project.learningz.entity.*;
import com.project.learningz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Map;

@Controller
public class LandingController {
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

    @GetMapping("/")
    public String landingPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }
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
        return "/home/landing";
    }
}
