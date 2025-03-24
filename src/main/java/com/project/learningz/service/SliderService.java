package com.project.learningz.service;

import com.project.learningz.entity.Slider;
import com.project.learningz.entity.SystemSetting;
import com.project.learningz.repository.QuizRepository;
import com.project.learningz.repository.SliderRepository;
import com.project.learningz.repository.SystemSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SliderService {

    @Autowired
    private SliderRepository sliderRepository;
    @Autowired
    private SystemSettingRepository systemSettingRepository;

    public List<Slider> getAllSliders() {
        return sliderRepository.findAll();
    }

    public void addSlider(Slider slider) {
        sliderRepository.save(slider);
    }

    public Slider getSliderById(Integer id) {
        return sliderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slider not found"));
    }

    public void updateSlider(Slider slider) {
        sliderRepository.save(slider);
    }

    public void deleteSlider(Integer id) {
        sliderRepository.deleteById(id);
    }

    public void toggleVisibility(Integer id) {
        sliderRepository.findById(id)
                .ifPresent(slider -> {
                    slider.setStatus(!slider.getStatus());
                    sliderRepository.save(slider);
                });
    }

    public Page<Slider> getSlidersWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return sliderRepository.findAll(pageable);
    }

    public long countSliders() {
        return sliderRepository.count();
    }

    public Page<Slider> searchSliders(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,Sort.by("createdAt").descending());

        if (keyword == null || keyword.isEmpty()) {
            return sliderRepository.findAll(pageable);
        }

        if (keyword.matches("\\d+")) {
            Integer sliderId = Integer.parseInt(keyword);
            Optional<Slider> slider = sliderRepository.findById(sliderId);
            return slider.map(value -> new PageImpl<>(List.of(value), pageable, 1))
                    .orElseGet(() -> new PageImpl<>(Collections.emptyList(), pageable, 0));
        }

        return sliderRepository.searchByTitleOrDescription(keyword, pageable);
    }
    public Integer getMaxVisibleSlider() {
        SystemSetting systemSetting = systemSettingRepository.findBySettingName("Max ad show");
        return systemSetting.getSettingValue();
    }
    public boolean isSliderLimitExceeded() {
        int maxVisibleSlider = getMaxVisibleSlider();
        long activeSliderCount = sliderRepository.countByStatus(true);
        return activeSliderCount >= maxVisibleSlider;
    }

}
