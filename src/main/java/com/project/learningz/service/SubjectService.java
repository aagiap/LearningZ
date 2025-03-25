package com.project.learningz.service;

import com.project.learningz.entity.Subject;
import com.project.learningz.repository.SubjectRepository;
import jakarta.transaction.Transactional;
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

    public Subject getSubjectById(int id) {
        return subjectRepository.getSubjectById(id);
    }

    public List<Subject> getSubjectsByKey(String key) {
        return subjectRepository.getSubjectByKey(key);
    }

    @Transactional
    public void updateSubject(int subjectId, String subjectName, String description) {
        Subject subject = subjectRepository.getSubjectById(subjectId);
        subject.setName(subjectName);
        subject.setDescription(description);
        subjectRepository.save(subject);
    }

    @Transactional
    public void createSubject(String subjectName, String description) {
        Subject subject = new Subject();
        subject.setName(subjectName);
        subject.setDescription(description);
        subjectRepository.save(subject);
    }

    public boolean checkSubjectNameExistsWhenAdd(String subjectName) {
        List<Subject> subjectList = subjectRepository.findAll();
        for(Subject subject : subjectList) {
            if(subject.getName().equalsIgnoreCase(subjectName.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSubjectNameExistsWhenEdit(String subjectName, int subjectId) {
        List<Subject> subjectList = subjectRepository.findAll();
        for(Subject subject : subjectList) {
            if(subject.getName().equalsIgnoreCase(subjectName.trim()) && !getSubjectById(subjectId).getName().equalsIgnoreCase(subjectName.trim())) {
                return true;
            }
        }
        return false;
    }

    public List<String> getAllSubjectInQuestions() {
        return subjectRepository.getAllSubjects();
    }
}
