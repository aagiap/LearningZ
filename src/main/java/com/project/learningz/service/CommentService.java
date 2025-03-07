package com.project.learningz.service;

import com.project.learningz.entity.Comment;
import com.project.learningz.entity.Post;
import com.project.learningz.entity.User;
import com.project.learningz.repository.CommentRepository;
import com.project.learningz.service.UserService;
import com.project.learningz.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    public Comment createComment(Integer postId, Integer userId, String content) {
        User user = userService.findById(userId);
        Post post = postService.findById(postId);

        if (user != null && post != null) {

            Comment comment = new Comment();
            comment.setUser(user);
            comment.setPost(post);
            comment.setContent(content);
            return commentRepository.save(comment);
        }

        return null;
    }

    public void deleteCommentsByPost(Integer postId) {
        commentRepository.deleteByPost_PostId(postId);
    }

    public List<Comment> getCommentsByPost(Integer postId) {
        return commentRepository.findByPost_PostId(postId);
    }
}
