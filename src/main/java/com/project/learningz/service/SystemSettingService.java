package com.project.learningz.service;

import com.project.learningz.entity.SystemSetting;
import com.project.learningz.repository.SystemSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemSettingService {
    @Autowired
    private SystemSettingRepository systemSettingRepository;

    public SystemSettingService() {
    }

    public List<SystemSetting> updateSystemSetting(List<SystemSetting> systemSettings){
        for (SystemSetting systemSetting : systemSettings) {
            systemSettingRepository.save(systemSetting);
        }
        return systemSettings;
    }

    public List<SystemSetting> getAllSystemSetting(){
        return systemSettingRepository.findAll();
    }
}
