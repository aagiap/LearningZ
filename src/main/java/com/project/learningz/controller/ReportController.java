package com.project.learningz.controller;

import com.project.learningz.entity.User;
import com.project.learningz.repository.CommentRepository;
import com.project.learningz.repository.PostRepository;
import com.project.learningz.repository.UserRepository;
import com.project.learningz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    @Autowired
    private UserService userService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<String> reportPost(@PathVariable Integer postId) {
        User user = getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(401).body("You need to log in to report this post.");
        }
        postRepository.findById(postId).ifPresent(post -> {
            post.setReported(true);
            postRepository.save(post);
        });
        return ResponseEntity.ok("Post reported successfully");
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<String> reportComment(@PathVariable Integer commentId) {
        User user = getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(401).body("You need to log in to report this comment.");
        }
        commentRepository.findById(commentId).ifPresent(comment -> {
            comment.setReported(true);
            commentRepository.save(comment);
        });
        return ResponseEntity.ok("Comment reported successfully");
    }
    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof OAuth2User oAuth2User) {
            String email = oAuth2User.getAttribute("email");
            return userService.findByEmail(email);
        } else if (principal instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            return userService.findByUsername(username);
        }
        return null;
    }

}

