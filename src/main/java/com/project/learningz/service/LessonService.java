package com.project.learningz.service;

import com.project.learningz.entity.Chapter;
import com.project.learningz.entity.Lesson;
import com.project.learningz.repository.ChapterRepository;
import com.project.learningz.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    public List<Lesson> getLessonsByChapterId(Integer chapterId) {
        return lessonRepository.findByChapterId(chapterId);
    }
    public List<Chapter> getChaptersByCourseId(Integer courseId) {
        return chapterRepository.findByCourseId(courseId);
    }
    public Lesson getLessonById(Integer lessonId) {
        return lessonRepository.findLessonById(lessonId);
    }

}
