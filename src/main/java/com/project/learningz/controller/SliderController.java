package com.project.learningz.controller;

import com.project.learningz.entity.Slider;
import com.project.learningz.service.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/marketer")
public class SliderController {

    @Autowired
    private SliderService sliderService;

    private static final String REDIRECT_SLIDERS = "redirect:/marketer/slider";
    @GetMapping("/slider")
    public String showSliders(@RequestParam(defaultValue = "0") int page, Model model) {
        int size = 2;  // Số phần tử trên mỗi trang
        model.addAttribute("sliders", sliderService.getSlidersWithPagination(page, size).getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", sliderService.getSlidersWithPagination(page, size).getTotalPages());
        return "/marketer/slider";
    }
    @GetMapping("/dashboard")
    public String showSliders( Model model) {

        return "/marketer/dashboard";
    }
    // Hiển thị form thêm slider

    @GetMapping("/add_slider")
    public String showAddSliderForm(Model model) {
        model.addAttribute("slider", new Slider());
        return "/marketer/add_slider";
    }

    // Xử lý form thêm slider
    @PostMapping("/add_slider")
    public String addSlider(@ModelAttribute Slider slider) {
        slider.setStatus(true); // Mặc định là slider được kích hoạt
        sliderService.addSlider(slider);
        return REDIRECT_SLIDERS;
    }

    // Hiển thị form sửa slider
    @GetMapping("/slider/edit/{id}")
    public String showEditSliderForm(@PathVariable Integer id, Model model) {
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
    public String deleteSlider(@PathVariable Integer id) {
        sliderService.deleteSlider(id);
        return REDIRECT_SLIDERS;
    }

    // Ẩn/Hiện slider
    @GetMapping("/slider/toggleVisibility/{id}")
    public String toggleSliderVisibility(@PathVariable Integer id) {
        sliderService.toggleVisibility(id);
        return REDIRECT_SLIDERS;
    }


}
