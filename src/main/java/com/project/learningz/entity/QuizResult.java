package com.project.learningz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class QuizResult {

    @EmbeddedId
    private QuizResultId id;

    @MapsId("quizId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "max_score",nullable = false)
    private Float maxScore;

    @Column(name = "num_atempts",nullable = false)
    private Integer numAtempts;

    @Column(name = "result_status",nullable = false)
    private String resultStatus;
}
