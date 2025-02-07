package com.project.learningz.dto;

import java.io.Serializable;
import java.util.List;

public class QuizSubmitionListDTO implements Serializable {
    private List<QuizSubmitionDTO> answers;

    public QuizSubmitionListDTO(List<QuizSubmitionDTO> answers) {
        this.answers = answers;
    }

    public List<QuizSubmitionDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QuizSubmitionDTO> answers) {
        this.answers = answers;
    }
}
