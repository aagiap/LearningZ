package com.project.learningz.service;

import com.project.learningz.entity.VipPackage;
import com.project.learningz.repository.UserMembershipRepository;
import com.project.learningz.repository.VipPackageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VipPackageService {
    @Autowired
    private VipPackageRepository vipPackageRepository;



    public List<VipPackage> getAllVipPackages() {
        return vipPackageRepository.findAll();
    }

    public VipPackage getVipPackageById(int id) {
        return vipPackageRepository.findById(id).get();
    }

    @Transactional
    public VipPackage save(VipPackage vipPackage) {
        return vipPackageRepository.save(vipPackage);
    }

    public VipPackage findById(Integer id) {
        return vipPackageRepository.findById(id).orElse(null);
    }

    public void toggleVisibility(Integer id) {
        vipPackageRepository.findById(id)
                .ifPresent(vipPackage -> {
                    vipPackage.setStatus(!vipPackage.getStatus()); // Đảo trạng thái
                    vipPackageRepository.save(vipPackage);
                });
    }
    public void deleteById(Integer id) {
        vipPackageRepository.deleteById(id);
    }
}
