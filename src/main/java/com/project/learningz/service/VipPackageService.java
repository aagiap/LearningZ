package com.project.learningz.service;

import com.project.learningz.entity.VipPackage;
import com.project.learningz.repository.UserMembershipRepository;
import com.project.learningz.repository.VipPackageRepository;
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
}
