package com.project.learningz.repository;

import com.project.learningz.dto.ChapterDetailDTO;
import com.project.learningz.dto.LessonDetailDTO;
import com.project.learningz.entity.Chapter;
import com.project.learningz.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByChapterId(Integer chapterId);

    Lesson findLessonById(Integer id);

    @Query("SELECT l FROM Lesson l WHERE l.chapter.course.id = :courseId")
    List<Lesson> findByCourseId(Integer courseId);

    @Query("""
        SELECT new com.project.learningz.dto.LessonDetailDTO(
            l.id,
            l.chapter.id,
            l.title,
            l.quizType,
            l.description,
            l.lessonDriveLink,
            l.documentFolderLink,
            l.videoFolderLink,
            l.quizImageLink,
            COUNT(v),
            COUNT(p)
        )
        FROM Lesson l 
        LEFT JOIN Video v ON l.id = v.lesson.id
        LEFT JOIN PDF p ON l.id = p.lesson.id
        WHERE l.chapter.id = ?1
        GROUP BY l.id,l.chapter.id,l.title,l.quizType,l.description,
            l.lessonDriveLink,l.documentFolderLink,l.videoFolderLink,l.quizImageLink
        ORDER BY l.id
""")
    List<LessonDetailDTO> allLessonsByChapterId(Integer chapterId);

    @Query("""
        SELECT new com.project.learningz.dto.LessonDetailDTO(
            l.id,
            l.chapter.id,
            l.title,
            l.quizType,
            l.description,
            l.lessonDriveLink,
            l.documentFolderLink,
            l.videoFolderLink,
            l.quizImageLink,
            COUNT(v),
            COUNT(p)
        )
        FROM Lesson l 
        LEFT JOIN Video v ON l.id = v.lesson.id
        LEFT JOIN PDF p ON l.id = p.lesson.id
        WHERE l.chapter.id = ?1 AND l.title LIKE CONCAT('%', ?2, '%')
        GROUP BY l.id,l.chapter.id,l.title,l.quizType,l.description,
            l.lessonDriveLink,l.documentFolderLink,l.videoFolderLink,l.quizImageLink
        ORDER BY l.id
""")
    List<LessonDetailDTO> findLessons(Integer chapterId, String keyword);

    @Query("""
        SELECT DISTINCT l.title
        FROM QuestionBank q
                JOIN QuizQuestion qq ON q.id = qq.question.id
                JOIN Quiz qu ON qq.quiz.id = qu.id
                JOIN qu.lesson l
                JOIN l.chapter ch
                JOIN ch.course co
                JOIN co.subject s
                JOIN co.grade g
    """)
    List<String> getAllLesson();

    @Query("SELECT COUNT(l) from Lesson l join l.chapter c join c.course co where co.title = :courseTitle")
    Integer countNumberOfLesson(@Param("courseTitle") String courseTitle);
}
