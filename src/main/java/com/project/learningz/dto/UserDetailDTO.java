package com.project.learningz.dto;

public class UserDetailDTO {
    private int id;
    private String username;
    private String courseTitle;

    public UserDetailDTO() {
    }

    public UserDetailDTO(int id, String username, String courseTitle) {
        this.id = id;
        this.username = username;
        this.courseTitle = courseTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
}
