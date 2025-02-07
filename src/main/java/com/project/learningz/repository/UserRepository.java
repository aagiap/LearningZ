package com.project.learningz.repository;

import com.project.learningz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByEmail(String email);
    User findByUsername(String username);
    User findByResetPasswordToken(String token);
    User findByGoogleId(String googleId);

    @Query("SELECT u.avtUrl FROM User u WHERE u.username = ?1")
    String findAvatarUrlByUsername(String username);


}