package com.project.learningz.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Chapters")
public class Chapter {
    @Id
    @Column(name = "chapter_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Size(max = 255)
    @Column(name = "chapter_title", columnDefinition = "NVARCHAR(255)")
    private String chapterTitle;

    @NotNull
    @Column(name = "description", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String description;

    @Column(name = "chapter_order")
    private Integer chapterOrder;

}