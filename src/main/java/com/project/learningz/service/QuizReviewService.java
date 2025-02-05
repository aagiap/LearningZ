package com.project.learningz.service;

import com.project.learningz.dto.QuizSubmitionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizReviewService {

    public int countCorrectAnswers(List<QuizSubmitionDTO> answers) {
        int correctAnswers = 0;
        for (QuizSubmitionDTO answer : answers) {
            if (answer.getSelectedOption().equals(answer.getCorrectAnswer())) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    public int countTotalQuestions(List<QuizSubmitionDTO> answers) {
        return answers.size();
    }

    public float calculateScore(int totalQuestions, int correctAnswers) {
       return Math.round((float) correctAnswers / totalQuestions * 1000) / 100.0f;
    }


}
