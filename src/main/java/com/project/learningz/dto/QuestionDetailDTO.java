package com.project.learningz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDetailDTO {
    private Integer questionId;
    private String content;
    private String correctOption;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String courseTitle;
    private String subject;
    private String chapterTitle;
    private String lessonTitle;
    private String grade;
}
