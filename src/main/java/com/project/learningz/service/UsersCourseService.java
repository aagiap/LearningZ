package com.project.learningz.service;

import com.project.learningz.dto.CourseReviewDTO;
import com.project.learningz.entity.User;
import com.project.learningz.entity.UsersCourse;
import com.project.learningz.entity.UsersCourseId;
import com.project.learningz.repository.UserCourseRepository;
import com.project.learningz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UsersCourseService {
    private final UserCourseRepository userCourseRepository;
    private final UserRepository userRepository;

    public Map<Integer, Double> getAverageRatingByCourse() {
        List<Object[]> results = userCourseRepository.findAverageRatingByCourse();
        Map<Integer, Double> averageRatings = new HashMap<>();
        for (Object[] result : results) {
            Integer courseId = (Integer) result[0];
            Double averageRating = (Double) result[1];
            averageRatings.put(courseId, averageRating);
        }
        return averageRatings;
    }

    public int numberOfStudentsInCourse(int courseId) {
        Integer count = userCourseRepository.findStudentCountByCourseId(courseId);
        return count != null ? count : 0;
    }

    //lấy danh sách rating của mỗi course
    public List<CourseReviewDTO> getCourseReviews(int courseId) {
        return userCourseRepository.findReviewsByCourseId(courseId);
    }

    public boolean checkUserEnrolled(String userName, int courseId) {
        return userCourseRepository.isUserEnrolled(userName, courseId);
    }

    public void addOrUpdateReview(String userName, int courseId, int rating, String comment) {

        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new RuntimeException("User not found: " + userName);
        }
        UsersCourseId id = new UsersCourseId();
        id.setCourseId(courseId);
        id.setUserId(user.getId());
        UsersCourse usersCourse = userCourseRepository.findById(id).orElse(new UsersCourse());
        usersCourse.setId(id);
        usersCourse.setRating(rating);
        usersCourse.setComment(comment);
        usersCourse.setDate(LocalDate.now());
        userCourseRepository.save(usersCourse);
    }
}
