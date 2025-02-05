package com.project.learningz.service;

import com.project.learningz.entity.QuestionBank;
import com.project.learningz.repository.QuizQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizQuestionBankService {
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;


    public List<QuestionBank> findQuestionBankByQuizId(Integer quizId) {
        return quizQuestionRepository.findQuestionBankByQuizId(quizId);
    }

}
