package com.project.learningz.repository;

import com.project.learningz.dto.QuizDetailDTO;
import com.project.learningz.dto.QuizJoinToGradeDTO;
import com.project.learningz.entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    @Query("SELECT new com.project.learningz.dto.QuizJoinToGradeDTO( q.id,q.totalQuestions,q.timeLimit,  l.title, g.description, co.subject.name) " +
            "FROM Quiz q " +
            "JOIN q.lesson l " +
            "JOIN l.chapter c " +
            "JOIN c.course co " +
            "JOIN co.grade g " +
            "WHERE co.subject = :subject AND g.name = :description AND l.quizType = 'EXAM'")
    List<QuizJoinToGradeDTO> findByCourseSubjectAndGradeDescription(
            @Param("subject") String subject,
            @Param("description") String description);

    @Query("SELECT new com.project.learningz.dto.QuizJoinToGradeDTO(q.id, q.totalQuestions,q.timeLimit, l.title, g.description, co.subject.name) " +
            "FROM Quiz q " +
            "JOIN q.lesson l " +
            "JOIN l.chapter c " +
            "JOIN c.course co " +
            "JOIN co.grade g " +
            "WHERE co.subject = :subject AND l.quizType = 'EXAM'")
    List<QuizJoinToGradeDTO> findByCourseSubject(@Param("subject") String subject);

    @Query("SELECT new com.project.learningz.dto.QuizJoinToGradeDTO(q.id, q.totalQuestions,q.timeLimit, l.title, g.description, co.subject.name) " +
            "FROM Quiz q " +
            "JOIN q.lesson l " +
            "JOIN l.chapter c " +
            "JOIN c.course co " +
            "JOIN co.grade g " +
            "WHERE g.name = :grade AND l.quizType = 'EXAM'")
    List<QuizJoinToGradeDTO> findByGrade(@Param("grade") String grade);

    @Query("SELECT new com.project.learningz.dto.QuizJoinToGradeDTO(q.id, q.totalQuestions,q.timeLimit, l.title, g.description, co.subject.name) " +
            "FROM Quiz q " +
            "JOIN q.lesson l " +
            "JOIN l.chapter c " +
            "JOIN c.course co " +
            "JOIN co.grade g " +
            "WHERE q.id = :quizId")
    QuizJoinToGradeDTO findByQuizId(@Param("quizId") Integer quizId);

    @Query("SELECT COUNT(*) " +
            "FROM Quiz q " +
            "JOIN q.lesson l " +
            "JOIN l.chapter c " +
            "JOIN c.course co " +
            "JOIN co.grade g " +
            "WHERE co.id = :courseId")
    Integer countNumberOfQuizInCourse(@Param("courseId") Integer courseId);

    @Query("SELECT q " +
            "FROM Quiz q " +
            "JOIN q.lesson l " +
            "JOIN l.chapter c " +
            "JOIN c.course co " +
            "WHERE co.id = :courseId")
    List<Quiz> findByCourseId(@Param("courseId") int courseId);

    @Query("SELECT new com.project.learningz.dto.QuizDetailDTO(q.id, q.totalQuestions, q.timeLimit, q.title, " +
            "co.title, s.name, c.chapterTitle, l.title, g.name) " +
            "FROM Quiz q " +
            "JOIN q.lesson l " +
            "JOIN l.chapter c " +
            "JOIN c.course co " +
            "JOIN co.grade g " +
            "JOIN co.subject s " +
            "WHERE l.id = :lessonId " +
            "AND LOWER(q.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<QuizDetailDTO> findQuizzesByLessonIdAndKey(@Param("lessonId") Integer lessonId,
                                                    @Param("keyword") String keyword,
                                                    Pageable pageable);


    @Query("SELECT new com.project.learningz.dto.QuizDetailDTO(q.id, q.totalQuestions,q.timeLimit, q.title, co.title, s.name, c.chapterTitle, l.title, g.name) " +
            "FROM Quiz q " +
            "JOIN q.lesson l " +
            "JOIN l.chapter c " +
            "JOIN c.course co " +
            "JOIN co.grade g " +
            "JOIN co.subject s " +
            "WHERE q.id = :quizId")
    QuizDetailDTO findQuizzesByQuizId(@Param("quizId") Integer quizId);

    @Query("SELECT new com.project.learningz.dto.QuizDetailDTO(q.id, q.totalQuestions,q.timeLimit, q.title, co.title, s.name, c.chapterTitle, l.title, g.name) " +
            "FROM Quiz q " +
            "JOIN q.lesson l " +
            "JOIN l.chapter c " +
            "JOIN c.course co " +
            "JOIN co.grade g " +
            "JOIN co.subject s " +
            "WHERE l.id = :lessonId")
    Page<QuizDetailDTO> getAllQuizzesDTO(@Param("lessonId") Integer lessonId,
                                         Pageable pageable);

    Quiz getQuizById(Integer quizId);

@Query("SELECT q, r " +
        "FROM Quiz q " +
        "LEFT JOIN q.lesson l " +
        "LEFT JOIN l.chapter c " +
        "LEFT JOIN c.course co " +
        "LEFT JOIN q.quizResults r ON r.user.id = :userId " +
        "WHERE co.id = :courseId")
List<Object[]> getQuizHistory(@Param("courseId") int courseId, @Param("userId") int userId);

    @Query("SELECT COUNT(q) from Quiz  q join  q.lesson l join l.chapter c join c.course co where co.title = :courseTitle")
    Integer countNumberOfQuizzes(@Param("courseTitle") String courseTitle);

}
