package com.project.learningz.service;

import com.project.learningz.constant.Role;
import com.project.learningz.constant.UserStatus;
import com.project.learningz.dto.UserDetailDTO;
import com.project.learningz.entity.User;
import com.project.learningz.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    public List<String> userCheck(int id, String username, String phoneNumber, MultipartFile avatarUrl){
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
    public void updateUser(int id, String username, String phoneNumber, MultipartFile avatarUrl) throws GeneralSecurityException, IOException {
        User user = userRepository.findById(id);
        if(user != null && !username.equals(user.getUsername()) && username.length() > 0) {
            user.setUsername(username);
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
            user.setAvtUrl(googleDriveService.uploadFileAvatar(avatarUrl));
        }
        userRepository.save(user);
    }
    @Transactional
    public void updateUser(int id, String username, String phoneNumber)  {
        User user = userRepository.findById(id);
        if(user != null && !username.equals(user.getUsername()) && username.length() > 0) {
            user.setUsername(username);
        }
        if(user != null && !phoneNumber.equals(user.getPhoneNum()) && phoneNumber.length() > 0) {
            User userCheck = userRepository.findByPhoneNumber(phoneNumber);
            if(userCheck != null) {
                throw new IllegalStateException("phone already exist");
            }else{
                user.setPhoneNum(phoneNumber);
            }
        }
        userRepository.save(user);
    }

    public User getUserById(Integer userId) {
        return userRepository.findUserById(userId);
    }

    public Integer getUserIdByUsername(String username) {
        return userRepository.findIdByUserName(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findById(int id) {
        return userRepository.findById(id);
    }

    public String findUserNameByEmail(String email){
        return  userRepository.findUserNameByEmail(email);
    }

    public Role getRoleById(Integer userId) {
        return userRepository.getRoleById(userId);
    }

    public boolean isNormalStudent(Integer userId, Role role){
        return userRepository.isNormalStudent(userId, role);
    }

    public void save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        userRepository.save(user);
    }

    public List<UserDetailDTO> getTop3UserByTeacherId(int userId) {
        return userRepository.getTop3UserByTeacherId(userId);
    }

    public boolean banUserById(Integer userId){
        Optional<User> userOpt = userRepository.findById(userId);
        if(userOpt.isPresent()) {
            User user = userOpt.get();
            user.setUserStatus(UserStatus.BANNED);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}