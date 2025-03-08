package com.project.learningz.controller;

import com.project.learningz.entity.Comment;
import com.project.learningz.entity.Post;
import com.project.learningz.entity.User;
import com.project.learningz.service.CommentService;
import com.project.learningz.service.PostService;
import com.project.learningz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public String createComment(@RequestParam Integer postId,
                                @RequestParam String content,
                                @RequestParam(required = false) Integer parentId,
                                Model model) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        commentService.createComment(postId, currentUser.getId(), content, parentId);

        // Cập nhật lại danh sách bình luận
        Post post = postService.findById(postId);
        List<Comment> comments = commentService.getCommentsByPost(postId);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("isLoggedIn", true);

        return "post/single_post"; // Trả về trang hiển thị bài viết + bình luận
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof OAuth2User) {
            String email = ((OAuth2User) principal).getAttribute("email");
            return userService.findByEmail(email);
        } else if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userService.findByUsername(username);
        }
        return null;
    }
}
