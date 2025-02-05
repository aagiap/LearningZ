package com.project.learningz.service;

import com.project.learningz.entity.Course;
import com.project.learningz.repository.CourseRepository;
import com.project.learningz.repository.UserRepository;
import com.project.learningz.specification.CourseSpecification;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public Page<Course> getCoursesPagingByKeyword(String keyword, Pageable pageable) {
        Specification<Course> spec = CourseSpecification.getAllSpec();
        if (StringUtils.isNotEmpty(keyword)) {
            spec = spec.and(CourseSpecification.byKeywordSpec(keyword));
        }
        return courseRepository.findAll(spec, pageable);
    }

    public Page<Course> getCoursesPagingByKeywordNGradeId(int gradeId, String keyword, Pageable pageable) {
        Specification<Course> spec = CourseSpecification.getAllSpec();
        if (gradeId > 0) {
            spec = spec.and(CourseSpecification.byGradeId(gradeId));
        }
        if (StringUtils.isNotEmpty(keyword)) {
            spec = spec.and(CourseSpecification.byKeywordSpec(keyword));
        }
        return courseRepository.findAll(spec, pageable);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<String> findDistinctSubject() {
        return courseRepository.findDistinctSubject();
    }

}
