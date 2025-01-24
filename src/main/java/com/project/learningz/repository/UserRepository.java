package com.project.learningz.repository;


import com.project.learningz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);
    User findByUsername(String username);
    public User findByResetPasswordToken(String token);




}