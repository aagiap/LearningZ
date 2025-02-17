package com.project.learningz.controller;

import com.project.learningz.dto.CourseDetailsDTO;
import com.project.learningz.entity.Course;
import com.project.learningz.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ExpertController {

    @Autowired
    private CourseService courseService;

    @GetMapping(path = "/expert")
    public String courseList(Model model) {
        List<CourseDetailsDTO> courseList = new ArrayList<>();
        courseList = courseService.allCoursesByUserID(5);
        if(courseList.isEmpty()){
            model.addAttribute("notification","Empty List");
        }else{
            model.addAttribute("courseList",courseList);
        }

        return "/expertPage/courseListExpert";
    }

    @GetMapping(path = "/expert/search")
    public String courseListSearch(Model model,
                                   String courseSearchKey,
                                   String subject) {
        List<CourseDetailsDTO> courseList = new ArrayList<>();

        if (courseSearchKey != null) {
            courseList = courseService.findCourses(5, subject, courseSearchKey);
        }else{
            courseList = courseService.findCourses(5, subject, "");
        }
        if(courseList.isEmpty()){
            model.addAttribute("notification","Empty List");
        }else{
            model.addAttribute("courseList",courseList);
        }
        return "/expertPage/courseListExpert";
    }

    @PostMapping(path = "/expert/edit")
    public String editCourse(Model model,
                             @RequestParam("courseId") int courseId){
        Course course = courseService.findByCourseId(courseId);
        model.addAttribute("course",course);
        return "/expertPage/editCourse";
    }

    @PostMapping(path = "/expert/edit/update")
    public String updateCourse(Model model,
                               @RequestParam("id") int id,
                               @RequestParam("createdByUserId") int createdByUseID,
                               @RequestParam("courseDriveLink") String courseDriveLink,
                               @RequestParam("title") String title,
                               @RequestParam("subject") String subject,
                               @RequestParam("gradeId") int gradeId,
                               @RequestParam("description") String description){
        if(title.isEmpty() || title.trim().isEmpty()){
            model.addAttribute("error","Please fill title");
            model.addAttribute("course",courseService.findByCourseId(id));
        }else{
            courseService.updateCourse(id, createdByUseID, courseDriveLink, title,
                    subject, gradeId, description);
            model.addAttribute("course",courseService.findByCourseId(id));
            model.addAttribute("notification","Update Course Successfully");
        }
        return "/expertPage/editCourse";
    }
}
