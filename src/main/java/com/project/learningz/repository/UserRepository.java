package com.project.learningz.repository;

import com.project.learningz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByEmail(String email);
    User findByUsername(String username);
    User findByResetPasswordToken(String token);
    User findByGoogleId(String googleId);

    User findUserById(Integer id);

    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    Integer findIdByUserName(@Param("username") String username);

    @Query("SELECT u.avtUrl FROM User u WHERE u.username = ?1")
    String findAvatarUrlByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id=?1")
    User findById(int id);

    @Query("SELECT u FROM User u WHERE u.phoneNum=?1")
    User findByPhoneNumber(String phoneNumber);
    @Query("SELECT u.username FROM User u where u.email=?1")
    String findUserNameByEmail(String email);
}