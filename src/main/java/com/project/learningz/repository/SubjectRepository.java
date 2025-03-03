package com.project.learningz.repository;

import com.project.learningz.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer>, JpaSpecificationExecutor<Subject> {
    @Query("""
        SELECT DISTINCT s.name
        FROM QuestionBank q
                JOIN QuizQuestion qq ON q.id = qq.question.id
                JOIN Quiz qu ON qq.quiz.id = qu.id
                JOIN qu.lesson l
                JOIN l.chapter ch
                JOIN ch.course co
                JOIN co.subject s
    """)
    List<String> getAllSubjects();

    @Query("""
    SELECT s FROM Subject s WHERE s.id = ?1
    """)
    Subject getSubjectById(int id);

    @Query("""
    SELECT s FROM Subject s WHERE s.name LIKE CONCAT('%',?1,'%') 
    """)
    List<Subject> getSubjectByKey(String key);
}
