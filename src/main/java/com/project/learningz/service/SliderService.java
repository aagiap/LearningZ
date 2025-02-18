package com.project.learningz.service;

import com.project.learningz.entity.Slider;
import com.project.learningz.repository.SliderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SliderService {

    @Autowired
    private SliderRepository sliderRepository;

    // Lấy tất cả slider
    public List<Slider> getAllSliders() {
        return sliderRepository.findAll();
    }

    // Thêm slider mới
    public void addSlider(Slider slider) {
        sliderRepository.save(slider);
    }

    // Lấy slider theo ID
    public Slider getSliderById(Integer id) {
        return sliderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slider not found"));
    }

    // Cập nhật slider
    public void updateSlider(Slider slider) {
        sliderRepository.save(slider);
    }

    // Xóa slider
    public void deleteSlider(Integer id) {
        sliderRepository.deleteById(id);
    }

    // Ẩn/Hiện slider
    public void toggleVisibility(Integer id) {
        sliderRepository.findById(id)
                .ifPresent(slider -> {
                    slider.setStatus(!slider.getStatus()); // Đảo trạng thái active
                    sliderRepository.save(slider);
                });
    }

    // Lấy sliders với phân trang
    public Page<Slider> getSlidersWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return sliderRepository.findAll(pageable);
    }
}
