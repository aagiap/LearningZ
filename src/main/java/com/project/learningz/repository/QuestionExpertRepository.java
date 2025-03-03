package com.project.learningz.repository;

import com.project.learningz.dto.QuestionDetailDTO;
import com.project.learningz.entity.QuestionBank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionExpertRepository extends JpaRepository<QuestionBank, Integer> {

    @Query("""
                SELECT new com.project.learningz.dto.QuestionDetailDTO(
                    q.id, q.content, q.correctOption, 
                    co.title, s.name, ch.chapterTitle, l.title, g.name
                )
                FROM QuestionBank q
                JOIN QuizQuestion qq ON q.id = qq.question.id
                JOIN Quiz qu ON qq.quiz.id = qu.id
                JOIN qu.lesson l
                JOIN l.chapter ch
                JOIN ch.course co
                JOIN co.subject s
                JOIN co.grade g          
            """)
    Page<QuestionDetailDTO> getAllQuestions(Pageable pageable);

    @Query("""
                SELECT new com.project.learningz.dto.QuestionDetailDTO(
                    q.id, q.content, q.correctOption, 
                    co.title, s.name, ch.chapterTitle, l.title, g.name
                )
                FROM QuestionBank q
                JOIN QuizQuestion qq ON q.id = qq.question.id
                JOIN Quiz qu ON qq.quiz.id = qu.id
                JOIN qu.lesson l
                JOIN l.chapter ch
                JOIN ch.course co
                JOIN co.subject s
                JOIN co.grade g
                WHERE LOWER(q.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(g.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(co.title) LIKE LOWER(CONCAT('%', :keyword, '%'))            
                OR LOWER(ch.chapterTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) 
                OR LOWER(l.title) LIKE LOWER(CONCAT('%', :keyword, '%'))  
            """)
    Page<QuestionDetailDTO> searchAllQuestionsByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("""
                SELECT new com.project.learningz.dto.QuestionDetailDTO(
                    q.id, q.content, q.correctOption, 
                    co.title, s.name, ch.chapterTitle, l.title, g.name
                )
                FROM QuestionBank q
                JOIN QuizQuestion qq ON q.id = qq.question.id
                JOIN Quiz qu ON qq.quiz.id = qu.id
                JOIN qu.lesson l
                JOIN l.chapter ch
                JOIN ch.course co
                JOIN co.subject s
                JOIN co.grade g
                WHERE (:grade = '' OR g.name = :grade)
                AND (:subject = '' OR s.name = :subject)
                AND (:course = '' OR co.title = :course)
                AND (:chapter = '' OR ch.chapterTitle = :chapter)
                AND (:lesson = '' OR l.title = :lesson) 
            """)
    Page<QuestionDetailDTO> filterQuestion(@Param("grade") String grade,
                                           @Param("course") String course,
                                           @Param("subject") String subject,
                                           @Param("chapter") String chapter,
                                           @Param("lesson") String lesson,
                                           Pageable pageable);

}
