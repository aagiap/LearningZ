package com.project.learningz.service;


import com.project.learningz.entity.AiFeedBack;
import com.project.learningz.repository.AiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    @Autowired
    private AiRepository aiRepository;

    public void save(AiFeedBack aiFeedBack) {
        aiRepository.save(aiFeedBack);
    }
}
