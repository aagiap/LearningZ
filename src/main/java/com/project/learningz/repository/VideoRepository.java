package com.project.learningz.repository;

import com.project.learningz.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

    @Query("SELECT v FROM Video v WHERE v.id = :videoId")
    Video findByVideoId(@Param("videoId") Integer videoId);
}
