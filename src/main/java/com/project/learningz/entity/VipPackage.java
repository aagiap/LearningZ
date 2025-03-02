package com.project.learningz.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class VipPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vip_package_id", nullable = false)
    private Integer id;

    @NotNull
    @Size(max = 255)
    @Nationalized
    @Column(name = "package_name", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String packageName;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Integer duration;

    //    @NotNull
//    @Column(name = "price", nullable = false, precision = 13, scale = 2)
//    private BigDecimal price;
    @NotNull
    @Column(name = "price", nullable = false)
    private long price;

    @OneToMany(mappedBy = "vipPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserMembership> userMemberships = new ArrayList<>();


}
