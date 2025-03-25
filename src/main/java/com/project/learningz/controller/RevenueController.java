package com.project.learningz.controller;

import com.project.learningz.entity.MonthlyStatistic;
import com.project.learningz.service.MonthlyStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;

@RestController
@RequestMapping("/api/revenue")
public class RevenueController {
    @Autowired
    private MonthlyStatisticService monthlyStatisticService;

    @GetMapping("/monthly")
    public ResponseEntity<Map<String, Object>> getMonthlyRevenue(
            @RequestParam(required = false, defaultValue = "2025") int year) {

        List<MonthlyStatistic> statistics = monthlyStatisticService.getStatisticsForYear(year);
        Map<Integer, BigDecimal> revenueMap = new HashMap<>();
        for (MonthlyStatistic stat : statistics) {
            revenueMap.put(stat.getMonth(), stat.getTotalRevenue());
        }

        List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        List<BigDecimal> revenueData = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            revenueData.add(revenueMap.getOrDefault(i, BigDecimal.ZERO));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("months", months);
        response.put("revenue", revenueData);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/daily")
    public ResponseEntity<Map<String, Object>> getDailyRevenue(
            @RequestParam int month,
            @RequestParam(required = false, defaultValue = "2025") int year) {

        Map<Integer, BigDecimal> dailyRevenue = monthlyStatisticService.getDailyRevenue(year, month);
        List<String> days = new ArrayList<>();
        List<BigDecimal> revenueData = new ArrayList<>();

        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            days.add("Day " + day);
            revenueData.add(dailyRevenue.getOrDefault(day, BigDecimal.ZERO));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("days", days);
        response.put("revenue", revenueData);

        return ResponseEntity.ok(response);
    }
}
