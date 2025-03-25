package com.project.learningz.controller;


import com.project.learningz.dto.SettingListDTO;
import com.project.learningz.entity.SystemSetting;
import com.project.learningz.service.SystemSettingService;
import com.project.learningz.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/super_admin")
public class SystemSettingController {
    @Autowired
    private SystemSettingService systemSettingService;

    @Autowired
    private UserManagementService userManagementService;

    @GetMapping("/system-setting")
    private String systemSetting(Model model,
                                 @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                 @AuthenticationPrincipal OAuth2User userOAuth2) {
        String username = null;

        if (user != null) {
            username = user.getUsername();
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userManagementService.findUserNameByEmail(email);
        }
        String avatarUrl = userManagementService.getAvtByUsername(username);
        if (avatarUrl == null) {
            avatarUrl = "/image/AvartaDefault.jpg";
        }
        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        List<SystemSetting> systemSettings = systemSettingService.getAllSystemSetting();
        model.addAttribute("systemSettings", systemSettings);
        return "user_management/system_setting";
    }

    @PostMapping("/update-system-setting")
    private String updateSystemSetting(@ModelAttribute SettingListDTO systemSettings, Model model,
                                       @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                       @AuthenticationPrincipal OAuth2User userOAuth2) {

        String username = null;

        if (user != null) {
            username = user.getUsername();
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userManagementService.findUserNameByEmail(email);
        }
        String avatarUrl = userManagementService.getAvtByUsername(username);
        if (avatarUrl == null) {
            avatarUrl = "/image/AvartaDefault.jpg";
        }
        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);

        List<SystemSetting> systemSettingList = systemSettings.getSettings();
        List<SystemSetting> newSetting = systemSettingService.updateSystemSetting(systemSettingList);
        model.addAttribute("message", "Update system setting successful!");
        model.addAttribute("systemSettings", newSetting);
        return "user_management/system_setting";

    }
}
