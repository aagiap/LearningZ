package com.project.learningz.repository;

import com.project.learningz.entity.User;
import com.project.learningz.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Integer> {

    List<UserMembership> findByExpirationDateBefore(LocalDate date);

    UserMembership findTopByUserOrderByExpirationDateDesc(User user);
}
