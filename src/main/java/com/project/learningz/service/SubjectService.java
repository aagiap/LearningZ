package com.project.learningz.service;

import com.project.learningz.entity.Subject;
import com.project.learningz.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public List<String> getAllSubjectInQuestions() {
        return subjectRepository.getAllSubjects();
    }
}
