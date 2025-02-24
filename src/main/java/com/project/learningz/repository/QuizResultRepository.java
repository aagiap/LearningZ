package com.project.learningz.repository;

import com.project.learningz.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult,Integer> {
    QuizResult findQuizResultsByQuizIdAndUserId(Integer quizId,Integer userId);
    List<QuizResult> findQuizResultsByUserId(Integer userId);

    @Query("SELECT qr FROM QuizResult qr JOIN qr.quiz q JOIN q.lesson l JOIN l.chapter c JOIN c.course cc WHERE qr.user.id = :userId AND cc.id = :courseId")
    List<QuizResult> getQuizResultInCourse(@Param("userId") Integer userId,@Param("courseId") Integer courseId);



}
