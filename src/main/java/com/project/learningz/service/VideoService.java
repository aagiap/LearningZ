package com.project.learningz.service;

import com.project.learningz.entity.Video;
import com.project.learningz.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;

    public Video findByVideoId(Integer videoId) {
        return videoRepository.findByVideoId(videoId);
    }
}
