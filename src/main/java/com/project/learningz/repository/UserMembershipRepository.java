package com.project.learningz.repository;

import com.project.learningz.entity.User;
import com.project.learningz.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Integer> {

    List<UserMembership> findByExpirationDateBefore(LocalDate date);

    UserMembership findTopByUserOrderByExpirationDateDesc(User user);

    @Query("SELECT um FROM UserMembership um where um.user.id= :userId ORDER BY um.registrationDate DESC")
    List<UserMembership> findByUserId(Integer userId);
}
