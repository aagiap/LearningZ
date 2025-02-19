package com.project.learningz.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "title", columnDefinition = "NVARCHAR(255)")
    private String title;;

    @Column(name = "description", columnDefinition = "NVARCHAR(500)")
    private String description;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "status", nullable = false)
    private Boolean status;
}
