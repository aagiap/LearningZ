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
        int countError = 0;
        if(!errorList.isEmpty()){
            for(int i = 0; i < errorList.size(); i++){
                if(countError == errorList.size() - 1){
                    error += errorList.get(i) + " ";
                }else{
                    error += errorList.get(i) + " and ";
                }
                countError++;
            }
        }
        return error;
    }

    public Chapter findChapter(Integer courseId, Integer order) {
        return chapterRepository.findChapter(courseId, order);
    }

    @Transactional
    public void updateChapter(int chapterId, int order, String title, String description, String chapterDriveLink) throws GeneralSecurityException, IOException {
        //add note for course note when sth change
        int countChange = 0;
        String courseNote = "";
        Chapter chapter = getChapterById(chapterId);

        if (chapter.getChapterOrder() != order) {
            countChange++;
            courseNote += "change chapter order from " + chapter.getChapterOrder() + " to " + order + ".";
        }
        chapter.setChapterOrder(order);

        if (!chapter.getChapterTitle().equalsIgnoreCase(title)) {
            countChange++;
            courseNote += "change chapter title of chapter " + order + " from " + chapter.getChapterTitle() + " to " + title + ".";
        }
        chapter.setChapterTitle(title);

        if(!chapter.getDescription().equalsIgnoreCase(description)){
            countChange++;
            courseNote += "change chapter description of chapter " + order + " to " + description + ".";
        }
        chapter.setDescription(description);

        if(chapterDriveLink != null && !chapterDriveLink.trim().isEmpty()) {
            googleDriveService.renameFolder(chapterDriveLink,
                    "Chapter " + order + ": " + title);
        }

        if(countChange != 0){
            courseService.setPendingCourse(chapterRepository.findChapterById(chapterId).getCourse().getId(),
                    courseNote);
        }
        chapterRepository.save(chapter);
    }

    public boolean checkUpdateChapter(int chapterId, int chapterOrder, String chapterTitle, String description){
        Chapter chapter = getChapterById(chapterId);
        if(chapter.getChapterOrder() != chapterOrder){
            return true;
        }
        if(!chapter.getChapterTitle().equalsIgnoreCase(chapterTitle)){
            return true;
        }
        if(!chapter.getDescription().equalsIgnoreCase(description)){
            return true;
        }
        return false;
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
        int countError = 0;
        if(!errorList.isEmpty()){
            for(int i = 0; i < errorList.size(); i++){
                if(countError == errorList.size() - 1){
                    error += errorList.get(i) + " ";
                }else{
                    error += errorList.get(i) + " and ";
                }
                countError++;
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
        if(courseService.getCourseById(courseId).getCourseDriveLink() != null
                && !courseService.getCourseById(courseId).getCourseDriveLink().trim().isEmpty()) {
            String chapterDriveLink = googleDriveService
                    .createFolder("Chapter " + order + ": " + title,courseService.getCourseById(courseId).getCourseDriveLink());
            chapter.setChapterDriveLink(chapterDriveLink);
        }
        courseService.setPendingCourse(courseId, "add chapter " + order + ": " + title + ".");
        chapterRepository.save(chapter);
    }

    public List<String> getAllChaptersInQuestions() {
        return chapterRepository.getAllChaptersInQuestions();
    }

}
