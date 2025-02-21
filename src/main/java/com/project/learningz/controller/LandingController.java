package com.project.learningz.controller;

import com.project.learningz.entity.Course;
import com.project.learningz.entity.Grade;
import com.project.learningz.entity.Slider;
import com.project.learningz.service.CourseService;
import com.project.learningz.service.GradeService;
import com.project.learningz.service.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class LandingController {
    @Autowired
    private GradeService gradeService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SliderService sliderService;
    @GetMapping("/")
    public String landingPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }
        List<Slider> sliders = sliderService.getAllSliders();
        model.addAttribute("sliders", sliders);
        List<Grade> grades = gradeService.getAllGrades();
        model.addAttribute("grades", grades);
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "/home/landing";
    }
}
