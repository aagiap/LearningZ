package com.project.learningz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseReviewDTO {
    private int userId;
    private String username;
    private int courseId;
    private Integer rating;
    private String comment;
    private LocalDate date;
    private String avatar;
}
