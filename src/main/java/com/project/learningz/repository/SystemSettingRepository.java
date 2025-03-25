package com.project.learningz.repository;

import com.project.learningz.entity.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemSettingRepository extends JpaRepository<SystemSetting,Integer> {

    SystemSetting findBySettingName(String settingName);
}
