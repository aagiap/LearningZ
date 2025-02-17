package com.project.learningz.dto;

public class CourseDetailsDTO {
    private Integer id;
    private String createdByUsername;
    private String gradeName;
    private String subject;
    private String title;
    private String description;
    private String courseDriveLink;
    private Long numberOfLessons;
    private Long numberOfChapters;

    public CourseDetailsDTO() {
    }

    public CourseDetailsDTO(Integer id, String createdByUsername, String gradeName, String subject, String title, String description, String courseDriveLink, Long numberOfLessons, Long numberOfChapters) {
        this.id = id;
        this.createdByUsername = createdByUsername;
        this.gradeName = gradeName;
        this.subject = subject;
        this.title = title;
        this.description = description;
        this.courseDriveLink = courseDriveLink;
        this.numberOfLessons = numberOfLessons;
        this.numberOfChapters = numberOfChapters;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public void setCreatedByUsername(String createdByUsername) {
        this.createdByUsername = createdByUsername;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseDriveLink() {
        return courseDriveLink;
    }

    public void setCourseDriveLink(String courseDriveLink) {
        this.courseDriveLink = courseDriveLink;
    }

    public Long getNumberOfLessons() {
        return numberOfLessons;
    }

    public void setNumberOfLessons(Long numberOfLessons) {
        this.numberOfLessons = numberOfLessons;
    }

    public Long getNumberOfChapters() {
        return numberOfChapters;
    }

    public void setNumberOfChapters(Long numberOfChapters) {
        this.numberOfChapters = numberOfChapters;
    }
}
