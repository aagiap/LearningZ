package com.project.learningz.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.io.Serializable;

@Getter
@Setter
@Entity
public class QuestionBank implements Serializable {
    @Id
    @Column(name = "question_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Size(max = 255)
    @Nationalized
    @Column(name = "content", columnDefinition = "NVARCHAR(255)")
    private String content;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "option_1", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String option1;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "option_2", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String option2;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "option_3", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String option3;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "option_4", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String option4;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "correct_option", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String correctOption;

    @Size(max = 255)
    @Nationalized
    @Column(name = "image_quiz_url")
    private String imageQuizUrl;

}