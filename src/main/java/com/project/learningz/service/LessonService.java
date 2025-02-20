package com.project.learningz.service;

import com.project.learningz.entity.Chapter;
import com.project.learningz.entity.Course;
import com.project.learningz.entity.Lesson;
import com.project.learningz.entity.Quiz;
import com.project.learningz.repository.ChapterRepository;
import com.project.learningz.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.learningz.constant.QuizType;
import com.project.learningz.dto.LessonDetailDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private QuizResultService quizResultService;

    @Autowired
    private GoogleDriveService googleDriveService;

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
    public List<LessonDetailDTO> allLessonsByChapterId(Integer chapterId) {
        return lessonRepository.allLessonsByChapterId(chapterId);
    }

    public List<LessonDetailDTO> findLessons(Integer courseId, String keyword) {
        return lessonRepository.findLessons(courseId, keyword);
    }



    @Transactional
    public void updateLesson(int lessonId, int chapterId, String lessonDriveLink,
                             String documentFolderLink, String videoFolderLink, String quizImageLink,
                             String lessonTitle, QuizType quizType, String description) {
        Lesson lesson = lessonRepository.findLessonById(lessonId);
        lesson.setTitle(lessonTitle);
        lesson.setQuizType(quizType);
        lesson.setDescription(description);
        lessonRepository.save(lesson);
    }

    @Transactional
    public void createLesson(int chapterId, String lessonTitle, QuizType quizType, String description) throws GeneralSecurityException, IOException {
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonTitle);
        lesson.setQuizType(quizType);
        lesson.setDescription(description);
        lesson.setChapter(chapterRepository.findChapterById(chapterId));
        String lessonDriveLink = googleDriveService.createFolder(lessonTitle,
                chapterRepository.findChapterById(chapterId).getChapterDriveLink());
        lesson.setLessonDriveLink(lessonDriveLink);
        String documentFolderLink = googleDriveService.createFolder("Documents",lessonDriveLink);
        lesson.setDocumentFolderLink(documentFolderLink);
        String videoFolderLink = googleDriveService.createFolder("Videos",lessonDriveLink);
        lesson.setVideoFolderLink(videoFolderLink);
        String quizImageLink = googleDriveService.createFolder("Quiz Images",lessonDriveLink);
        lesson.setQuizImageLink(quizImageLink);
        lessonRepository.save(lesson);
    }
}
