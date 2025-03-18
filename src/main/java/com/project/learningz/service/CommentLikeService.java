package com.project.learningz.service;

import com.project.learningz.entity.Comment;
import com.project.learningz.entity.CommentLike;
import com.project.learningz.entity.User;
import com.project.learningz.repository.CommentLikeRepository;
import com.project.learningz.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public boolean toggleLike(Comment comment, User user) {
        Optional<CommentLike> existingLike = commentLikeRepository.findByCommentAndUser(comment, user);

        if (existingLike.isPresent()) {
            commentLikeRepository.delete(existingLike.get());
            comment.decreaseLikeCount();
            commentRepository.save(comment);
            return false;
        } else {
            CommentLike newLike = new CommentLike();
            newLike.setComment(comment);
            newLike.setUser(user);
            commentLikeRepository.save(newLike);

            comment.increaseLikeCount();
            commentRepository.save(comment);
            return true;
        }
    }
    public boolean isLikedByUser(Comment comment, User user) {
        if (user == null) return false;
        return commentLikeRepository.findByCommentAndUser(comment, user).isPresent();
    }
}
