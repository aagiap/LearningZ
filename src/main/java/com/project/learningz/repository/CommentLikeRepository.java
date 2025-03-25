package com.project.learningz.repository;

import com.project.learningz.entity.Comment;
import com.project.learningz.entity.CommentLike;
import com.project.learningz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Integer> {
    Optional<CommentLike> findByCommentAndUser(Comment comment, User user);
    void deleteByCommentId(Integer id);
    @Modifying
    @Transactional
    @Query("DELETE FROM CommentLike cl WHERE cl.comment.id IN :commentIds")
    void deleteByCommentIds(@Param("commentIds") List<Integer> commentIds);
}
