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
public class QuizResultId implements java.io.Serializable{
    private static final long serialVersionUID = -5686710465739381531L;
    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @NotNull
    @Column(name = "quiz_id", nullable = false)
    private Integer quizId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        QuizResultId entity = (QuizResultId) o;
        return Objects.equals(this.userId, entity.userId) &&
                Objects.equals(this.quizId, entity.quizId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, quizId);
    }

    public QuizResultId() {
    }

    public QuizResultId(Integer userId, Integer quizId) {
        this.userId = userId;
        this.quizId = quizId;
    }
}
