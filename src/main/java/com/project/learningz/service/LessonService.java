package com.project.learningz.service;

import com.project.learningz.entity.Chapter;
import com.project.learningz.entity.Course;
import com.project.learningz.entity.Lesson;
import com.project.learningz.entity.Quiz;
import com.project.learningz.repository.ChapterRepository;
import com.project.learningz.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private QuizResultService quizResultService;

    @Autowired
    private CourseService courseService;

    public List<Lesson> getLessonsByChapterId(Integer chapterId) {
        return lessonRepository.findByChapterId(chapterId);
    }

    public List<Chapter> getChaptersByCourseId(Integer courseId) {
        return chapterRepository.findByCourseId(courseId);
    }

    public Lesson getLessonById(Integer lessonId) {
        return lessonRepository.findLessonById(lessonId);
    }

    public List<String> isLessonCompleted(Integer userId, Integer courseId) {
        List<String> completionStatus = new ArrayList<>();
        List<Lesson> lessons = lessonRepository.findByCourseId(courseId);
        for (Lesson lesson : lessons) {
            List<Quiz> quizzes = lesson.getQuizzes();
            boolean allQuizzesCompleted = true;
            for (Quiz quiz : quizzes) {
                String quizResult = quizResultService.isPass(userId, quiz.getId());
                if (quizResult.equals("Not done yet") || quizResult.equals("Not pass")) {
                    allQuizzesCompleted = false;
                    break;
                }
            }
            if (allQuizzesCompleted) {
                completionStatus.add("Completed");
            } else {
                completionStatus.add("Not complete");
            }
        }
        return completionStatus;
    }

    public Integer getFirstLessonIdOfPreviousChapter(Chapter chapterCurrent, List<Chapter> chapters) {
        Integer chapterCurrentIndex = chapters.indexOf(chapterCurrent);
        if (chapterCurrentIndex == 0) {
            return null;
        }
        Chapter chapterPrevious = chapters.get(chapterCurrentIndex - 1);
        List<Lesson> lessons = lessonRepository.findByChapterId(chapterPrevious.getId());
        if (lessons.size() == 0) {
            return null;
        }
        return lessons.get(0).getId();
    }

    public Integer getFirstLessonIdOfNextChapter(Chapter chapterCurrent, List<Chapter> chapters) {
        Integer chapterCurrentIndex = chapters.indexOf(chapterCurrent);
        if (chapterCurrentIndex == chapters.size() - 1) {
            return null;
        }
        Chapter chapterNext = chapters.get(chapterCurrentIndex + 1);
        List<Lesson> lessons = lessonRepository.findByChapterId(chapterNext.getId());
        if (lessons.size() == 0) {
            return null;
        }
        return lessons.get(0).getId();
    }
}