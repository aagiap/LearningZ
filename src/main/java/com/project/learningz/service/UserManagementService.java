package com.project.learningz.service;

import com.project.learningz.constant.Role;
import com.project.learningz.entity.User;
import com.project.learningz.repository.UserManagementRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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

    public void updatePassword(User user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    public void encryptExistingPassword() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            String password = user.getPassword();
            if (!password.startsWith("$2a$")) {
                String encryptedPassword = passwordEncoder.encode(password);
                user.setPassword(encryptedPassword);
                userRepository.save(user);
            }
        }
    }

    public List<User> getAllUsersSorted(String sortField, String sortOrder) {
        Sort sort;
        if (sortOrder.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortField).descending();
        } else {
            sort = Sort.by(sortField).ascending();
        }
        return userRepository.findAll(sort);
    }

    public List<User> searchUsersSorted(String keyword, String sortField, String sortOrder) {
        Sort sort;
        if (sortOrder.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortField).descending();
        } else {
            sort = Sort.by(sortField).ascending();
        }
        return userRepository.findByKeyword(keyword.toUpperCase(), sort);
    }

    public void deleteUserById(Integer id) {
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete this user due to data integrity constraints.");
        }
    }

    public void createUser(String username, String email, String password, String phoneNum, String role) {
        if (password.length() < 6) {
            throw new RuntimeException("Password must be at least 6 digits");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists: " + email);
        }
        if (userRepository.existsByPhoneNum(phoneNum)) {
            throw new RuntimeException("Phone number already exists: " + phoneNum);
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setPhoneNum(phoneNum);
        user.setAvtUrl("AvartaDefault.jpg");

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

    public void updateUser(User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isPresent()) {
            User updatedUser = userOptional.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPhoneNum(user.getPhoneNum());
            updatedUser.setRole(user.getRole());
            userRepository.save(updatedUser);
        }
    }

}

