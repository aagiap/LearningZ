package com.project.learningz.service;

import com.project.learningz.entity.Chapter;
import com.project.learningz.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.project.learningz.dto.ChapterDetailDTO;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private GoogleDriveService googleDriveService;

    public Chapter getChapterById(Integer chapterId) {
        return chapterRepository.findChapterById(chapterId);
    }

    List<Chapter> getChaptersByCourseId(Integer courseId) {
        return chapterRepository.findByCourseId(courseId);
    }

    public List<ChapterDetailDTO> allChaptersByCourseId(int courseId) {
        return chapterRepository.allChapterByCourseId(courseId);
    }

    public List<ChapterDetailDTO> findChapters(int courseId, String keyword) {
        return chapterRepository.findChapters(courseId, keyword);
    }

    public String checkUpdate(int id,int courseId, int order, String title){
        List<String> errorList = new ArrayList<>();
        if(title == null || title.trim().isEmpty()){
            errorList.add("Please input title");
        }
        if(findChapter(courseId,order) != null
                && getChapterById(id).getChapterOrder() != order){
            errorList.add("Order already exists");
        }
        String error = "";
        if(!errorList.isEmpty()){
            for(int i = 0; i < errorList.size(); i++){
                error += errorList.get(i) + " ";
            }
        }
        return error;
    }

    public Chapter findChapter(Integer courseId, Integer order) {
        return chapterRepository.findChapter(courseId, order);
    }

    @Transactional
    public void updateChapter(int chapterId, int order, String title, String description) throws GeneralSecurityException, IOException {
        Chapter chapter = getChapterById(chapterId);
        chapter.setChapterOrder(order);
        chapter.setChapterTitle(title);
        chapter.setDescription(description);
        googleDriveService.renameFolder(getChapterById(chapterId).getChapterDriveLink(),
                "Chapter " + order + ": " + title);
        chapterRepository.save(chapter);
    }

    public String checkCreate(int courseId, int order, String title) {
        List<String> errorList = new ArrayList<>();
        if(title == null || title.trim().isEmpty()){
            errorList.add("Please input title");
        }
        if(findChapter(courseId,order) != null){
            errorList.add("Order already exists");
        }
        String error = "";
        if(!errorList.isEmpty()){
            for(int i = 0; i < errorList.size(); i++){
                error += errorList.get(i) + " ";
            }
        }
        return error;
    }

    @Transactional
    public void createChapter(int courseId, int order, String title, String description) throws GeneralSecurityException, IOException {
        Chapter chapter = new Chapter();
        chapter.setChapterOrder(order);
        chapter.setChapterTitle(title);
        chapter.setDescription(description);
        chapter.setCourse(courseService.getCourseById(courseId));
        String chapterDriveLink = googleDriveService
                .createFolder("Chapter " + order + ": " + title,courseService.getCourseById(courseId).getCourseDriveLink());
        chapter.setChapterDriveLink(chapterDriveLink);
        chapterRepository.save(chapter);
    }

    public List<String> getAllChaptersInQuestions() {
        return chapterRepository.getAllChaptersInQuestions();
    }

}
