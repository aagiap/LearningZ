package com.project.learningz.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
public class QuestionBank {
    @Id
    @Column(name = "question_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "quiz_id", nullable = false)
    private Integer quizId;

    @Size(max = 255)
    @Nationalized
    @Column(name = "content")
    private String content;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "option_1", nullable = false)
    private String option1;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "option_2", nullable = false)
    private String option2;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "option_3", nullable = false)
    private String option3;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "option_4", nullable = false)
    private String option4;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "correct_option", nullable = false)
    private String correctOption;

}