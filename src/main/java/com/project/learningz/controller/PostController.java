package com.project.learningz.controller;

import com.project.learningz.constant.Role;
import com.project.learningz.entity.*;
import com.project.learningz.service.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Controller
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {

    private PostService postService;
    private CommentService commentService;
    private UserService userService;
    private GradeService gradeService;
    private SubjectService subjectService;
    private GoogleDriveService googleDriveService;
    private CommentLikeService commentLikeService;

    @GetMapping
    public String showPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) Integer gradeId,
            @RequestParam(required = false) Integer subjectId,
            Model model) {

        int size = 2;
        Page<Post> postPage = postService.getFilteredPosts(page, size, gradeId, subjectId);

        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());


        model.addAttribute("grades", gradeService.getAllGrades());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("selectedGrade", gradeId);
        model.addAttribute("selectedSubject", subjectId);

        addUserInfoToModel(model);
        return "/post/post";
    }
    @GetMapping("/create")
    public String showCreatePostPage(Model model) {
        addUserInfoToModel(model);
        model.addAttribute("grades", gradeService.getAllGrades());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("post", new Post());
        return "post/create_post";
    }
    @PostMapping("/create")
    public String createPost(
            @RequestParam(value = "content", required = false) String content,
            @RequestParam("gradeId") int gradeId,
            @RequestParam("subjectId") int subjectId,
            @RequestParam(value = "postImg", required = false) MultipartFile postImg,
            Model model) {
        try {
            addUserInfoToModel(model);
            boolean hasContent = content != null && !content.trim().isEmpty();
            boolean hasImage = postImg != null && !postImg.isEmpty();

            if (!hasContent && !hasImage) {
                model.addAttribute("error", "Post have to be filled by image or content");
                model.addAttribute("grades", gradeService.getAllGrades());
                model.addAttribute("subjects", subjectService.getAllSubjects());
                return "post/create_post";
            }

            Grade grade = gradeService.findById(gradeId);
            Subject subject = subjectService.getSubjectById(subjectId);
            User user = getCurrentUser();

            if (grade == null || subject == null || user == null) {
                model.addAttribute("error", "Log in to create post");
                model.addAttribute("grades", gradeService.getAllGrades());
                model.addAttribute("subjects", subjectService.getAllSubjects());
                return "post/create_post";
            }

            String imageUrl = null;
            if (hasImage) {
                imageUrl = googleDriveService.uploadPostImage(postImg);
            }

            Post post = new Post();
            post.setPostContent(hasContent ? content.trim() : null);
            post.setPostImgUrl(imageUrl);
            post.setGrade(grade);
            post.setSubject(subject);
            post.setUser(user);

            postService.savePost(post);
            return "redirect:/post";
        } catch (Exception e) {
            addUserInfoToModel(model);
            model.addAttribute("grades", gradeService.getAllGrades());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            return "post/create_post";
        }
    }

    @GetMapping("/edit/{postId}")
    public String showEditPostPage(@PathVariable Integer postId, Model model) {
        Post post = postService.findById(postId);
        User user = getCurrentUser();

        if (post == null || user == null || !post.getUser().getId().equals(user.getId())) {
            return "redirect:/post?error=Bạn không có quyền chỉnh sửa bài viết này!";
        }

        model.addAttribute("post", post);
        model.addAttribute("grades", gradeService.getAllGrades());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        addUserInfoToModel(model);
        return "post/edit_post";
    }

    @PostMapping("/edit")
    public String editPost(
            @RequestParam("postId") Integer postId,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam("gradeId") int gradeId,
            @RequestParam("subjectId") int subjectId,
            @RequestParam(value = "postImg", required = false) MultipartFile postImg,
            @RequestParam(value = "deleteImage", required = false) String deleteImage) {
        try {
            Post post = postService.findById(postId);
            User user = getCurrentUser();

            if (post == null || user == null || !post.getUser().getId().equals(user.getId())) {
                return "redirect:/post?error=You not have permission to edit this post";
            }

            if (content == null || content.trim().isEmpty()) {
                post.setPostContent(null);
            } else {
                post.setPostContent(content.trim());
            }

            post.setGrade(gradeService.findById(gradeId));
            post.setSubject(subjectService.getSubjectById(subjectId));

            if ("true".equals(deleteImage) && post.getPostImgUrl() != null) {
                String fileId = googleDriveService.getGoogleDriveFileId(post.getPostImgUrl());
                if (fileId != null) {
                    try {
                        googleDriveService.deleteFile(fileId);
                    } catch (IOException | GeneralSecurityException e) {
                        e.printStackTrace();
                    }
                }
                post.setPostImgUrl(null);
            }

            if (postImg != null && !postImg.isEmpty()) {
                if (post.getPostImgUrl() != null) {
                    String fileId = googleDriveService.getGoogleDriveFileId(post.getPostImgUrl());
                    if (fileId != null) {
                        try {
                            googleDriveService.deleteFile(fileId);
                        } catch (IOException | GeneralSecurityException e) {
                            e.printStackTrace();
                        }
                    }
                }
                String imageUrl = googleDriveService.uploadPostImage(postImg);
                post.setPostImgUrl(imageUrl);
            }

            postService.savePost(post);
            return "redirect:/post";
        } catch (Exception e) {
            return "redirect:/post?error=Đã xảy ra lỗi khi chỉnh sửa bài viết!";
        }
    }

    @GetMapping("/{postId}")
    public String getPostDetails(@PathVariable Integer postId, Model model) {
        Post post = postService.findById(postId);
        if (post == null) {
            return "redirect:/post";
        }

        List<Comment> comments = commentService.getCommentsByPost(postId);

        User user = getCurrentUser();

        if (user != null) {
            for (Comment comment : comments) {
                boolean isLiked = commentLikeService.isLikedByUser(comment, user);
                comment.setLiked(isLiked);
            }
        }

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("grades", gradeService.getAllGrades());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("grade", post.getGrade());
        model.addAttribute("subject", post.getSubject());

        addUserInfoToModel(model);
        return "post/single_post";
    }

    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Integer id, Model model) {
        User user = getCurrentUser();
        Post post = postService.findById(id);

        if (post != null && user != null) {
            boolean isAuthor = post.getUser().getId().equals(user.getId());
            boolean isAdminOrMarketing = user.getRole() == Role.ADMIN || user.getRole() == Role.MARKETING_TEAM;

            if (isAuthor || isAdminOrMarketing) {
                postService.deletePost(id);
            } else {
                model.addAttribute("error", "You don't have permission to delete this post");
            }
        }
        return "redirect:/post";
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

    private void addUserInfoToModel(Model model) {
        User user = getCurrentUser();
        if (user != null) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("userRole", user.getRole().name());
            model.addAttribute("avatarUrl", user.getAvtUrl() != null ? user.getAvtUrl() : "/static/image/AvartaDefault.jpg");
            model.addAttribute("userId", user.getId());
            model.addAttribute("isLoggedIn", true);
        } else {
            model.addAttribute("isLoggedIn", false);
        }
    }
}
