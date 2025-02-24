package com.project.learningz.service;

import com.project.learningz.dto.CourseReviewDTO;
import com.project.learningz.entity.*;
import com.project.learningz.repository.*;
import com.project.learningz.entity.*;
import com.project.learningz.repository.QuizRepository;
import com.project.learningz.repository.QuizResultRepository;
import com.project.learningz.repository.UserCourseRepository;
import com.project.learningz.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersCourseService {
    private final UserCourseRepository userCourseRepository;
    private final UserRepository userRepository;

    @Autowired
    private QuizResultRepository quizResultRepository;

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private CourseRepository courseRepository;

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


    public boolean checkConditionFeedback(Integer userId, Integer courseId) {
        Integer numberOfQuiz = quizRepository.countNumberOfQuizInCourse(courseId);
        int count = 0;
        List<QuizResult> quizResults = quizResults(userId);
        for (QuizResult quizResult : quizResults) {
            if (quizResult.getMaxScore() >= 8) {
                count++;
            }
        }
        if (count == numberOfQuiz) {
            return true;
        }
        return false;
    }

    public String progressStatus(Integer userId, Integer courseId) {
        Integer numberOfQuiz = quizRepository.countNumberOfQuizInCourse(courseId);
        int count = 0;
        //List<QuizResult> quizResults = quizResults(userId);
        List<QuizResult> quizResults = quizResultRepository.getQuizResultInCourse(userId, courseId);
        for (QuizResult quizResult : quizResults) {
            if (quizResult.getMaxScore() >= 8) {
                count++;
            }
        }
        return count + "/" + numberOfQuiz;
    }

    public boolean checkIsFeeback(Integer userId, Integer courseId) {
        UsersCourse usersCourse = userCourseRepository.findUsersCourseBy(userId, courseId);
        if (usersCourse == null) {
            return false;
        }
        if (usersCourse.getRating() != null) {
            return true;
        }
        return false;
    }

    public List<QuizResult> quizResults(Integer userId) {
        return quizResultRepository.findQuizResultsByUserId(userId);
    }


    public CourseReviewDTO getUserFeedback(Integer userId, Integer courseId) {
        return userCourseRepository.findReviewByUserIdAndCourseId(userId, courseId).orElse(null);
    }

    @Transactional
    public boolean clearFeedback(Integer userId, Integer courseId) {
        Optional<UsersCourse> usersCourseOpt = userCourseRepository.findReviewByUserIdAndCourseIdReturnEntity(userId, courseId);

        if (usersCourseOpt.isPresent()) {
            UsersCourse usersCourse = usersCourseOpt.get();
            usersCourse.setRating(null);
            usersCourse.setComment(null);
            userCourseRepository.save(usersCourse);
            return true;
        }
        return false;
    }


    public void updateFeedback(Integer userId, Integer courseId, Integer rating, String comment) {
        UsersCourse usersCourse = userCourseRepository.findReviewByUserIdAndCourseIdReturnEntity(userId, courseId)
                .orElseThrow(() -> new RuntimeException("Cannot find User feedback!"));

        usersCourse.setRating(rating);
        usersCourse.setComment(comment);
        usersCourse.setDate(LocalDate.now());

        userCourseRepository.save(usersCourse);
    }
    public int countReviewByCourseId(int courseId){
        return userCourseRepository.countReviewByCourseId(courseId);
    }

    public List<UsersCourse> getUserCourseByUserId(int userId){
        return userCourseRepository.getCourseByUserId(userId);
    }

    @Transactional
    public void enrollCourse(Integer userId, Integer courseId) {
        User user = userRepository.findUserById(userId);
        Course course = courseRepository.findByCourseId(courseId);
        UsersCourseId usersCourseId = new UsersCourseId();
        usersCourseId.setUserId(userId);
        usersCourseId.setCourseId(courseId);
        UsersCourse usersCourse = new UsersCourse();
        usersCourse.setId(usersCourseId);
        usersCourse.setUser(user);
        usersCourse.setCourse(course);
        userCourseRepository.save(usersCourse);
    }

}
