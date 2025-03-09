package com.project.learningz.dto;

import com.project.learningz.entity.Course;

public class TopCourseDTO {
    private Course course;
    private Long enrollmentCount;

    public TopCourseDTO() {
    }

    public TopCourseDTO(Course course, Long enrollmentCount) {
        this.course = course;
        this.enrollmentCount = enrollmentCount;
    }

    public Long getEnrollmentCount() {
        return enrollmentCount;
    }

    public void setEnrollmentCount(Long enrollmentCount) {
        this.enrollmentCount = enrollmentCount;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

