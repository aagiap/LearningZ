package com.project.learningz.service;

import com.project.learningz.entity.User;
import com.project.learningz.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@AllArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GoogleDriveService googleDriveService;

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("Could not find any user with the email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return  userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    public String getAvtByUsername(String username) {
        return userRepository.findAvatarUrlByUsername(username);
    }

    public List<String> userCheck(int id, String username, String email, String phoneNumber){
        User user = userRepository.findById(id);
        List<String> errorList = new ArrayList<>();
        if(user == null) {
            errorList.add("user not found");
        }else{
            if(username.trim().length() == 0){
                errorList.add("username is empty");
            }else{
                User userCheck = userRepository.findByUsername(username);
                if(userCheck != null && userCheck.getId() != user.getId()) {
                    errorList.add("username already exist");
                }
            }
            if(email.trim().length() == 0){
                errorList.add("email is empty");
            }else{
                User userCheck = userRepository.findByEmail(email);
                if(userCheck != null && userCheck.getId() != user.getId()) {
                    errorList.add("email already exist");
                }
            }
            if(phoneNumber.trim().length() != 0){
                User userCheck = userRepository.findByPhoneNumber(phoneNumber);
                if(userCheck != null && userCheck.getId() != user.getId()) {
                    errorList.add("phone number already exist");
                }
            }
        }
        return errorList;
    }

    @Transactional
    public void updateUser(int id, String username, String email, String phoneNumber, String avatarUrl) {
        User user = userRepository.findById(id);
        if(user != null && !username.equals(user.getUsername()) && username.length() > 0) {
            user.setUsername(username);
        }
        if(user != null && !email.equals(user.getEmail()) && email.length() > 0) {
            User userCheck = userRepository.findByEmail(email);
            if(userCheck != null) {
                throw new IllegalStateException("email already exist");
            }else{
                user.setEmail(email);
            }
        }
        if(user != null && !phoneNumber.equals(user.getPhoneNum()) && phoneNumber.length() > 0) {
            User userCheck = userRepository.findByPhoneNumber(phoneNumber);
            if(userCheck != null) {
                throw new IllegalStateException("phone already exist");
            }else{
                user.setPhoneNum(phoneNumber);
            }
        }
        if(avatarUrl != null) {
            user.setAvtUrl(avatarUrl);
        }
        userRepository.save(user);
    }
}