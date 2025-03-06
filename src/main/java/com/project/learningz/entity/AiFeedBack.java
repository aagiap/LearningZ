package com.project.learningz.entity;


import com.project.learningz.constant.AiReact;
import com.project.learningz.constant.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "AiFeedBacks")
public class AiFeedBack {

    @Id
    @Column(name = "ai_feedback_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ai-answer", nullable = true, length = 255, columnDefinition = "NVARCHAR(255)")
    private String aiAnswer;

    @Column(name = "user_question", nullable = true, length = 255, columnDefinition = "NVARCHAR(255)")
    private String userQuestion;

    @Column(name = "ai_react")
    @Enumerated(EnumType.STRING)
    private AiReact aiReact;
}
