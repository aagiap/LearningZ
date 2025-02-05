package com.project.learningz.dto;

public class QuizSubmitionDTO {
    private Integer questionId;
    private String selectedOption;
    private String correctAnswer;

    public QuizSubmitionDTO() {
    }

    public QuizSubmitionDTO(Integer questionId, String selectedOption, String correctAnswer) {
        this.questionId = questionId;
        this.selectedOption = selectedOption;
        this.correctAnswer = correctAnswer;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
