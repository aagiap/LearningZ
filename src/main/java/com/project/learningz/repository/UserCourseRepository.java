package com.project.learningz.repository;

import com.project.learningz.entity.UsersCourse;
import com.project.learningz.entity.UsersCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}
