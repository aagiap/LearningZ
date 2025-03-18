package com.project.learningz.repository;

import com.project.learningz.entity.Comment;
import com.project.learningz.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPost(Post post, Sort sort);
    @Transactional
    void deleteByPost_PostId(Integer postId);
}
