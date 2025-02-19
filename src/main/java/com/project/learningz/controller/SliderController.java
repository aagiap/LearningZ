package com.project.learningz.controller;

import com.project.learningz.entity.Slider;
import com.project.learningz.entity.User;
import com.project.learningz.repository.UserRepository;
import com.project.learningz.service.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/marketer")
public class SliderController {

    @Autowired
    private SliderService sliderService;

    @Autowired
    private UserRepository userRepository;

    private static final String REDIRECT_SLIDERS = "redirect:/marketer/slider";

    // Phương thức dùng chung để lấy thông tin user
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
    public String showSliders(@RequestParam(defaultValue = "0") int page, Model model) {
        int size = 2; // Số phần tử trên mỗi trang
        Page<Slider> sliderPage = sliderService.getSlidersWithPagination(page, size);

        getAuthenticatedUserInfo(model);
        model.addAttribute("sliders", sliderPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", sliderPage.getTotalPages());

        return "/marketer/slider";
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

    // Xử lý form thêm slider
    @PostMapping("/add_slider")
    public String addSlider(@ModelAttribute Slider slider) {
        slider.setStatus(true); // Mặc định slider được kích hoạt
        sliderService.addSlider(slider);
        return REDIRECT_SLIDERS;
    }

    // Hiển thị form sửa slider
    @GetMapping("/slider/edit/{id}")
    public String showEditSliderForm(@PathVariable Integer id, Model model) {
        getAuthenticatedUserInfo(model);
        model.addAttribute("slider", sliderService.getSliderById(id));
        return "/marketer/edit_slider";
    }

    // Xử lý form sửa slider
    @PostMapping("/slider/edit")
    public String editSlider(@ModelAttribute Slider slider) {
        sliderService.updateSlider(slider);
        return REDIRECT_SLIDERS;
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
}
