package com.project.learningz.dto;

import java.io.Serializable;

public class QuizJoinToGradeDTO implements Serializable {
    private Integer quizId;
    private Integer totalQuestions;
    private Integer timeLimit;
    private String title;
    private String gradeDescription;
    private String subject;

    public QuizJoinToGradeDTO(Integer quizId, Integer totalQuestions, Integer timeLimit, String title, String gradeDescription, String subject) {
        this.quizId = quizId;
        this.totalQuestions = totalQuestions;
        this.timeLimit = timeLimit;
        this.title = title;
        this.gradeDescription = gradeDescription;
        this.subject = subject;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGradeDescription() {
        return gradeDescription;
    }

    public void setGradeDescription(String gradeDescription) {
        this.gradeDescription = gradeDescription;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
