package com.project.learningz.entity;

import com.project.learningz.constant.QuizType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Video> videos = new ArrayList<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PDF> pdfs = new ArrayList<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Quiz> quizzes = new ArrayList<>();

    @Column(name = "lesson_drive_link", nullable = true, length = 255, columnDefinition = "NVARCHAR(255)")
    private String lessonDriveLink;

    @Column(name = "document_folder_link", nullable = true, length = 255, columnDefinition = "NVARCHAR(255)")
    private String documentFolderLink;

    @Column(name = "video_folder_link", nullable = true, length = 255, columnDefinition = "NVARCHAR(255)")
    private String videoFolderLink;

    @Column(name = "quiz_image_link", nullable = true, length = 255, columnDefinition = "NVARCHAR(255)")
    private String quizImageLink;
}