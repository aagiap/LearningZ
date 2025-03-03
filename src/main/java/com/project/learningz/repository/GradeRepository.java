package com.project.learningz.repository;

import com.project.learningz.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Integer>, JpaSpecificationExecutor<Grade> {
    Grade findByName(String name);

    @Query("SELECT DISTINCT g.name FROM Grade g")
    List<String> findDistinctGradeName();

    @Query("SELECT g FROM Grade g WHERE g.id=?1")
    Grade findById(int gradeId);

    @Query("""
        SELECT DISTINCT g.name
        FROM QuestionBank q
                JOIN QuizQuestion qq ON q.id = qq.question.id
                JOIN Quiz qu ON qq.quiz.id = qu.id
                JOIN qu.lesson l
                JOIN l.chapter ch
                JOIN ch.course co
                JOIN co.subject s
                JOIN co.grade g
    """)
    List<String> getAllGrade();
}

