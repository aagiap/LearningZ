package com.project.learningz.repository;

import com.project.learningz.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult,Integer> {
    QuizResult findQuizResultsByQuizIdAndUserId(Integer quizId,Integer userId);
    List<QuizResult> findQuizResultsByUserId(Integer userId);
}
