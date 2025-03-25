package com.project.learningz.dto;

public class CourseLearnedStatsDTO {
    private String courseTitle;
    private long numberOfStudents;

    public CourseLearnedStatsDTO() {
    }

    public CourseLearnedStatsDTO(String courseTitle, long numberOfStudents) {
        this.courseTitle = courseTitle;
        this.numberOfStudents = numberOfStudents;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public long getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(long numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
}
