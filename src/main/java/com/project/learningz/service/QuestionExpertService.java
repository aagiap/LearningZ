package com.project.learningz.service;

import com.project.learningz.dto.QuestionDetailDTO;
import com.project.learningz.entity.QuestionBank;
import com.project.learningz.entity.Quiz;
import com.project.learningz.entity.QuizQuestion;
import com.project.learningz.entity.QuizQuestionId;
import com.project.learningz.repository.QuestionExpertRepository;
import com.project.learningz.repository.QuizQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionExpertService {

    @Autowired
    QuestionExpertRepository questionExpertRepository;
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    public Page<QuestionDetailDTO> filterQuestionByQuizIdAndKeyword(Integer quizId, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (quizId != null && keyword != null) {
            return questionExpertRepository.searchAllQuestionsByQuizIdKeyword(keyword, quizId, pageable);
        }
        return questionExpertRepository.getQuestionsByQuizId(quizId, pageable);
    }

    public QuestionDetailDTO getQuestionDetail(Integer questionId) {
        return questionExpertRepository.getDetailQuestions(questionId);
    }

    public void deleteQuestion(Integer questionId) {
        try{
            questionExpertRepository.deleteById(questionId);
        } catch (Exception e) {
            throw new RuntimeException("Cannot delete question because of " + e.getMessage());
        }
    }

    public void createQuestion(String content, String correctOption, String option1, String option2, String option3, String option4, Quiz quiz) {
        QuestionBank question = new QuestionBank();
        question.setContent(content);
        question.setCorrectOption(correctOption);
        question.setOption2(option2);
        question.setOption3(option3);
        question.setOption4(option4);
        question.setOption1(option1);
        question = questionExpertRepository.save(question);

        quiz.setTotalQuestions(quiz.getTotalQuestions() + 1);

        QuizQuestionId quizQuestionId = new QuizQuestionId(quiz.getId(), question.getId());

        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setId(quizQuestionId);
        quizQuestion.setQuestion(question);
        quizQuestion.setQuiz(quiz);
        quizQuestionRepository.save(quizQuestion);
    }

    public QuestionBank getQuestionBankById(Integer questionId) {
        return questionExpertRepository.getQuestionBankById(questionId);
    }

    public void updateQuestion(QuestionBank questionBank) {
        QuestionBank question = questionExpertRepository.getQuestionBankById(questionBank.getId());
        question.setContent(questionBank.getContent());
        question.setCorrectOption(questionBank.getCorrectOption());
        question.setOption2(questionBank.getOption2());
        question.setOption3(questionBank.getOption3());
        question.setOption4(questionBank.getOption4());
        question.setOption1(questionBank.getOption1());
        questionExpertRepository.save(question);
    }

    public List<QuestionDetailDTO> filterQuestionByQuizId(Integer quizId) {
        return questionExpertRepository.getQuestionByQuizId(quizId);
    }


}
