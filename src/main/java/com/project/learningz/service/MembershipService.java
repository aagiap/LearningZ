package com.project.learningz.service;

import com.project.learningz.constant.Role;
import com.project.learningz.entity.User;
import com.project.learningz.entity.UserMembership;
import com.project.learningz.repository.UserMembershipRepository;
import com.project.learningz.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor

public class MembershipService {
    private static final Logger logger = LoggerFactory.getLogger(MembershipService.class);

    private final UserRepository userRepository;
    private final UserMembershipRepository userMembershipRepository;

    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Ho_Chi_Minh")
    @Transactional
    public void downgradeExpiredVipUsers() {
        System.out.println("Scheduled Task Running: " + LocalDate.now());
        LocalDate today = LocalDate.now();
        List<User> vipUsers = userRepository.findByRole(Role.VIP_STUDENT);
        for (User user : vipUsers) {
            UserMembership latestMembership = userMembershipRepository.findTopByUserOrderByExpirationDateDesc(user);
            if (latestMembership == null || latestMembership.getExpirationDate().isBefore(today)) {
                user.setRole(Role.STUDENT);
                userRepository.save(user);
                System.out.println("Downgrade User: " + user.getId());
            }
        }
        System.out.println("Scheduled Task done!");
    }

    public void save(UserMembership userMembership) {
        if (userMembership == null) {
            throw new IllegalArgumentException("User Membership cannot be null");
        }
        userMembershipRepository.save(userMembership);
    }
}
