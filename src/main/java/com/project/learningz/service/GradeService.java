package com.project.learningz.service;

import com.project.learningz.entity.Grade;
import com.project.learningz.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepository;

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public List<String> findDistinctGradeName() {
        return gradeRepository.findDistinctGradeName();
    }

    public Grade findById(int gradeId){
        return gradeRepository.findById(gradeId);
    }
}