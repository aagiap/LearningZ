package com.project.learningz.repository;

import com.project.learningz.entity.MonthlyStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonthlyStatisticRepository extends JpaRepository<MonthlyStatistic, Integer> {
    Optional<MonthlyStatistic> findByYearAndMonth(int year, int month);


    @Query("SELECT m FROM MonthlyStatistic m WHERE m.year = :year ORDER BY m.month ASC")
    List<MonthlyStatistic> findByYear(@Param("year") int year);
}

