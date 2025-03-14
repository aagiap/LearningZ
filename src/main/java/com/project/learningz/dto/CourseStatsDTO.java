package com.project.learningz.dto;

public class CourseStatsDTO {
    private String courseTitle;
    private double courseScore;

    public CourseStatsDTO() {
    }

    public CourseStatsDTO(String courseTitle, double courseScore) {
        this.courseTitle = courseTitle;
        this.courseScore = courseScore;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public double getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(double courseScore) {
        this.courseScore = courseScore;
    }
}
