package com.project.learningz.controller;

import com.project.learningz.entity.Comment;
import com.project.learningz.entity.Post;
import com.project.learningz.entity.User;
import com.project.learningz.service.CommentLikeService;
import com.project.learningz.service.CommentService;
import com.project.learningz.service.PostService;
import com.project.learningz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentLikeService commentLikeService;


    @PostMapping("/create")
    public String createComment(@RequestParam Integer postId,
                                @RequestParam String content,
                                Model model) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        commentService.createComment(postId, currentUser.getId(), content);
        Post post = postService.findById(postId);
        List<Comment> comments = commentService.getCommentsByPost(postId);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("isLoggedIn", true);

        return "redirect:/post/" + postId;
    }
    @PostMapping("edit/{id}")
    public String editComment(@PathVariable Integer id,
                              @RequestParam String content,
                              @RequestParam Integer postId) {
        Comment comment = commentService.findById(id);
        if (comment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }

        User currentUser = getCurrentUser();
        if (!comment.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized");
        }
        content = content.replace("\r\n", "\n");
        comment.setContent(content);
        commentService.save(comment);

        return "redirect:/post/" + postId;
    }


    @PostMapping("/delete/{id}")
    public String deleteComment(@PathVariable Integer id) {
        User currentUser = getCurrentUser();
        Comment comment = commentService.findById(id);
        if (comment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }

        if (!comment.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized");
        }

        Integer postId = comment.getPost().getPostId();
        commentService.deleteComment(id);
        return "redirect:/post/" + postId;
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
    @PostMapping("/{commentId}/like")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> toggleLike(@PathVariable Integer commentId) {
        User user = getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(401).body(null);
        }

        Comment comment = commentService.findById(commentId);
        if (comment == null) {
            return ResponseEntity.status(404).body(null);
        }

        boolean isLiked = commentLikeService.toggleLike(comment, user);

        Map<String, Object> response = new HashMap<>();
        response.put("isLiked", isLiked);
        response.put("likeCount", comment.getLikeCount());

        return ResponseEntity.ok(response);
    }
}
