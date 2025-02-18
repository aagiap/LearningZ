package com.project.learningz.repository;

import com.project.learningz.entity.Slider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SliderRepository extends JpaRepository<Slider, Integer> {
    Page<Slider> findAll(Pageable pageable);
}


