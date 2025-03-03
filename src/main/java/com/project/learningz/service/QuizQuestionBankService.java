package com.project.learningz.service;

import com.project.learningz.entity.QuestionBank;
import com.project.learningz.repository.QuizQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizQuestionBankService {
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;


    public List<QuestionBank> findQuestionBankByQuizId(Integer quizId) {
        List<QuestionBank> questionBankList = quizQuestionRepository.findQuestionBankByQuizId(quizId);
        Collections.shuffle(questionBankList);
        suffeleOption(questionBankList);
        return questionBankList;
    }

    private void suffeleOption(List<QuestionBank> questionBankList) {

        for (QuestionBank questionBank : questionBankList) {
            String option1 = questionBank.getOption1();
            String option2 = questionBank.getOption2();
            String option3 = questionBank.getOption3();
            String option4 = questionBank.getOption4();
            List<String> option = new ArrayList<>(Arrays.asList(option1, option2, option3, option4));
            Collections.shuffle(option);
            questionBank.setOption1(option.get(0));
            questionBank.setOption2(option.get(1));
            questionBank.setOption3(option.get(2));
            questionBank.setOption4(option.get(3));
        }
    }
}
