package com.project.learningz.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "username", nullable = false)
    private String username;

    @Size(max = 255)
    @Nationalized
    @Column(name = "avt_url")
    private String avtUrl;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 255)
    @Column(name = "email", unique = true)
    private String email;

    @Size(max = 50)
    @Column(name = "phoneNum", length = 50)
    private String phoneNum;

    @Size(max = 50)
    @Column(name = "Role", length = 50)
    private String role;

    @Size(max = 500)
    @Column(name = "google_id", length = 500)
    private String googleId;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

}