package com.project.learningz.repository;

import com.project.learningz.dto.CourseReviewDTO;
import com.project.learningz.entity.UsersCourse;
import com.project.learningz.entity.UsersCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserCourseRepository extends JpaRepository<UsersCourse, UsersCourseId> {
    @Query("""
                SELECT c.id AS courseId, 
                ROUND(AVG(uc.rating), 2) AS averageRating
                FROM Course c
                LEFT JOIN UsersCourse uc ON c.id = uc.course.id
                WHERE uc.rating IS NOT NULL
                GROUP BY c.id
            """)
    List<Object[]> findAverageRatingByCourse();


    @Query("""
            SELECT COUNT(uc.user.id) 
            FROM UsersCourse uc 
            WHERE uc.course.id = :courseId
            """)
    Integer findStudentCountByCourseId(@Param("courseId") Integer courseId);

    @Query("""
                SELECT new com.project.learningz.dto.CourseReviewDTO(
                    uc.user.id, u.username, uc.course.id, uc.rating, uc.comment, uc.date, u.avtUrl)
                FROM UsersCourse uc
                JOIN User u ON uc.user.id = u.id
                WHERE uc.course.id = :courseId
                AND uc.comment IS NOT NULL AND uc.comment <> ''
                AND uc.rating IS NOT NULL
               ORDER BY uc.date DESC
            """)
    List<CourseReviewDTO> findReviewsByCourseId(@Param("courseId") int courseId);


    @Query("""
                SELECT COUNT(uc) > 0 FROM UsersCourse uc 
                JOIN uc.user u
                JOIN uc.course c
                WHERE u.username = :userName AND c.id = :courseId
            """)
    boolean isUserEnrolled(@Param("userName") String userName, @Param("courseId") int courseId);


    @Query("SELECT uc FROM UsersCourse uc WHERE uc.user.id = :userId AND uc.course.id = :courseId")
    UsersCourse findUsersCourseBy(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

    @Query("SELECT new com.project.learningz.dto.CourseReviewDTO(uc.user.id, u.username, uc.course.id, uc.rating, uc.comment, uc.date, u.avtUrl) " +
            "FROM UsersCourse uc JOIN uc.user u " +
            "WHERE uc.user.id = :userId AND uc.course.id = :courseId")
    Optional<CourseReviewDTO> findReviewByUserIdAndCourseId(@Param("userId") Integer userId, @Param("courseId") Integer courseId);


    @Query("SELECT uc FROM UsersCourse uc WHERE uc.user.id = :userId AND uc.course.id = :courseId")
    Optional<UsersCourse> findReviewByUserIdAndCourseIdReturnEntity(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

    @Query("SELECT COUNT (uc) FROM UsersCourse uc WHERE uc.course.id = :courseId AND uc.rating IS NOT NULL")
    Integer countReviewByCourseId(Integer courseId);
}
