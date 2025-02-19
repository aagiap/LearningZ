package com.project.learningz.repository;

import com.project.learningz.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByChapterId(Integer chapterId);

    Lesson findLessonById(Integer id);

    @Query("SELECT l FROM Lesson l WHERE l.chapter.course.id = :courseId")
    List<Lesson> findByCourseId(Integer courseId);
}
