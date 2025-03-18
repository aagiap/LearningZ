package com.project.learningz.controller;

import com.project.learningz.entity.Comment;
import com.project.learningz.entity.Post;
import com.project.learningz.entity.Slider;
import com.project.learningz.entity.User;
import com.project.learningz.repository.CommentRepository;
import com.project.learningz.repository.PostRepository;
import com.project.learningz.repository.SliderRepository;
import com.project.learningz.repository.UserRepository;
import com.project.learningz.service.CommentService;
import com.project.learningz.service.GoogleDriveService;
import com.project.learningz.service.PostService;
import com.project.learningz.service.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Controller
@RequestMapping("/marketer")
public class SliderController {

    @Autowired
    private SliderService sliderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoogleDriveService googleDriveService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    private static final String REDIRECT_SLIDERS = "redirect:/marketer/slider";

    private void getAuthenticatedUserInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String avatarUrl = "/static/image/AvartaDefault.jpg";

        if (authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
            String email = oAuth2User.getAttribute("email");
            User user = userRepository.findByEmail(email);
            if (user != null) {
                username = user.getUsername();
                avatarUrl = user.getAvtUrl() != null ? user.getAvtUrl() : avatarUrl;
            }
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                user = userRepository.findByEmail(username);
            }
            if (user != null) {
                avatarUrl = user.getAvtUrl() != null ? user.getAvtUrl() : avatarUrl;
            }
        }
        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
    }

    @GetMapping("/slider")
    public String showSliders(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(required = false, defaultValue = "") String keyword,
                              Model model) {
        int size = 2;
        Page<Slider> sliderPage;

        if (keyword.isEmpty()) {
            sliderPage = sliderService.getSlidersWithPagination(page, size);
        } else {
            sliderPage = sliderService.searchSliders(keyword, page, size);
        }

        getAuthenticatedUserInfo(model);
        model.addAttribute("sliders", sliderPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", sliderPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("hasResults", !sliderPage.isEmpty());

        return "marketer/slider";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        getAuthenticatedUserInfo(model);
        return "/marketer/dashboard";
    }

    // Hiển thị form thêm slider
    @GetMapping("/add_slider")
    public String showAddSliderForm(Model model) {
        getAuthenticatedUserInfo(model);
        model.addAttribute("slider", new Slider());
        return "/marketer/add_slider";
    }

    @PostMapping("/add_slider")
    public String addSlider(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("img") MultipartFile imgFile,
            @RequestParam("status") boolean status,
            @RequestParam(value = "backlink", required = false) String backlink,
            Model model) {
        try {
            String imageUrl = googleDriveService.uploadBannerFile(imgFile);

            Slider slider = new Slider();
            slider.setTitle(title);
            slider.setDescription(description);
            slider.setImageUrl(imageUrl);
            slider.setStatus(status);
            slider.setBacklink(backlink);

            sliderService.addSlider(slider);
            return REDIRECT_SLIDERS;
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi tải ảnh hoặc lưu slider!");
            return "marketer/add_slider";
        }
    }


    // Hiển thị form sửa slider
    @GetMapping("/slider/edit/{id}")
    public String showEditSliderForm(@PathVariable Integer id,
                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                     Model model) {
        getAuthenticatedUserInfo(model);
        model.addAttribute("slider", sliderService.getSliderById(id));
        model.addAttribute("page", page);
        return "/marketer/edit_slider";
    }


    @PostMapping("/slider/edit")
    public String editSlider(@RequestParam("sliderId") Integer sliderId,
                             @RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                             @RequestParam(value = "status", defaultValue = "false") boolean status,
                             @RequestParam(value = "backlink", required = false) String backlink,
                             @RequestParam(value = "page", defaultValue = "1") int page) {
        try {
            Slider existingSlider = sliderService.getSliderById(sliderId);
            String oldImageUrl = existingSlider.getImageUrl();

            existingSlider.setTitle(title);
            existingSlider.setDescription(description);
            existingSlider.setStatus(status);
            existingSlider.setBacklink(backlink);

            if (imageFile != null && !imageFile.isEmpty()) {
                String newImageUrl = googleDriveService.uploadBannerFile(imageFile);
                existingSlider.setImageUrl(newImageUrl);

                if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                    String oldFileId = googleDriveService.getGoogleDriveFileId(oldImageUrl);
                    if (oldFileId != null) {
                        googleDriveService.deleteFile(oldFileId);
                    }
                }
            }

            sliderService.updateSlider(existingSlider);
            return "redirect:/marketer/slider?page=" + page;

        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return "redirect:/slider/edit/" + sliderId + "?page=" + page;
        }
    }



    // Xóa slider
    @GetMapping("/slider/delete/{id}")
    public String deleteSlider(@PathVariable Integer id, @RequestParam(defaultValue = "0") int page) {
        sliderService.deleteSlider(id);
        int totalSliders = (int) sliderService.countSliders();
        int size = 2;
        int totalPages = (int) Math.ceil((double) totalSliders / size);
        if (page >= totalPages && page > 0) {
            page--;
        }
        return "redirect:/marketer/slider?page=" + page;
    }

    // Ẩn/Hiện slider
    @GetMapping("/slider/toggleVisibility/{id}")
    public String toggleSliderVisibility(@PathVariable Integer id, @RequestParam(defaultValue = "0") int page) {
        sliderService.toggleVisibility(id);
        return "redirect:/marketer/slider?page=" + page;
    }
    @GetMapping("/report/post")
    public String showReportedPosts(@RequestParam(defaultValue = "0") int page, Model model) {
        int size = 2;
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> reportedPosts = postRepository.findByReportedTrue(pageable);

        getAuthenticatedUserInfo(model);
        model.addAttribute("reportedPosts", reportedPosts.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", reportedPosts.getTotalPages());

        return "marketer/manage_post";
    }


    @GetMapping("/approve/post/{postId}")
    public String approvePost(@PathVariable Integer postId, @RequestParam(defaultValue = "0") int page) {
        postRepository.findById(postId).ifPresent(post -> {
            post.setReported(false);
            postRepository.save(post);
        });

        int totalReportedPosts = (int) postRepository.countByReportedTrue();
        int size = 2;
        int totalPages = (int) Math.ceil((double) totalReportedPosts / size);

        if (page >= totalPages && page > 0) {
            page--;
        }

        return "redirect:/marketer/report/post?page=" + page;
    }


    @GetMapping("/delete/post/{postId}")
    public String deletePost(@PathVariable int postId, @RequestParam(defaultValue = "0") int page) {
        postService.deletePost(postId);

        int totalPosts = (int) postService.countReportedPosts();
        int size = 2;
        int totalPages = (int) Math.ceil((double) totalPosts / size);

        if (page >= totalPages && page > 0) {
            page--;
        }
        return "redirect:/marketer/report/post?page=" + page;
    }
    @GetMapping("/report/comment")
    public String showReportedComments(@RequestParam(defaultValue = "0") int page, Model model) {
        int size = 2;
        Pageable pageable = PageRequest.of(page, size, Sort.by("commentDate").descending());
        Page<Comment> reportedComments = commentRepository.findByReportedTrue(pageable);

        getAuthenticatedUserInfo(model);
        model.addAttribute("reportedComments", reportedComments.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", reportedComments.getTotalPages());

        return "marketer/manage_comment";
    }

    @GetMapping("/approve/comment/{commentId}")
    public String approveComment(@PathVariable Integer commentId, @RequestParam(defaultValue = "0") int page) {
        commentRepository.findById(commentId).ifPresent(comment -> {
            comment.setReported(false);
            commentRepository.save(comment);
        });

        int totalReportedComments = (int) commentRepository.countByReportedTrue();
        int size = 2;
        int totalPages = (int) Math.ceil((double) totalReportedComments / size);

        if (page >= totalPages && page > 0) {
            page--;
        }

        return "redirect:/marketer/report/comment?page=" + page;
    }

    @GetMapping("/delete/comment/{commentId}")
    public String deleteComment(@PathVariable int commentId, @RequestParam(defaultValue = "0") int page) {
        commentService.deleteComment(commentId);

        int totalComments = (int) commentService.countReportedComments();
        int size = 2;
        int totalPages = (int) Math.ceil((double) totalComments / size);

        if (page >= totalPages && page > 0) {
            page--;
        }

        return "redirect:/marketer/report/comment?page=" + page;
    }


}
