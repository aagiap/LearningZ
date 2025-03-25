package com.project.learningz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class QuizQuestionId implements java.io.Serializable {
    private static final long serialVersionUID = -4245566505664809162L;
    @NotNull
    @Column(name = "quiz_id", nullable = false)
    private Integer quizId;

    @NotNull
    @Column(name = "question_id", nullable = false)
    private Integer questionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        QuizQuestionId entity = (QuizQuestionId) o;
        return Objects.equals(this.questionId, entity.questionId) &&
                Objects.equals(this.quizId, entity.quizId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, quizId);
    }

    public QuizQuestionId(Integer quizId, Integer questionId) {
        this.quizId = quizId;
        this.questionId = questionId;
    }

    public QuizQuestionId() {
    }
}