package com.project.learningz.service;

import com.project.learningz.constant.CourseStatus;
import com.project.learningz.dto.CourseDetailsDTO;
import com.project.learningz.dto.TopCourseDTO;
import com.project.learningz.entity.Course;
import com.project.learningz.repository.CourseRepository;
import com.project.learningz.specification.CourseSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final GradeService gradeService;
    private final UserService userService;
    private final GoogleDriveService googleDriveService;
    private final SubjectService subjectService;

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

    public List<CourseDetailsDTO> findCourses(int userId, int subjectId, String courseSearchKey) {
        if(subjectId != 0){
            if(courseSearchKey != null && !courseSearchKey.isEmpty()){
                return courseRepository.findCourses(userId, subjectId,courseSearchKey);
            }else {
                return courseRepository.findCourses(userId, subjectId, "");
            }
        }else{
            if(courseSearchKey != null && !courseSearchKey.isEmpty()){
                return courseRepository.findCourses(userId, courseSearchKey);
            }else{
                return courseRepository.findCourses(userId, "");
            }
        }
    }

    public Course findByCourseId(int courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    @Transactional
    public void updateCourse(int courseId, int createdByUseID, String courseDriveLink, String title, int subjectId, int gradeId, CourseStatus courseStatus, MultipartFile courseImageUrl, String description) throws GeneralSecurityException, IOException {
        Course course = courseRepository.findById(courseId).orElse(null);
        course.setTitle(title);
        course.setSubject(subjectService.getSubjectById(subjectId));
        course.setCourseStatus(courseStatus);

        if(courseDriveLink != null && !courseDriveLink.isEmpty()){
            googleDriveService.renameFolder(courseDriveLink, title);
        }

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
    public void createCourse(int createdByID, int gradeId, int subjectId, String title, CourseStatus courseStatus ,String description, MultipartFile courseImageUrl) throws GeneralSecurityException, IOException {
        Course newCourse = new Course();
        newCourse.setCreatedBy(userService.findById(createdByID));
        newCourse.setTitle(title);
        newCourse.setSubject(subjectService.getSubjectById(subjectId));
        newCourse.setCourseStatus(courseStatus);
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

    public List<String> getAllCourseInQuestions() {
        return courseRepository.getAllCourse();
    }

    public List<Course> searchCourseByStatusAndKeyword(CourseStatus status, String keyword, String sortField, String sortOrder) {
        Sort sort;
        if (sortOrder.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortField).descending();
        } else {
            sort = Sort.by(sortField).ascending();
        }
        return courseRepository.searchCourseByStatusAndKeyword(status, keyword, sort);
    }

    public List<Course> getAllCoursesByKeyword(String keyword, String sortField, String sortOrder) {
        Sort sort;
        if (sortOrder.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortField).descending();
        } else {
            sort = Sort.by(sortField).ascending();
        }
        return courseRepository.getAllCoursesByKeyword(keyword, sort);
    }

    public void approveCourse(Integer courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course Not Found"));
        course.setCourseStatus(CourseStatus.ACTIVE);
        courseRepository.save(course);
    }

    public void rejectCourse(Integer courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course Not Found"));
        course.setCourseStatus(CourseStatus.INACTIVE);
        courseRepository.save(course);
    }

    public Integer sumOfCourseByStatus(CourseStatus courseStatus) {
        if(courseStatus == CourseStatus.ACTIVE){
            return courseRepository.getAllCoursesByStatus(CourseStatus.ACTIVE).size();
        } else if(courseStatus == CourseStatus.INACTIVE){
            return courseRepository.getAllCoursesByStatus(CourseStatus.INACTIVE).size();
        } else if (courseStatus == CourseStatus.PENDING){
            return courseRepository.getAllCoursesByStatus(CourseStatus.PENDING).size();
        } else {
            return courseRepository.getAllCourse().size();
        }
    }

    public List<TopCourseDTO> getTop5Courses() {
        Pageable pageable = PageRequest.of(0, 5); // Lấy top 5 khóa học
        List<Object[]> results = courseRepository.getTop5PopularCoursesWithEnrollments(pageable);

        List<TopCourseDTO> topCourses = new ArrayList<>();
        for (Object[] row : results) {
            Course course = (Course) row[0];   // Lấy đối tượng Course
            Long enrollmentCount = (Long) row[1];  // Lấy số lượng đăng ký

            // Đưa vào DTO để trả về
            topCourses.add(new TopCourseDTO(course, enrollmentCount));
        }

        return topCourses;
    }
}
