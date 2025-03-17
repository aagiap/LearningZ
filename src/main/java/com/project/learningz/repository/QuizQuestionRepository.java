package com.project.learningz.repository;

import com.project.learningz.entity.QuestionBank;
import com.project.learningz.entity.QuizQuestion;
import com.project.learningz.entity.QuizQuestionId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, QuizQuestionId> {


    @Query("SELECT q.question FROM QuizQuestion q WHERE q.quiz.id = :quizId")
    List<QuestionBank> findQuestionBankByQuizId(Integer quizId);

    @Modifying
    @Transactional
    @Query("DELETE FROM QuizQuestion qq WHERE qq.quiz.id = :quizId and qq.question.id = :questionId")
    void deleteByQuizIdAndQuestionId(@Param("quizId") Integer quizId,
                                     @Param("questionId") Integer questionId);

    boolean existsById(QuizQuestionId id);
}
