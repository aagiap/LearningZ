package com.project.learningz.service;

import com.project.learningz.dto.QuizSubmitionDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizReviewService {

    public int countCorrectAnswers(List<QuizSubmitionDTO> answers) {
        int correctAnswers = 0;
        for (QuizSubmitionDTO answer : answers) {
            if (answer.getSelectedOption() != null && answer.getSelectedOption().equals(answer.getCorrectAnswer())) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    public int countAnsweredQuestions(List<QuizSubmitionDTO> answers) {
        int answeredQuestions = 0;
        for (QuizSubmitionDTO answer : answers) {
            if (answer.getSelectedOption() != null) {
                answeredQuestions++;
            }
        }
        return answeredQuestions;
    }

    public int countTotalQuestions(List<QuizSubmitionDTO> answers) {
        return answers.size();
    }

    public float calculateScore(int totalQuestions, int correctAnswers) {
        return Math.round((float) correctAnswers / totalQuestions * 1000) / 100.0f;
    }

    public List<QuizSubmitionDTO> setWrongSelections(List<QuizSubmitionDTO> answers) {
        for (QuizSubmitionDTO answer : answers) {
            if (answer.getSelectedOption() != null && !answer.getSelectedOption().equals(answer.getCorrectAnswer()) || answer.getSelectedOption() == null) {
                answer.setWrongSelection(answer.getSelectedOption());
            }
        }
        return answers;
    }

    public List<String> getResultQuestion(List<QuizSubmitionDTO> answers) {
        List<String> failedAnswers = new ArrayList<>();
        for (QuizSubmitionDTO answer : answers) {
            if (answer.getSelectedOption() != null && !answer.getSelectedOption().equals(answer.getCorrectAnswer())) {
                failedAnswers.add("False");
            }else if(answer.getSelectedOption() == null){
                failedAnswers.add("Not Answered");
            }else {
                failedAnswers.add("True");
            }
        }
        return failedAnswers;
    }


}
