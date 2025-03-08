package com.project.learningz.repository;

import com.project.learningz.entity.Slider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SliderRepository extends JpaRepository<Slider, Integer> {
    Page<Slider> findAll(Pageable pageable);
    long count();
    @Query("SELECT s FROM Slider s WHERE " +
            "LOWER(s.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Slider> searchByTitleOrDescription(@Param("keyword") String keyword, Pageable pageable);

}


