package com.project.learningz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "content", columnDefinition = "NVARCHAR(255)")
    private String content;

    @Column(nullable = false)
    private LocalDateTime commentDate;

    @Column(nullable = false)
    private int likeCount = 0;

    @Transient
    private boolean isLiked;

    @PreUpdate
    public void preUpdate() {
        this.commentDate = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        this.commentDate = LocalDateTime.now();
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void setLiked(boolean liked) {
        this.isLiked = liked;
    }

    private boolean reported = false;
}
