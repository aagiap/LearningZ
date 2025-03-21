package com.project.learningz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "monthly_statistic")
public class MonthlyStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "month", nullable = false)
    private Integer month;

    @Column(name = "total_users_registered", nullable = false)
    private Integer totalUsersRegistered ;

    @Column(name = "total_courses_registered", nullable = false)
    private Integer totalCoursesRegistered ;

    @Column(name = "total_revenue", nullable = false)
    private BigDecimal totalRevenue ;

    @Column(name = "total_visits", nullable = false)
    private Integer totalVisits ;

    public MonthlyStatistic(int year, int month) {
        this.year = year;
        this.month = month;
        this.totalVisits = 0;
        this.totalUsersRegistered = 0;
        this.totalCoursesRegistered = 0;
        this.totalRevenue = BigDecimal.ZERO;
    }

    public MonthlyStatistic() {

    }
}
