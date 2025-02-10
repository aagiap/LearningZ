package com.project.learningz.controller;

import com.project.learningz.constant.Role;
import com.project.learningz.entity.User;
import com.project.learningz.repository.UserRepository;
import com.project.learningz.service.GoogleDriveService;
import com.project.learningz.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {
    int countUpdate = 0;
    int idReload = 0;
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoogleDriveService googleDriveService;
    @GetMapping(path = "/home/profile")
    public String profile(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        String avatarUrl = null;
        String email = null;
        String phoneNumber = null;
        Role role = null;
        if (principal instanceof OAuth2User){
            username = ((OAuth2User) principal).getAttribute("name");
            avatarUrl = ((OAuth2User) principal).getAttribute("picture");
            email = ((OAuth2User) principal).getAttribute("email");
            User user = userRepository.findByEmail(email);
            if (user != null) {
                role = user.getRole();
            }
        }else{
            username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username);
            if (user != null) {
                avatarUrl = user.getAvtUrl();
                email = user.getEmail();
                phoneNumber = user.getPhoneNum();
                role = user.getRole();
            }
        }
        if(countUpdate != 0 && idReload != 0){
            User user = userRepository.findById(idReload);
            username = user.getUsername();
            avatarUrl = user.getAvtUrl();
            email = user.getEmail();
            phoneNumber = user.getPhoneNum();
        }
        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("email", email);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("role", role);
        return "profile/profile";
    }

    @GetMapping(path = "/home/profile/profile_edit")
    public String profile_edit(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = 0;
        String username;
        String avatarUrl = null;
        String email = null;
        String phoneNumber = null;
        if (principal instanceof OAuth2User){
            username = ((OAuth2User) principal).getAttribute("name");
            avatarUrl = ((OAuth2User) principal).getAttribute("picture");
            email = ((OAuth2User) principal).getAttribute("email");
            User user = userRepository.findByEmail(email);
            if (user != null) {
                userId = user.getId();
            }
        }else{
            username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username);
            if (user != null) {
                userId = user.getId();
                avatarUrl = user.getAvtUrl();
                email = user.getEmail();
                phoneNumber = user.getPhoneNum();
            }
        }
        if(countUpdate != 0 && idReload != 0){
            User user = userRepository.findById(idReload);
            userId = user.getId();
            username = user.getUsername();
            avatarUrl = user.getAvtUrl();
            email = user.getEmail();
            phoneNumber = user.getPhoneNum();
        }
        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("userId", userId);
        return "profile/profile_edit";
    }

    @PostMapping(path = "/home/profile/profile_edit/update_user")
    public String updateProfile(Model model,
                                @RequestParam("userId") int id,
                                @RequestParam("avatarUrl") MultipartFile avatarFile,
                                @RequestParam("username") String username,
                                @RequestParam("phoneNumber") String phoneNumber,
                                HttpSession session) throws GeneralSecurityException, IOException {
        List<String> errors = new ArrayList<String>();
                errors = userService.userCheck(id, username, phoneNumber);
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("username", username);
            model.addAttribute("phoneNumber", phoneNumber);
            model.addAttribute("userId", id);
            if(avatarFile != null){
                model.addAttribute("avatarUrl", avatarFile.getOriginalFilename());
            }else{
                User user = userRepository.findById(id);
                model.addAttribute("avatarUrl", user.getAvtUrl());
            }
        }else{
            String avatarUrl = null;
            if(!avatarFile.isEmpty()){
                try{
                    avatarUrl = googleDriveService.uploadFile(avatarFile);
                }catch(IOException | GeneralSecurityException e){
                    model.addAttribute("errors", List.of("Failed to upload file"));
                    model.addAttribute("errors", errors);
                    model.addAttribute("username", username);
                    model.addAttribute("phoneNumber", phoneNumber);
                    model.addAttribute("userId", id);
                    model.addAttribute("avatarUrl", avatarUrl);
                    return "profile/profile_edit";
                }
            }
            countUpdate++;
            idReload = id;

            session.setAttribute("countUpdate", countUpdate);
            session.setAttribute("idReload", idReload);


            User user = userRepository.findById(id);
            if(user.getAvtUrl().contains("https://lh3.googleusercontent.com/d/")){
                String[] oldAvtId = user.getAvtUrl().split("https://lh3.googleusercontent.com/d/");
                googleDriveService.deleteFile(oldAvtId[1]);
            }

            userService.updateUser(id, username, phoneNumber, avatarUrl);
            model.addAttribute("notification","Update success");
            model.addAttribute("username", username);
            model.addAttribute("phoneNumber", phoneNumber);
            model.addAttribute("userId", id);
            model.addAttribute("avatarUrl", avatarUrl);
        }
        return "profile/profile_edit";
    }
}