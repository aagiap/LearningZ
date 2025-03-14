package com.project.learningz.service;

import com.project.learningz.dto.QuestionDetailDTO;
import com.project.learningz.repository.QuestionExpertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionExpertService {

    @Autowired
    QuestionExpertRepository questionExpertRepository;

    public Page<QuestionDetailDTO> getAllQuestion(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return questionExpertRepository.getAllQuestions(pageable);
    }

    public Page<QuestionDetailDTO> getAllQuestionByKeyword(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return questionExpertRepository.searchAllQuestionsByKeyword(keyword, pageable);
    }

    public Page<QuestionDetailDTO> filterQuestion(String grade, String course, String subject, String chapter, String lesson, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return questionExpertRepository.filterQuestion(grade, course, subject, chapter, lesson, pageable);
    }

    public List<QuestionDetailDTO> filterQuestionByQuizId(Integer quizId) {
        return questionExpertRepository.getQuestionByQuizId(quizId);
    }


}
