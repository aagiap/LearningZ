package com.project.learningz.entity;

import com.project.learningz.constant.QuizType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "Lessons")
public class Lesson {
    @Id
    @Column(name = "lession_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "title", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "quiz_type", nullable = false, columnDefinition = "varchar(255) default 'PRACTICE'")
    private QuizType quizType;


    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;


}