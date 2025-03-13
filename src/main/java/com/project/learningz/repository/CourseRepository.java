package com.project.learningz.repository;

import com.project.learningz.constant.CourseStatus;
import com.project.learningz.dto.CourseDetailsDTO;
import com.project.learningz.dto.CourseLearnedStatsDTO;
import com.project.learningz.dto.CourseStatsDTO;
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

    @Query("""
    SELECT COUNT(c.id)
    FROM Course AS c
    WHERE c.createdBy.id = ?1
    GROUP BY c.createdBy.id  \s
""")
    int getTotalCoursesByUserId(int userId);

    @Query("""
    SELECT 
        COUNT(v.id)
    FROM Course c
    LEFT JOIN Chapter ch ON c.id = ch.course.id
    LEFT JOIN Lesson l ON ch.id = l.chapter.id
    LEFT JOIN Video v ON l.id = v.lesson.id
    WHERE c.createdBy.id = ?1
""")
    int getTotalVideosByUserId(int userId);

    @Query("""
    SELECT COUNT(p.id)
    FROM Course c
    LEFT JOIN Chapter ch ON c.id = ch.course.id
    LEFT JOIN Lesson l ON ch.id = l.chapter.id
    LEFT JOIN PDF p ON l.id = p.lesson.id
    WHERE c.createdBy.id = ?1
""")
    int getTotalDocsByUserId(int userId);

    @Query("""
    SELECT COUNT(uc.user.id)
    FROM UsersCourse uc
    LEFT JOIN Course c ON uc.course.id = c.id
    WHERE c.createdBy.id = ?1
""")
    int getTotalStudentsByUserId(int userId);

    @Query("""
    SELECT COUNT(c.id)
    FROM Course AS c
    WHERE c.createdBy.id = ?1 AND c.courseStatus = ?2
""")
    int getTotalCourseWithStatusByUserId(int userId, CourseStatus status);

    @Query("""
    SELECT c
    FROM Course AS c
    WHERE c.createdBy.id = ?1
    ORDER BY c.id DESC
    LIMIT 3
""")
    List<Course> getTop3CoursesListByUserId(int userId);

    @Query("""
    SELECT new com.project.learningz.dto.CourseStatsDTO(
    c.title,
    COALESCE(ROUND(AVG(CAST(uc.rating AS float )),2),0)
    )
    FROM Course AS c
    LEFT JOIN UsersCourse uc ON c.id = uc.course.id
    WHERE c.createdBy.id = ?1
    GROUP BY c.title
    ORDER BY COALESCE(ROUND(AVG(CAST(uc.rating AS float )),2),0) DESC
""")
    List<CourseStatsDTO> getCourseAndScoreByUserId(int userId);

    @Query("""
    SELECT new com.project.learningz.dto.CourseLearnedStatsDTO(
        c.title,
        COUNT(uc.user.id)
    )
    FROM Course AS c
    LEFT JOIN UsersCourse uc ON c.id = uc.course.id
    WHERE c.createdBy.id = ?1
    GROUP BY c.title
    ORDER BY COUNT(uc.user.id) DESC
""")
    List<CourseLearnedStatsDTO> getCourseLearnedStatsByUserId(int userId);

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
    WHERE c.createdBy.id = ?1 AND c.subject.id = ?2 AND c.grade.id = ?3 AND c.title like CONCAT('%', ?4, '%')
    GROUP BY c.id, c.createdBy.username, g.name, c.subject.name, 
    c.courseStatus, c.title, c.description, c.courseDriveLink
""")
    List<CourseDetailsDTO> findCourses(int userId, int subjectId, int gradeId, String courseSearchKey);

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
    WHERE c.createdBy.id = ?1 AND c.grade.id = ?2 AND c.title like CONCAT('%', ?3, '%')
    GROUP BY c.id, c.createdBy.username, g.name, c.subject.name, 
    c.courseStatus, c.title, c.description, c.courseDriveLink
""")
    List<CourseDetailsDTO> findCoursesWithGrade(int id, int gradeId, String keyWord);
}
