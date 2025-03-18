package com.project.learningz.repository;

import com.project.learningz.entity.Comment;
import com.project.learningz.entity.CommentLike;
import com.project.learningz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Integer> {
    Optional<CommentLike> findByCommentAndUser(Comment comment, User user);
}
