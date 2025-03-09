package com.project.learningz.repository;

import com.project.learningz.constant.CourseStatus;
import com.project.learningz.dto.CourseDetailsDTO;
import com.project.learningz.entity.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        c.subject.name,
        c.title,
        c.courseStatus,
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
    GROUP BY c.id, c.createdBy.username, g.name, c.subject.name, 
    c.courseStatus, c.title, c.description, c.courseDriveLink
""")
    List<CourseDetailsDTO> allCoursesByUserID(int userId);

    @Query("""
    SELECT new com.project.learningz.dto.CourseDetailsDTO(
        c.id,
        c.createdBy.username,
        g.name,
        c.subject.name,
        c.title,
        c.courseStatus,
        c.description,
        c.courseDriveLink,
        COUNT(DISTINCT l.id),
        COUNT(DISTINCT ch.id)
    )
    FROM Course AS c
    LEFT JOIN Chapter ch ON c.id = ch.course.id
    LEFT JOIN Lesson l ON ch.id = l.chapter.id
    LEFT JOIN Grade g ON c.grade.id = g.id
    WHERE c.createdBy.id = ?1 AND c.title like CONCAT('%', ?2, '%')
    GROUP BY c.id, c.createdBy.username, g.name, c.subject.name, 
    c.courseStatus, c.title, c.description, c.courseDriveLink
""")
    List<CourseDetailsDTO> findCourses(int id, String keyWord);

    @Query("""
    SELECT new com.project.learningz.dto.CourseDetailsDTO(
        c.id,
        c.createdBy.username,
        g.name,
        c.subject.name,
        c.title,
        c.courseStatus,
        c.description,
        c.courseDriveLink,
        COUNT(DISTINCT l.id),
        COUNT(DISTINCT ch.id)
    )
    FROM Course AS c
    LEFT JOIN Chapter ch ON c.id = ch.course.id
    LEFT JOIN Lesson l ON ch.id = l.chapter.id
    LEFT JOIN Grade g ON c.grade.id = g.id
    WHERE c.createdBy.id = ?1 AND c.subject.id = ?2 AND c.title like CONCAT('%', ?3, '%')
    GROUP BY c.id, c.createdBy.username, g.name, c.subject.name, 
    c.courseStatus, c.title, c.description, c.courseDriveLink
""")
    List<CourseDetailsDTO> findCourses(int id, int subjectId, String keyWord);

    @Query("SELECT c FROM Course c WHERE c.id = ?1")
    Course findByCourseId(int courseId);

    List<Course> findCoursesByGradeId(int gradeId);

    @Query("""
                SELECT DISTINCT co.title
                FROM QuestionBank q
                        JOIN QuizQuestion qq ON q.id = qq.question.id
                        JOIN Quiz qu ON qq.quiz.id = qu.id
                        JOIN qu.lesson l
                        JOIN l.chapter ch
                        JOIN ch.course co
                        JOIN co.subject s
                        JOIN co.grade g
            """)
    List<String> getAllCourse();

    @Query("SELECT c FROM Course c WHERE c.courseStatus = :status " +
            "AND (:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.grade.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.createdBy.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.subject.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Course> searchCourseByStatusAndKeyword(@Param("status") CourseStatus status,
                                                @Param("keyword") String keyword,
                                                Sort sort);

    @Query("SELECT c FROM Course c WHERE (:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.grade.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.createdBy.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.subject.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Course> getAllCoursesByKeyword(@Param("keyword") String keyword,
                                        Sort sort);

    @Query("SELECT c FROM Course c WHERE c.courseStatus = :status")
    List<Course> getAllCoursesByStatus(CourseStatus status);


    @Query("SELECT uc.course, COUNT(uc.user) FROM UsersCourse uc " +
            "WHERE uc.user IS NOT NULL " +  // Loại bỏ dòng có user NULL
            "GROUP BY uc.course " +
            "HAVING COUNT(uc.user) > 0 " +  // Đảm bảo chỉ tính khóa học có ít nhất 1 người đăng ký
            "ORDER BY COUNT(uc.user) DESC")
    List<Object[]> getTop5PopularCoursesWithEnrollments(Pageable pageable);


}
