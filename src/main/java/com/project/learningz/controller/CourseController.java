package com.project.learningz.controller;

import com.project.learningz.entity.Course;
import com.project.learningz.service.CourseService;
import com.project.learningz.service.UsersCourseService;
import com.project.learningz.utils.PageWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Map;

@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final UsersCourseService usersCourseService;

    @GetMapping("")
    public String viewCourse(Model model,
                             @RequestParam(name = "keyword", defaultValue = "") String keyword,
                             @RequestParam(name = "gradeId", defaultValue = "-1") int gradeId,
                             @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                             @RequestParam(name = "pageSize", defaultValue = "8") int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Course> pageCourse = courseService.getCoursesPagingByKeywordNGradeId(gradeId, keyword, pageable);
        Map<Integer, Double> averageRatings = usersCourseService.getAverageRatingByCourse();

        PageWrapper<Course> response = new PageWrapper<>(pageCourse, "/course");
        model.addAttribute("courses", pageCourse.getContent());
        model.addAttribute("averageRatings", averageRatings);
        model.addAttribute("page", response);
        model.addAttribute("keyword", keyword);
        model.addAttribute("gradeId", gradeId);

        return "course/course_list";
    }
}
