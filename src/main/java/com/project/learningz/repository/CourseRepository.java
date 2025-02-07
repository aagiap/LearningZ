package com.project.learningz.repository;

import com.project.learningz.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course> {
    Course findByGradeId(Integer gradeId);

    @Query("SELECT DISTINCT c.subject FROM Course c")
    List<String> findDistinctSubject();

    @Query(value = """
            select count(l.lession_id)
            from lessons l join chapters c on l.chapter_id = c.chapter_id
            where c.course_id = :courseId
            """, nativeQuery = true)
    public int countLessonByCourseId(@Param("courseId") int courseId);







}
