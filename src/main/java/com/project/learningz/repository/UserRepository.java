package com.project.learningz.repository;

import com.project.learningz.constant.Role;
import com.project.learningz.dto.UserDetailDTO;
import com.project.learningz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    @Query("SELECT u.role FROM User u WHERE u.id= :userId")
    Role getRoleById(@Param("userId") Integer userId);

    @Query("SELECT u FROM User u WHERE u.role= :role")
    List<User> findByRole(Role role);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.id = :userId AND u.role = :role")
    boolean isNormalStudent(@Param("userId") Integer userId, @Param("role") Role role);

    @Query("""
    SELECT new com.project.learningz.dto.UserDetailDTO(
        uc.user.id,
        uc.user.username,
        uc.course.title
    )
    FROM UsersCourse uc
    WHERE uc.course.createdBy.id = ?1
    ORDER BY uc.user.id DESC
    LIMIT 3
""")
    List<UserDetailDTO> getTop3UserByTeacherId(int userId);
}