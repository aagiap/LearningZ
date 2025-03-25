package com.project.learningz.service;

import com.project.learningz.entity.*;
import com.project.learningz.repository.QuizRepository;
import com.project.learningz.repository.QuizResultRepository;
import com.project.learningz.repository.SystemSettingRepository;
import com.project.learningz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizResultService {
    @Autowired
    QuizResultRepository quizResultRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuizService quizService;
    @Autowired
    private UserService userService;

    @Autowired
    private SystemSettingRepository systemSettingRepository;

    public void saveResult(Integer userId, Integer quizId, Float score) {
        QuizResult quizResult = quizResultRepository.findQuizResultsByQuizIdAndUserId(quizId, userId);
        if (quizResult == null) {
            quizResult = new QuizResult();
            quizResult.setId(new QuizResultId(quizId, userId));
            quizResult.setQuiz(quizService.getQuizById(quizId));
            quizResult.setUser(userService.getUserById(userId));
            quizResult.setMaxScore(score);
            quizResult.setNumAtempts(1);
            if(score >= getMinScoreToPass()) {
                quizResult.setResultStatus("Pass");
            } else {
                quizResult.setResultStatus("Fail");
            }
            quizResultRepository.save(quizResult);
        }
         else {
            quizResult.setNumAtempts(quizResult.getNumAtempts() + 1);
            quizResultRepository.save(quizResult);
            if (quizResult.getMaxScore() <= score) {
                quizResult.setMaxScore(score);
                if(quizResult.getMaxScore() >= getMinScoreToPass()) {
                    quizResult.setResultStatus("Pass");
                }
                quizResultRepository.save(quizResult);
            }
        }
    }

    public String isPass(Integer userId, Integer quizId) {
        QuizResult quizResult = quizResultRepository.findQuizResultsByQuizIdAndUserId(quizId,userId);
        if (quizResult == null) {
            return "Not done yet";
        } else {
            if (quizResult.getResultStatus().equals("Fail")) {
                return "Not pass";
            }
        }
        return "Pass";
    }

    public Integer getMinScoreToPass() {
        SystemSetting systemSetting = systemSettingRepository.findBySettingName("Min score to pass");
        return systemSetting.getSettingValue();
    }



    public QuizResult findQuizResultsByQuizIdAndUserId(Integer userId, Integer quizId) {
        return quizResultRepository.findQuizResultsByQuizIdAndUserId(quizId, userId);
    }

    public Integer getMaxAttempts() {
        SystemSetting systemSetting = systemSettingRepository.findBySettingName("Max times take a quiz");
        return systemSetting.getSettingValue();
    }

    public boolean isMaxAttempts(Integer userId, Integer quizId) {
        QuizResult quizResult = quizResultRepository.findQuizResultsByQuizIdAndUserId(quizId, userId);
        if (quizResult == null) {
            return false;
        } else {
            return quizResult.getNumAtempts() >= getMaxAttempts();
        }
    }

}
