package com.project.learningz.service;

import com.project.learningz.entity.Quiz;
import com.project.learningz.entity.QuizResult;
import com.project.learningz.entity.QuizResultId;
import com.project.learningz.entity.User;
import com.project.learningz.repository.QuizRepository;
import com.project.learningz.repository.QuizResultRepository;
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

    public void saveResult(Integer userId, Integer quizId, Float score) {
        QuizResult quizResult = quizResultRepository.findQuizResultsByQuizIdAndUserId(quizId, userId);
        if (quizResult == null) {
            quizResult = new QuizResult();
            quizResult.setId(new QuizResultId(quizId, userId));
            quizResult.setQuiz(quizService.getQuizById(quizId));
            quizResult.setUser(userService.getUserById(userId));
            quizResult.setMaxScore(score);
            quizResultRepository.save(quizResult);
        }
         else {
            if (quizResult.getMaxScore() < score) {
                quizResult.setMaxScore(score);
                quizResultRepository.save(quizResult);
            }
        }
    }

    public String isPass(Integer userId, Integer quizId) {
        QuizResult quizResult = quizResultRepository.findQuizResultsByQuizIdAndUserId(quizId,userId);
        if (quizResult == null) {
            return "Not done yet";
        } else {
            if (quizResult.getMaxScore() < 8) {
                return "Not pass";
            }
        }
        return "Pass";
    }

    public QuizResult findQuizResultsByQuizIdAndUserId(Integer userId, Integer quizId) {
        return quizResultRepository.findQuizResultsByQuizIdAndUserId(quizId, userId);
    }

}
