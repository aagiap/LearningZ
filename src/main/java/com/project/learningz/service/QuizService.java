package com.project.learningz.service;


import com.project.learningz.dto.QuizDetailDTO;
import com.project.learningz.dto.QuizJoinToGradeDTO;
import com.project.learningz.entity.QuestionBank;
import com.project.learningz.entity.Quiz;
import com.project.learningz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public Quiz getQuizById(Integer quizId) {
        return quizRepository.findById(quizId).orElse(null);
    }

    public QuizJoinToGradeDTO getQuizJoinToGradeDTOById(Integer quizId) {
        return quizRepository.findByQuizId(quizId);
    }


    public List<QuizJoinToGradeDTO> getQuizzesWithFilter(String subject, String grade) {
        if (subject != null && grade != null) {
            return quizRepository.findByCourseSubjectAndGradeDescription(subject, grade);
        } else if (subject != null) {
            return quizRepository.findByCourseSubject(subject);
        } else if (grade != null) {
            return quizRepository.findByGrade(grade);
        } else {
            return null;
        }
    }


    public List<Quiz> getQuizzesByCourseId(int courseId) {
        return quizRepository.findByCourseId(courseId);
    }

    public Page<QuizDetailDTO> getQuizzesByLessonIdAndKey(Integer lessonId, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (lessonId != null && keyword != null) {
            return quizRepository.findQuizzesByLessonIdAndKey(lessonId, keyword, pageable);
        }
        return quizRepository.getAllQuizzesDTO(lessonId, pageable);
    }

    public QuizDetailDTO getQuizDetailById(Integer quizId) {
        return quizRepository.findQuizzesByQuizId(quizId);
    }


}
