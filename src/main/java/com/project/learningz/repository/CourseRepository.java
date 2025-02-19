package com.project.learningz.repository;

import com.project.learningz.dto.CourseDetailsDTO;
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

    @Query("""
    SELECT new com.project.learningz.dto.CourseDetailsDTO(
        c.id,
        c.createdBy.username,
        g.name,
        c.subject,
        c.title,
        c.description,
        c.courseDriveLink,
        COUNT(DISTINCT l.id),
        COUNT(DISTINCT ch.id)
    )
    FROM Course AS c
    LEFT JOIN Chapter ch ON c.id = ch.course.id
    LEFT JOIN Lesson l ON ch.id = l.chapter.id
    LEFT JOIN Grade g ON c.grade.id = g.id
    WHERE c.createdBy.id = ?1
    GROUP BY c.id, c.createdBy.username, g.name,
    c.subject, c.title, c.description, c.courseDriveLink
""")
    List<CourseDetailsDTO> allCoursesByUserID(int userId);


    @Query("""
    SELECT new com.project.learningz.dto.CourseDetailsDTO(
        c.id,
        c.createdBy.username,
        g.name,
        c.subject,
        c.title,
        c.description,
        c.courseDriveLink,
        COUNT(DISTINCT l.id),
        COUNT(DISTINCT ch.id)
    )
    FROM Course AS c
    LEFT JOIN Chapter ch ON c.id = ch.course.id
    LEFT JOIN Lesson l ON ch.id = l.chapter.id
    LEFT JOIN Grade g ON c.grade.id = g.id
    WHERE c.createdBy.id = ?1 AND c.subject like CONCAT('%', ?2, '%') AND c.title like CONCAT('%', ?3, '%')
    GROUP BY c.id, c.createdBy.username, g.name,
    c.subject, c.title, c.description, c.courseDriveLink
""")
    List<CourseDetailsDTO> findCourses(int id, String subject, String keyWord);

    @Query("SELECT c FROM Course c WHERE c.id = ?1")
    Course findByCourseId(int courseId);

    List<Course> findCoursesByGradeId(int gradeId);

}
