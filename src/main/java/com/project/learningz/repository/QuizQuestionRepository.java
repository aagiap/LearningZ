package com.project.learningz.repository;

import com.project.learningz.entity.QuestionBank;
import com.project.learningz.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Integer> {


    @Query("SELECT q.question FROM QuizQuestion q WHERE q.quiz.id = :quizId")
    List<QuestionBank> findQuestionBankByQuizId(Integer quizId);
}
