package com.project.learningz.dto;

public class ChapterDetailDTO {
    private Integer id;
    private Integer chapterOrder;
    private String courseTitle;
    private String chapterTitle;
    private String description;
    private String chapterDriveLink;
    private Long numberOfLessons;

    public ChapterDetailDTO() {
    }

    public ChapterDetailDTO(Integer id, Integer chapterOrder,
                            String courseTitle, String chapterTitle,
                            String description, String chapterDriveLink,
                            Long numberOfLessons) {
        this.id = id;
        this.chapterOrder = chapterOrder;
        this.courseTitle = courseTitle;
        this.chapterTitle = chapterTitle;
        this.description = description;
        this.chapterDriveLink = chapterDriveLink;
        this.numberOfLessons = numberOfLessons;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChapterOrder() {
        return chapterOrder;
    }

    public void setChapterOrder(Integer chapterOrder) {
        this.chapterOrder = chapterOrder;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChapterDriveLink() {
        return chapterDriveLink;
    }

    public void setChapterDriveLink(String chapterDriveLink) {
        this.chapterDriveLink = chapterDriveLink;
    }

    public Long getNumberOfLessons() {
        return numberOfLessons;
    }

    public void setNumberOfLessons(Long numberOfLessons) {
        this.numberOfLessons = numberOfLessons;
    }
}
