package com.project.learningz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sliders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Slider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slider_id")
    private Integer sliderId;

    @Column(name = "tittle", length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "ntext")
    private String description;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "status", nullable = false)
    private Boolean status;
}