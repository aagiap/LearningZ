package com.project.learningz.repository;

import com.project.learningz.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserManagementRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")

    public User findByResetPasswordToken(String token);

    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE LOWER(u.username) " +
            "LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(u.phoneNum) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(u.role) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    public List<User> findByKeyword(String keyword, Sort sort);

    @Query("SELECT u.avtUrl FROM User u WHERE u.username = ?1")
    String findAvatarUrlByUsername(String username);

    @Query("SELECT u.username FROM User u where u.email=?1")
    String findUserNameByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByPhoneNum(String phoneNum);
    User findByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'admin'")
    long countAdminUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'VIP_STUDENT'")
    long countVIPUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'TEACHER'")
    long countTeacherUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'STUDENT'")
    long countCasualStudentUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'MARKETING_TEAM'")
    long countMarketerUsers();

}
