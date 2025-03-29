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
            if(quizzes.isEmpty() || quizzes == null) {
                completionStatus.add("null");
                continue;
            }
            String allQuizzesCompleted = "Completed";
            for (Quiz quiz : quizzes) {
                String quizResult = quizResultService.isPass(userId, quiz.getId());
                if (quizResult.equals("Not done yet") || quizResult.equals("Not pass")) {
                    allQuizzesCompleted = "Not complete";
                    break;
                }
            }
             if (allQuizzesCompleted.equals("Completed")) {
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

    @Transactional
    public void updateLesson(int lessonId, int chapterId, String lessonDriveLink,
                             String documentFolderLink, String videoFolderLink, String quizImageLink,
                             String lessonTitle, QuizType quizType, String description) throws GeneralSecurityException, IOException {
        Lesson lesson = lessonRepository.findLessonById(lessonId);
        lesson.setTitle(lessonTitle);
        lesson.setQuizType(quizType);
        lesson.setDescription(description);

        if(lessonDriveLink!= null && !lessonDriveLink.isEmpty()){
            googleDriveService.renameFolder(lessonDriveLink, lessonTitle);
        }
        lessonRepository.save(lesson);
    }

    @Transactional
    public void createLesson(int chapterId, String lessonTitle, QuizType quizType, String description) throws GeneralSecurityException, IOException {
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonTitle);
        lesson.setQuizType(quizType);
        lesson.setDescription(description);
        lesson.setChapter(chapterRepository.findChapterById(chapterId));
        if(chapterRepository.findChapterById(chapterId).getChapterDriveLink() != null
                && !chapterRepository.findChapterById(chapterId).getChapterDriveLink().isEmpty()){
            String lessonDriveLink = googleDriveService.createFolder(lessonTitle,
                    chapterRepository.findChapterById(chapterId).getChapterDriveLink());
            lesson.setLessonDriveLink(lessonDriveLink);
            String documentFolderLink = googleDriveService.createFolder("Documents",lessonDriveLink);
            lesson.setDocumentFolderLink(documentFolderLink);
            String videoFolderLink = googleDriveService.createFolder("Videos",lessonDriveLink);
            lesson.setVideoFolderLink(videoFolderLink);
            String quizImageLink = googleDriveService.createFolder("Quiz Images",lessonDriveLink);
            lesson.setQuizImageLink(quizImageLink);
        }
        lessonRepository.save(lesson);
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

    public List<String> getAllLessonInQuestions() {
        return lessonRepository.getAllLesson();
    }
}