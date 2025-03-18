package com.project.learningz.service;

import com.project.learningz.entity.PDF;
import com.project.learningz.repository.PdfRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private GoogleDriveService googleDriveService;

    @Autowired
    private CourseService courseService;

    public PDF getPdfById(int id) {
        return pdfRepository.getPdfById(id);
    }

    public List<PDF> findListByLessonId(int lessonId) {
        return pdfRepository.findListByLessonId(lessonId);
    }

    public List<PDF> findDocs(int lessonId, String keyword) {
        return pdfRepository.findDocs(lessonId, keyword);
    }

    public String docCheck(String title, MultipartFile file) {
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
    public void createDoc(int lessonId, String title, MultipartFile file) throws GeneralSecurityException, IOException {
        PDF pdf = new PDF();
        pdf.setTitle(title);
        pdf.setLesson(lessonService.getLessonById(lessonId));
        String fileUrl = googleDriveService.uploadFileDocument(file,
                lessonService.getLessonById(lessonId).getDocumentFolderLink());
        pdf.setFileUrl(fileUrl);

        courseService.setPendingCourse(lessonService.getLessonById(lessonId).getChapter().getCourse().getId(),
                "add new document for lesson in chapter " +
                        lessonService.getLessonById(lessonId).getChapter().getChapterOrder() + ".");

        pdfRepository.save(pdf);
    }

    @Transactional
    public void updateDoc(int lessonId ,int docId, String title, MultipartFile file) throws GeneralSecurityException, IOException {
        int countChange = 0;
        String courseNote = "";

        PDF pdf = pdfRepository.getPdfById(docId);

        if(!pdf.getTitle().equalsIgnoreCase(title)){
            countChange++;
            courseNote += "change title for document in lesson of chapter " +
                    lessonService.getLessonById(lessonId).getChapter().getChapterOrder() + " to " + title  + ".";
        }
        pdf.setTitle(title);
        pdf.setLesson(lessonService.getLessonById(lessonId));
        String docUrl;
        if(!file.isEmpty()){
            docUrl = googleDriveService.uploadFileDocument(file,
                    lessonService.getLessonById(lessonId).getDocumentFolderLink());
            String oldDocUrl = pdf.getFileUrl();
            String[] oldDocUrlString = oldDocUrl.split("/");
            googleDriveService.deleteFile(oldDocUrlString[5]);
            pdf.setFileUrl(docUrl);
            countChange++;
            courseNote += "change document for lesson in chapter " +
                    lessonService.getLessonById(lessonId).getChapter().getChapterOrder() + ".";
        }

        if(countChange != 0){
            courseService.setPendingCourse(lessonService.getLessonById(lessonId).getChapter().getCourse().getId(),
                    courseNote);
        }
        pdfRepository.save(pdf);
    }

    public boolean checkDocumentUpdate(int docId, String oldTitle, String oldDocumentUrl){
        PDF pdf = pdfRepository.getPdfById(docId);
        if(!pdf.getTitle().equalsIgnoreCase(oldTitle)){
            return true;
        }
        if(!pdf.getFileUrl().equalsIgnoreCase(oldDocumentUrl)){
            return true;
        }
        return false;
    }
}
