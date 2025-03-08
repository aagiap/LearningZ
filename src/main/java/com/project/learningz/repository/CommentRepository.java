package com.project.learningz.repository;

import com.project.learningz.entity.Comment;
import com.project.learningz.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPostAndParentIsNull(Post post);
}
