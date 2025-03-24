package com.project.learningz.controller;

import com.project.learningz.entity.*;
import com.project.learningz.repository.CommentRepository;
import com.project.learningz.repository.PostRepository;
import com.project.learningz.repository.UserRepository;
import com.project.learningz.service.*;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private MonthlyStatisticService monthlyStatisticService;

    @Autowired
    private VipPackageService vipPackageService;

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
            RedirectAttributes redirectAttributes,
            Model model) {
        try {
            if (status && sliderService.isSliderLimitExceeded()) {
                redirectAttributes.addFlashAttribute("errorMessage1", "Cannot display slider, maximum limit reached!");
                return "redirect:/marketer/add_slider";
            }
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
            model.addAttribute("error", "Error when uploading image or edit slider");
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
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             Model model) {
        try {
            Slider existingSlider = sliderService.getSliderById(sliderId);
            boolean isCurrentlyHidden = existingSlider.getStatus() == null || !existingSlider.getStatus();
            if (isCurrentlyHidden && status && sliderService.isSliderLimitExceeded()) {
                model.addAttribute("slider", existingSlider);
                model.addAttribute("errorMessage1", "Cannot display slider, maximum limit reached!");
                return "marketer/edit_slider";
            }

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
            model.addAttribute("error", "An error occurred while editing the slider.");
            return "marketer/edit_slider";
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
    public String toggleSliderVisibility(@PathVariable Integer id,
                                         @RequestParam(defaultValue = "0") int page,
                                         RedirectAttributes redirectAttributes) {
        Slider slider = sliderService.getSliderById(id);
        if (slider == null) {
            redirectAttributes.addFlashAttribute("errorMessage1", "Slider does not exist!");
            return "redirect:/marketer/slider?page=" + page;
        }
        boolean isCurrentlyHidden = slider.getStatus() == null || !slider.getStatus();
        if (isCurrentlyHidden && sliderService.isSliderLimitExceeded()) {
            redirectAttributes.addFlashAttribute("errorMessage1", "Cannot display slider, maximum limit reached!");
            return "redirect:/marketer/slider?page=" + page;
        }
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
        model.addAttribute("hasResults", !reportedPosts.isEmpty());

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
        model.addAttribute("hasResults", !reportedComments.isEmpty());


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

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        getAuthenticatedUserInfo(model);

        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        int previousYear = (currentMonth == 1) ? currentYear - 1 : currentYear;
        int previousMonth = (currentMonth == 1) ? 12 : currentMonth - 1;

        MonthlyStatistic currentStat = monthlyStatisticService.getMonthlyStatistic(currentYear, currentMonth);
        MonthlyStatistic previousStat = monthlyStatisticService.getMonthlyStatistic(previousYear, previousMonth);

        model.addAttribute("usersChange", calculateChange(currentStat.getTotalUsersRegistered(), previousStat.getTotalUsersRegistered()));
        model.addAttribute("coursesChange", calculateChange(currentStat.getTotalCoursesRegistered(), previousStat.getTotalCoursesRegistered()));
        model.addAttribute("visitsChange", calculateChange(currentStat.getTotalVisits(), previousStat.getTotalVisits()));
        model.addAttribute("revenueChange", calculateChange(currentStat.getTotalRevenue().intValue(), previousStat.getTotalRevenue().intValue()));

        model.addAttribute("usersChangeIcon", getChangeIcon(currentStat.getTotalUsersRegistered(), previousStat.getTotalUsersRegistered()));
        model.addAttribute("coursesChangeIcon", getChangeIcon(currentStat.getTotalCoursesRegistered(), previousStat.getTotalCoursesRegistered()));
        model.addAttribute("visitsChangeIcon", getChangeIcon(currentStat.getTotalVisits(), previousStat.getTotalVisits()));
        model.addAttribute("revenueChangeIcon", getChangeIcon(currentStat.getTotalRevenue().intValue(), previousStat.getTotalRevenue().intValue()));

        model.addAttribute("currentStat", currentStat);
        model.addAttribute("previousStat", previousStat);
        System.out.println("Current Users: " + currentStat.getTotalUsersRegistered());
        System.out.println("Previous Users: " + previousStat.getTotalUsersRegistered());

        System.out.println("Current Courses: " + currentStat.getTotalCoursesRegistered());
        System.out.println("Previous Courses: " + previousStat.getTotalCoursesRegistered());

        System.out.println("Current Visits: " + currentStat.getTotalVisits());
        System.out.println("Previous Visits: " + previousStat.getTotalVisits());

        System.out.println("Current Revenue: " + currentStat.getTotalRevenue());
        System.out.println("Previous Revenue: " + previousStat.getTotalRevenue());
        List<MonthlyStatistic> statistics = monthlyStatisticService.getStatisticsForYear(currentYear);

        Map<Integer, BigDecimal> revenueMap = new HashMap<>();
        for (MonthlyStatistic stat : statistics) {
            revenueMap.put(stat.getMonth(), stat.getTotalRevenue());
        }

        List<BigDecimal> revenueData = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            revenueData.add(revenueMap.getOrDefault(i, BigDecimal.ZERO));
        }

        model.addAttribute("revenueData", revenueData);
        return "marketer/dashboard";
    }

    private String calculateChange(int current, int previous) {
        if (previous == 0) return "+100%";
        double change = ((double) (current - previous) / previous) * 100;
        return String.format("%.1f%%", change);
    }

    private String getChangeIcon(int current, int previous) {
        return (current >= previous) ? "bx bxs-up-arrow text-success" : "bx bxs-down-arrow text-danger";
    }

    @GetMapping("/vip_package")
    public String listVipPackages(Model model) {
        getAuthenticatedUserInfo(model);
        model.addAttribute("vipPackages", vipPackageService.getAllVipPackages());
        return "marketer/vip_package";
    }

    @GetMapping("/vip_package/edit/{id}")
    public String editVipPackage(@PathVariable Integer id, Model model) {
        getAuthenticatedUserInfo(model);
        VipPackage vipPackage = vipPackageService.getVipPackageById(id);
        if (vipPackage == null) {
            return "redirect:/marketer/vip_package";
        }
        model.addAttribute("vipPackage", vipPackage);
        return "marketer/edit_package";
    }

    @PostMapping("/vip_package/update/{id}")
    public String updateVipPackage(@PathVariable Integer id,
                                   @RequestParam String packageName,
                                   @RequestParam int duration,
                                   @RequestParam long price,
                                   @RequestParam(required = false) Long discountedPrice,
                                   @RequestParam boolean status,
                                   RedirectAttributes redirectAttributes) {
        try {
            VipPackage vipPackage = vipPackageService.findById(id);
            if (vipPackage == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "VIP package not found");
                return "redirect:/marketer/vip_package/edit/" + id;
            }

            if (discountedPrice != null && (discountedPrice < 0 || discountedPrice > price)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Discounted price invalid");
                return "redirect:/marketer/vip_package/edit/" + id;
            }

            vipPackage.setPackageName(packageName);
            vipPackage.setDuration(duration);
            vipPackage.setPrice(price);
            vipPackage.setDiscountedPrice(discountedPrice);
            vipPackage.setStatus(status);

            vipPackageService.save(vipPackage);
            redirectAttributes.addFlashAttribute("successMessage", "Completed update VIP package");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating VIP package");
        }
        return "redirect:/marketer/vip_package";
    }

    @GetMapping("/vip_package/toggleVisibility/{id}")
    public String toggleVipPackageVisibility(@PathVariable Integer id) {
        vipPackageService.toggleVisibility(id);
        return "redirect:/marketer/vip_package";
    }
    @GetMapping("/vip_package/add_package")
    public String showAddForm(Model model) {
        getAuthenticatedUserInfo(model);
        model.addAttribute("vipPackage", new VipPackage());
        return "marketer/add_package";
    }
    @PostMapping("/vip_package/add_package")
    public String addVipPackage(@ModelAttribute VipPackage vipPackage, RedirectAttributes redirectAttributes) {

        if (vipPackage.getDiscountedPrice() != null && vipPackage.getDiscountedPrice() > vipPackage.getPrice()) {
            redirectAttributes.addFlashAttribute("error", "Discounted price invalid");
            return "redirect:/marketer/add_package";
        }

        vipPackageService.save(vipPackage);
        redirectAttributes.addFlashAttribute("success", "Added vip package successfully");
        return "redirect:/marketer/vip_package";
    }
}


