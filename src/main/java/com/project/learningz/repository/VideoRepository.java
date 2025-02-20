package com.project.learningz.repository;

import com.project.learningz.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("SELECT v FROM Video v WHERE v.id = :videoId")
    Video findByVideoId(@Param("videoId") Integer videoId);
    @Query("""
    select v from Video v
    WHERE v.lesson.id=?1
""")
    List<Video> getVideoByLessonId(int lessonId);

    @Query("""
    SELECT v FROM Video v WHERE v.lesson.id=?1 AND v.title LIKE CONCAT('%', ?2, '%')
""")
    List<Video> findVideo(int lessonId, String keyWord);

    @Query("""
    SELECT v FROM Video v
    WHERE v.id=?1
""")
    Video getVideoById(int videoId);
}
