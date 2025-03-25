package com.project.learningz.controller;

import com.project.learningz.repository.CommentRepository;
import com.project.learningz.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/post/{postId}")
    public ResponseEntity<String> reportPost(@PathVariable Integer postId) {
        postRepository.findById(postId).ifPresent(post -> {
            post.setReported(true);
            postRepository.save(post);
        });
        return ResponseEntity.ok("Post reported successfully");
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<String> reportComment(@PathVariable Integer commentId) {
        commentRepository.findById(commentId).ifPresent(comment -> {
            comment.setReported(true);
            commentRepository.save(comment);
        });
        return ResponseEntity.ok("Comment reported successfully");
    }
}

