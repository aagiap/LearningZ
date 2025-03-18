package com.project.learningz.service;

import com.project.learningz.entity.Video;
import com.project.learningz.repository.VideoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private GoogleDriveService googleDriveService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private CourseService courseService;

    public Video getVideoById(int videoId) {
        return videoRepository.getVideoById(videoId);
    }

    public List<Video> getVideoListByLessonId(int lessonId){
        return videoRepository.getVideoByLessonId(lessonId);
    }

    public List<Video> findVideo(int lessonId, String keyWord) {
        return videoRepository.findVideo(lessonId, keyWord);
    }

    public String videoCheck(String title, MultipartFile file) {
        List<String> errorList = new ArrayList<>();
        if(title.trim().isEmpty()){
            errorList.add("empty title");
        }
        if(file.isEmpty()){
            errorList.add("empty file");
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
    public void createVideo(int lessonId, String videoTitle, MultipartFile videoFile) throws GeneralSecurityException, IOException {
        Video video = new Video();
        video.setTitle(videoTitle);
        String videoUrl = googleDriveService.uploadFileVideo(videoFile,
                lessonService.getLessonById(lessonId).getVideoFolderLink());
        video.setFileUrl(videoUrl);
        video.setLesson(lessonService.getLessonById(lessonId));

        courseService.setPendingCourse(lessonService.getLessonById(lessonId).getChapter().getCourse().getId(),
                "add new video for lesson in chapter " + lessonService.getLessonById(lessonId).getChapter().getChapterOrder() + ".");

        videoRepository.save(video);
    }

    @Transactional
    public void updateVideo(int lessonId, int videoId, String videoTitle, MultipartFile videoFile) throws GeneralSecurityException, IOException {
        int countChange = 0;
        String courseNote = "";

        Video video = videoRepository.getVideoById(videoId);

        if(!video.getTitle().equalsIgnoreCase(videoTitle)){
            countChange++;
            courseNote += "change title for video in lesson of chapter " +
                    lessonService.getLessonById(lessonId).getChapter().getChapterOrder() +
                    " to " + videoTitle + ".";
        }
        video.setTitle(videoTitle);
        String videoUrl;
        if (videoFile != null && !videoFile.isEmpty()) {
            videoUrl = googleDriveService.uploadFileVideo(videoFile,
                    lessonService.getLessonById(lessonId).getVideoFolderLink());
            String oldVideoUrl = video.getFileUrl();
            String[] oldVideoUrlString = oldVideoUrl.split("/");
            googleDriveService.deleteFile(oldVideoUrlString[5]);
            video.setFileUrl(videoUrl);
            countChange++;
            courseNote += "change video for lesson in chapter "
                    + lessonService.getLessonById(lessonId).getChapter().getChapterOrder() + ".";
        }
        video.setLesson(lessonService.getLessonById(lessonId));

        if(countChange != 0){
            courseService.setPendingCourse(lessonService.getLessonById(lessonId).getChapter().getCourse().getId(),
                    courseNote);
        }
        videoRepository.save(video);
    }

    public boolean checkVideoUpdate(int videoId, String oldTitle, String oldVideoUrl){
        Video video = videoRepository.getVideoById(videoId);
        if(!video.getFileUrl().equals(oldVideoUrl)){
            return true;
        }
        if(!video.getTitle().equalsIgnoreCase(oldTitle)){
            return true;
        }
        return false;
    }

    public Video findByVideoId(Integer videoId) {
        return videoRepository.findByVideoId(videoId);
    }
}
