package com.project.learningz.repository;

import com.project.learningz.entity.VipPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VipPackageRepository extends JpaRepository<VipPackage, Integer> {
    List<VipPackage> findByDiscountedPriceNotNull();

    @Query("SELECT v FROM VipPackage v WHERE v.status = TRUE")
    List<VipPackage> findVipAvitvatedPackages();
}
