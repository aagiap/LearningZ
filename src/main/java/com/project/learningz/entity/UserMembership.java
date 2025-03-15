package com.project.learningz.entity;

import com.project.learningz.constant.MembershipStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class UserMembership {
    @Id
    @Column(name = "membership_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vip_package_id", nullable = false)
    private VipPackage vipPackage;

    @NotNull
    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @NotNull
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MembershipStatus status;


    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
        if (vipPackage != null) {
            this.expirationDate = registrationDate.plusMonths(vipPackage.getDuration());
        }
    }

}