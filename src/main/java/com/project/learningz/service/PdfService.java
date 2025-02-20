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
        if(!errorList.isEmpty()){
            for(String s : errorList){
                error += s + "\n";
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
        pdfRepository.save(pdf);
    }

    @Transactional
    public void updateDoc(int lessonId ,int docId, String title, MultipartFile file) throws GeneralSecurityException, IOException {
        PDF pdf = pdfRepository.getPdfById(docId);
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
        }
        pdfRepository.save(pdf);
    }
}
