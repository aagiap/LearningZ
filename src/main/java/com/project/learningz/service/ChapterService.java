package com.project.learningz.service;

import com.project.learningz.entity.Chapter;
import com.project.learningz.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;

    public Chapter getChapterById(Integer chapterId) {
        return chapterRepository.findChapterById(chapterId);
    }

    List<Chapter> getChaptersByCourseId(Integer courseId) {
        return chapterRepository.findByCourseId(courseId);
    }
}
