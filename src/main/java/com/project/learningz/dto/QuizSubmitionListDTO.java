package com.project.learningz.dto;

import java.util.List;

public class QuizSubmitionListDTO {
    private List<QuizSubmitionDTO> answers;

    public List<QuizSubmitionDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QuizSubmitionDTO> answers) {
        this.answers = answers;
    }
}
