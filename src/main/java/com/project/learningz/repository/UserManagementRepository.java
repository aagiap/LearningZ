package com.project.learningz.repository;

import com.project.learningz.constant.Role;
import com.project.learningz.constant.UserStatus;
import com.project.learningz.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserManagementRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")

    public User findByResetPasswordToken(String token);

    User findByUsername(String username);


    @Query("SELECT u FROM User u WHERE u.role = :role " +
            "AND (:keyword IS NULL OR " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.phoneNum) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<User> searchUsersByRoleAndKeyword(@Param("role") Role role,
                                           @Param("keyword") String keyword,
                                           Sort sort);

    @Query("SELECT u FROM User u WHERE (:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.phoneNum) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<User> getAllUsersByKeyword(@Param("keyword") String keyword, Sort sort);


    @Query("SELECT u.avtUrl FROM User u WHERE u.username = ?1")
    String findAvatarUrlByUsername(String username);

    @Query("SELECT u.username FROM User u where u.email=?1")
    String findUserNameByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhoneNum(String phoneNum);

    User findByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'admin'")
    long countAdminSuperUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'VIP_STUDENT'")
    long countVIPUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'TEACHER'")
    long countTeacherUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'STUDENT'")
    long countCasualStudentUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'MARKETING_TEAM'")
    long countMarketerUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'ADMIN_USER_MANAGER'")
    long countAdminUserManageUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'ADMIN_COURSE_MANAGER'")
    long countAdminCourseManageUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.userStatus = 'BANNED'")
    long countBannedUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.userStatus = 'ACTIVE'")
    long countActiveUsers();


}
