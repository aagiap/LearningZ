package com.project.learningz.repository;

import com.project.learningz.dto.CourseReviewDTO;
import com.project.learningz.entity.UsersCourse;
import com.project.learningz.entity.UsersCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCourseRepository extends JpaRepository<UsersCourse, UsersCourseId> {
    @Query("""
                    SELECT c.id AS courseId, AVG(COALESCE(uc.rating, 0)) AS averageRating
                    FROM Course c
                    LEFT JOIN UsersCourse uc ON c.id = uc.course.id
                    GROUP BY c.id
            """)
        //COALESCE Đảm bảo rằng nếu không có rating, giá trị mặc định là 0.
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

}
