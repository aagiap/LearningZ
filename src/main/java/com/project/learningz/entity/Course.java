package com.project.learningz.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Courses")
public class Course {
    @Id
    @Column(name = "course_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "title", nullable = false, length = 100, columnDefinition = "NVARCHAR(255)")
    private String title;


    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "grade_id", nullable = false)
    private Grade grade;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Size(max = 100)
    @NotNull
    @Column(name = "subject", nullable = false, length = 100, columnDefinition = "NVARCHAR(255)")
    private String subject;

    @Column(name = "course_drive_link", nullable = true, length = 255, columnDefinition = "NVARCHAR(255)")
    private String courseDriveLink;
}