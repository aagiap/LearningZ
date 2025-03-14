package com.project.learningz.dto;

public class QuizDetailDTO {
    private Integer quizId;
    private Integer totalQuestions;
    private Integer timeLimit;
    private String title;
    private String courseTitle;
    private String subject;
    private String chapterTitle;
    private String lessonTitle;
    private String grade;

    public QuizDetailDTO() {
    }

    public QuizDetailDTO(Integer quizId, Integer totalQuestions, Integer timeLimit, String title, String courseTitle, String subject, String chapterTitle, String lessonTitle, String grade) {
        this.quizId = quizId;
        this.totalQuestions = totalQuestions;
        this.timeLimit = timeLimit;
        this.title = title;
        this.courseTitle = courseTitle;
        this.subject = subject;
        this.chapterTitle = chapterTitle;
        this.lessonTitle = lessonTitle;
        this.grade = grade;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
