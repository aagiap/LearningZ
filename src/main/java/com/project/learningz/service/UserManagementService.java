package com.project.learningz.service;

import com.project.learningz.constant.Role;
import com.project.learningz.constant.UserStatus;
import com.project.learningz.entity.User;
import com.project.learningz.repository.UserManagementRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@AllArgsConstructor

public class UserManagementService {

    private final UserManagementRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> searchUsersSorted(Role role, String keyword, String sortField, String sortOrder) {
        Sort sort;
        if (sortOrder.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortField).descending();
        } else {
            sort = Sort.by(sortField).ascending();
        }
        return userRepository.searchUsersByRoleAndKeyword(role, keyword, sort);
    }

    public List<User> getAllUsersByKeyword(String keyword, String sortField, String sortOrder) {
        Sort sort;
        if (sortOrder.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortField).descending();
        } else {
            sort = Sort.by(sortField).ascending();
        }
        return userRepository.getAllUsersByKeyword(keyword, sort);
    }

    public void banUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getUserStatus() == UserStatus.BANNED) {
            throw new RuntimeException("User is already banned");
        }
        user.setUserStatus(UserStatus.BANNED);
        userRepository.save(user);
    }

    public void createUser(String username, String email, String password, String phoneNum, String role) {
        if (password.length() < 6) {
            throw new RuntimeException("Password must be at least 6 digits");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email has already exists: " + email);
        }
        if (userRepository.existsByPhoneNum(phoneNum)) {
            throw new RuntimeException("Phone number has already exists: " + phoneNum);
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setPhoneNum(phoneNum);
        user.setAvtUrl("AvartaDefault.jpg");
        user.setUserStatus(UserStatus.ACTIVE);

        try {
            user.setRole(Role.valueOf(role.toUpperCase()));
            userRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + role);
        }
    }


    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateUser(User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isPresent()) {
            User updatedUser = userOptional.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPhoneNum(user.getPhoneNum());
            updatedUser.setRole(user.getRole());
            updatedUser.setUserStatus(user.getUserStatus());
            userRepository.save(updatedUser);
        }
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public String getAvtByUsername(String username) {
        return userRepository.findAvatarUrlByUsername(username);
    }

    public String findUserNameByEmail(String email) {
        return userRepository.findUserNameByEmail(email);
    }

    public long getNumberOfUsers() {
        List<User> users = userRepository.findAll();
        return users.size();
    }

    public long getNumberOfAdminSuperUsers() {
        return userRepository.countAdminSuperUsers();
    }

    public long getNumberOfAdminUserManageUsers() {
        return userRepository.countAdminUserManageUsers();
    }

    public long getNumberOfAdminCourseManageUsers() {
        return userRepository.countAdminCourseManageUsers();
    }


    public long getNumberOfVipUsers() {
        return userRepository.countVIPUsers();
    }

    public long getNumberOfMarketerUsers() {
        return userRepository.countMarketerUsers();
    }

    public long getNumberOfCasualStudentUsers() {
        return userRepository.countCasualStudentUsers();
    }

    public long getNumberOfTeacherUsers() {
        return userRepository.countTeacherUsers();
    }

    public List<User> getFiveLatestUsers() {
        return userRepository.findAll(PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"))).getContent();
    }

    public Long countActiveUsersByStatus() {
        return userRepository.countActiveUsers();
    }

    public Long countInactiveUsersByStatus() {
        return userRepository.countBannedUsers();
    }


}

