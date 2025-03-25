package com.project.learningz.repository;

import com.project.learningz.entity.User;
import com.project.learningz.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Integer> {

    List<UserMembership> findByExpirationDateBefore(LocalDate date);

    UserMembership findTopByUserOrderByExpirationDateDesc(User user);

    @Query("SELECT um FROM UserMembership um where um.user.id= :userId ORDER BY um.registrationDate DESC")
    List<UserMembership> findByUserId(Integer userId);

    @Query("SELECT COUNT(u) FROM UserMembership u WHERE YEAR(u.registrationDate) = :year AND MONTH(u.registrationDate) = :month")
    int countRegistrationsInMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT DISTINCT YEAR(um.registrationDate), MONTH(um.registrationDate) FROM UserMembership um WHERE um.registrationDate IS NOT NULL")
    List<Object[]> findDistinctMonthsWithDataFromUserMemberships();

    @Query("SELECT COALESCE(SUM(um.paidPrice), 0) FROM UserMembership um WHERE YEAR(um.registrationDate) = :year AND MONTH(um.registrationDate) = :month")
    BigDecimal calculateTotalRevenueInMonth(@Param("year") int year, @Param("month") int month);
    @Query("SELECT DAY(u.registrationDate), SUM(u.paidPrice) " +
            "FROM UserMembership u " +
            "WHERE YEAR(u.registrationDate) = :year AND MONTH(u.registrationDate) = :month " +
            "GROUP BY DAY(u.registrationDate) " +
            "ORDER BY DAY(u.registrationDate)")
    List<Object[]> getDailyRevenue(@Param("year") int year, @Param("month") int month);

}
