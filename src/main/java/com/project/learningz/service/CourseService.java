package com.project.learningz.service;

import com.project.learningz.dto.CourseDetailsDTO;
import com.project.learningz.entity.Course;
import com.project.learningz.repository.CourseRepository;
import com.project.learningz.repository.UserRepository;
import com.project.learningz.specification.CourseSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final GradeService gradeService;
    private final UserService userService;
    private final GoogleDriveService googleDriveService;

    public Page<Course> getCoursesPagingByKeyword(String keyword, Pageable pageable) {
        Specification<Course> spec = CourseSpecification.getAllSpec();
        if (StringUtils.isNotEmpty(keyword)) {
            spec = spec.and(CourseSpecification.byKeywordSpec(keyword));
        }
        return courseRepository.findAll(spec, pageable);
    }

    public Page<Course> getCoursesPaging(int gradeId, int subjectId, String keyword, Pageable pageable) {
        Specification<Course> spec = CourseSpecification.getAllSpec();
        if (gradeId > 0) {
            spec = spec.and(CourseSpecification.byGradeId(gradeId));
        }
        if(subjectId > 0) {
            spec = spec.and(CourseSpecification.bySubjectId(subjectId));
        }
        if (StringUtils.isNotEmpty(keyword)) {
            spec = spec.and(CourseSpecification.byKeywordSpec(keyword));
        }
        return courseRepository.findAll(spec, pageable);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<String> findDistinctSubject() {
        return courseRepository.findDistinctSubject();
    }

    public Course getCourseById(int id) {
        return courseRepository.findById(id).orElse(null);
    }

    public int getLessonCountByCourseId(int courseId) {
        return courseRepository.countLessonByCourseId(courseId);
    }

    public List<CourseDetailsDTO> allCoursesByUserID(int userId) {
        return courseRepository.allCoursesByUserID(userId);
    }

    public List<CourseDetailsDTO> findCourses(int userId, String subject, String courseSearchKey) {
        return courseRepository.findCourses(userId, subject, courseSearchKey);
    }

    public Course findByCourseId(int courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    @Transactional
    public void updateCourse(int courseId, int createdByUseID, String courseDriveLink, String title, String subject, int gradeId, MultipartFile courseImageUrl,String description) throws GeneralSecurityException, IOException {
        Course course = courseRepository.findById(courseId).orElse(null);
        course.setTitle(title);
        //course.setSubject(subject);
        course.setGrade(gradeService.findById(gradeId));
        course.setDescription(description);
        if(courseImageUrl != null && !courseImageUrl.isEmpty()) {
            if(course.getCourseImageUrl() != null){
                if(course.getCourseImageUrl().contains("https://lh3.googleusercontent.com/d/")){
                    String[] oldCourseImageSplit = course.getCourseImageUrl().split("https://lh3.googleusercontent.com/d/");
                    googleDriveService.deleteFile(oldCourseImageSplit[1]);
                }
            }
            String newCourseImage = googleDriveService.uploadFileCourseImage(courseImageUrl);
            course.setCourseImageUrl(newCourseImage);
        }
        courseRepository.save(course);
    }

    public List<Course> findCoursesByGradeId(int gradeId) {
        return courseRepository.findCoursesByGradeId(gradeId);
    }

    @Transactional
    public void createCourse(int createdByID, int gradeId, String subject, String title, String description, MultipartFile courseImageUrl) throws GeneralSecurityException, IOException {
        Course newCourse = new Course();
        newCourse.setCreatedBy(userService.findById(createdByID));
        newCourse.setTitle(title);
        //newCourse.setSubject(subject);
        newCourse.setGrade(gradeService.findById(gradeId));
        newCourse.setDescription(description);
        String courseDriveLink = googleDriveService.createFolder(title,googleDriveService.getCoursesFolderId());
        newCourse.setCourseDriveLink(courseDriveLink);
        if(courseImageUrl != null && !courseImageUrl.isEmpty()) {
            String imageUrl = googleDriveService.uploadFileCourseImage(courseImageUrl);
            newCourse.setCourseImageUrl(imageUrl);
        }
        courseRepository.save(newCourse);
    }
}
