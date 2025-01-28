package com.project.learningz.service;

import com.project.learningz.repository.UserCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UsersCourseService {
    private final UserCourseRepository userCourseRepository;

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
}
