package com.project.learningz.controller;

import com.project.learningz.entity.Comment;
import com.project.learningz.entity.Post;
import com.project.learningz.entity.User;
import com.project.learningz.service.CommentService;
import com.project.learningz.service.PostService;
import com.project.learningz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, CommentService commentService, UserService userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping()
    public String showPosts(@RequestParam(defaultValue = "0") int page, Model model) {
        int size = 2;
        Page<Post> postPage = postService.getAllPostsPaginated(page, size);

        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());

        addUserInfoToModel(model);
        return "/post/post";
    }

    @GetMapping("/create")
    public String showCreatePostPage(Model model) {
        addUserInfoToModel(model);
        return "post/create_post";
    }

    @GetMapping("/{postId}")
    public String getPostDetails(@PathVariable Integer postId, Model model) {
        Post post = postService.findById(postId);
        List<Comment> comments = commentService.getCommentsByPost(postId);

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);

        addUserInfoToModel(model);

        return "post/single_post";
    }

    private void addUserInfoToModel(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        String avatarUrl = "/static/image/AvartaDefault.jpg"; // Ảnh mặc định
        Integer userId = null;

        if (principal instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) principal;
            String email = oAuth2User.getAttribute("email");
            User user = userService.findByEmail(email);
            if (user != null) {
                avatarUrl = user.getAvtUrl() != null ? user.getAvtUrl() : avatarUrl;
                username = user.getUsername();
                userId = user.getId();
            }
        } else if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
            User user = userService.findByUsername(username);
            if (user == null) {
                user = userService.findByEmail(username);
            }
            if (user != null) {
                avatarUrl = user.getAvtUrl() != null ? user.getAvtUrl() : avatarUrl;
                userId = user.getId();
            }
        }

        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("userId", userId);
        model.addAttribute("isLoggedIn", username != null);
    }
}
