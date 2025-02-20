package com.project.learningz.dto;

import com.project.learningz.constant.QuizType;

public class LessonDetailDTO {
    private Integer lessonId;
    private Integer chapterId;
    private String lessonTitle;
    private QuizType quizType;
    private String description;
    private String lessonDriveLink;
    private String documentFolderLink;
    private String videoFolderLink;
    private String quizImageLink;
    private Long numberOfVideos;
    private Long numberOfDocs;

    public LessonDetailDTO() {
    }

    public LessonDetailDTO(Integer lessonId, Integer chapterId, String lessonTitle,
                           QuizType quizType, String description, String lessonDriveLink,
                           String documentFolderLink, String videoFolderLink, String quizImageLink,
                           Long numberOfVideos, Long numberOfDocs) {
        this.lessonId = lessonId;
        this.chapterId = chapterId;
        this.lessonTitle = lessonTitle;
        this.quizType = quizType;
        this.description = description;
        this.lessonDriveLink = lessonDriveLink;
        this.documentFolderLink = documentFolderLink;
        this.videoFolderLink = videoFolderLink;
        this.quizImageLink = quizImageLink;
        this.numberOfVideos = numberOfVideos;
        this.numberOfDocs = numberOfDocs;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public QuizType getQuizType() {
        return quizType;
    }

    public void setQuizType(QuizType quizType) {
        this.quizType = quizType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLessonDriveLink() {
        return lessonDriveLink;
    }

    public void setLessonDriveLink(String lessonDriveLink) {
        this.lessonDriveLink = lessonDriveLink;
    }

    public String getDocumentFolderLink() {
        return documentFolderLink;
    }

    public void setDocumentFolderLink(String documentFolderLink) {
        this.documentFolderLink = documentFolderLink;
    }

    public String getVideoFolderLink() {
        return videoFolderLink;
    }

    public void setVideoFolderLink(String videoFolderLink) {
        this.videoFolderLink = videoFolderLink;
    }

    public String getQuizImageLink() {
        return quizImageLink;
    }

    public void setQuizImageLink(String quizImageLink) {
        this.quizImageLink = quizImageLink;
    }

    public Long getNumberOfVideos() {
        return numberOfVideos;
    }

    public void setNumberOfVideos(Long numberOfVideos) {
        this.numberOfVideos = numberOfVideos;
    }

    public Long getNumberOfDocs() {
        return numberOfDocs;
    }

    public void setNumberOfDocs(Long numberOfDocs) {
        this.numberOfDocs = numberOfDocs;
    }
}
