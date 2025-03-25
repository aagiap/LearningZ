package com.project.learningz.dto;

import com.project.learningz.entity.SystemSetting;

import java.util.List;

public class SettingListDTO {
    private List<SystemSetting> settings;

    public SettingListDTO(List<SystemSetting> settings) {
        this.settings = settings;
    }

    public List<SystemSetting> getSettings() {
        return settings;
    }

    public void setSettings(List<SystemSetting> settings) {
        this.settings = settings;
    }
}
