package com.project.learningz.controller;

import com.project.learningz.entity.User;
import com.project.learningz.service.CommentService;
import com.project.learningz.entity.Comment;
import com.project.learningz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @GetMapping("/create/{postId}")
    public String showCommentForm(@PathVariable Integer postId, Model model) {
        User currentUser = getCurrentUser();

        if (currentUser == null) {
            return "redirect:/login";
        }

        Integer userId = currentUser.getId();

        model.addAttribute("postId", postId);
        model.addAttribute("userId", userId);
        return "/post/single_post";
    }

    @PostMapping("/create")
    public String createComment(@RequestParam Integer postId, @RequestParam Integer userId, @RequestParam String content) {
        User user = userService.findById(userId);
        if (user == null) {
            return "error";
        }

        Comment comment = commentService.createComment(postId, userId, content);
        if (comment != null) {
            return "redirect:/post/" + postId;
        }
        return "error";
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) principal;
            String email = oAuth2User.getAttribute("email");
            return userService.findByEmail(email);
        } else if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userService.findByUsername(username);
        }
        return null;
    }
}
