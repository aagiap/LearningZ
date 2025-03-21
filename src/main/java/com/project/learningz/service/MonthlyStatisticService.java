package com.project.learningz.service;

import com.project.learningz.entity.MonthlyStatistic;
import com.project.learningz.repository.MonthlyStatisticRepository;
import com.project.learningz.repository.UserMembershipRepository;
import com.project.learningz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MonthlyStatisticService {
    private final MonthlyStatisticRepository monthlyStatisticRepository;
    private final UserMembershipRepository userMembershipRepository;
    private final UserRepository userRepository;

    @Autowired
    public MonthlyStatisticService(MonthlyStatisticRepository monthlyStatisticRepository,
                                   UserMembershipRepository userMembershipRepository,
                                   UserRepository userRepository) {
        this.monthlyStatisticRepository = monthlyStatisticRepository;
        this.userMembershipRepository = userMembershipRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void updateMonthlyStatistic() {
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();

        // Lấy danh sách các tháng có dữ liệu từ User & UserMembership
        Set<String> uniqueMonths = getUniqueMonthsWithData();
        uniqueMonths.add(currentYear + "-" + currentMonth); // Đảm bảo cập nhật tháng hiện tại

        for (String monthStr : uniqueMonths) {
            String[] parts = monthStr.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);

            updateStatisticForMonth(year, month, year == currentYear && month == currentMonth);
        }
    }

    private Set<String> getUniqueMonthsWithData() {
        List<Object[]> userMonths = userRepository.findDistinctMonthsWithDataFromUsers();
        List<Object[]> membershipMonths = userMembershipRepository.findDistinctMonthsWithDataFromUserMemberships();

        Set<String> uniqueMonths = new HashSet<>();
        for (Object[] obj : userMonths) {
            uniqueMonths.add(obj[0] + "-" + obj[1]);
        }
        for (Object[] obj : membershipMonths) {
            uniqueMonths.add(obj[0] + "-" + obj[1]);
        }
        return uniqueMonths;
    }

    private void updateStatisticForMonth(int year, int month, boolean isCurrentMonth) {
        MonthlyStatistic statistic = monthlyStatisticRepository.findByYearAndMonth(year, month)
                .orElseGet(() -> new MonthlyStatistic(year, month));

        statistic.setTotalUsersRegistered(userRepository.countUsersByMonth(year, month));
        statistic.setTotalCoursesRegistered(userMembershipRepository.countRegistrationsInMonth(year, month));
        statistic.setTotalRevenue(userMembershipRepository.calculateTotalRevenueInMonth(year, month));

        if (isCurrentMonth) {
            statistic.setTotalVisits(statistic.getTotalVisits() + 1);
        }

        monthlyStatisticRepository.saveAndFlush(statistic);


    }

    public MonthlyStatistic getMonthlyStatistic(int year, int month) {
        return monthlyStatisticRepository.findByYearAndMonth(year, month)
                .orElseGet(() -> new MonthlyStatistic(year, month));
    }
    public List<MonthlyStatistic> getStatisticsForYear(int year) {
        return monthlyStatisticRepository.findByYear(year);
    }

}
