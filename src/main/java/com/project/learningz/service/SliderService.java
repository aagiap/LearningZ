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
        Pageable pageable = PageRequest.of(page, size);
        return sliderRepository.findAll(pageable);
    }

    public long countSliders() {
        return sliderRepository.count();
    }
}
