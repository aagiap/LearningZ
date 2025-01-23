package com.project.learningz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "UsersCourses")
public class UsersCourse {
    @EmbeddedId
    private UsersCourseId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId("courseId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "rating")
    private Integer rating;

    @Lob
    @Column(name = "comment", columnDefinition = "NVARCHAR(MAX)")
    private String comment;

    @ColumnDefault("getdate()")
    @Column(name = "\"date\"")
    private LocalDate date;

}