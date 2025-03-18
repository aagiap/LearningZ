package com.project.learningz.service;

import com.project.learningz.constant.CourseStatus;
import com.project.learningz.dto.CourseDetailsDTO;
import com.project.learningz.dto.TopCourseDTO;
import com.project.learningz.dto.CourseLearnedStatsDTO;
import com.project.learningz.dto.CourseStatsDTO;
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

    public List<CourseDetailsDTO> findCourses(int userId, int subjectId, int gradeId, String courseSearchKey) {
        if(subjectId != 0){
            if(gradeId == 0){
                if(courseSearchKey != null && !courseSearchKey.isEmpty()){
                    return courseRepository.findCourses(userId, subjectId,courseSearchKey);
                }else {
                    return courseRepository.findCourses(userId, subjectId, "");
                }
            }else{
                if(courseSearchKey != null && !courseSearchKey.isEmpty()){
                    return courseRepository.findCourses(userId, subjectId, gradeId,courseSearchKey);
                }else {
                    return courseRepository.findCourses(userId, subjectId, gradeId,"");
                }
            }
        }else if(gradeId == 0){
            if(courseSearchKey != null && !courseSearchKey.isEmpty()){
                return courseRepository.findCourses(userId, courseSearchKey);
            }else{
                return courseRepository.findCourses(userId, "");
            }
        }else{
            if(courseSearchKey != null && !courseSearchKey.isEmpty()){
                return courseRepository.findCoursesWithGrade(userId, gradeId, courseSearchKey);
            }else{
                return courseRepository.findCoursesWithGrade(userId, gradeId,"");
            }
        }
    }

    public Course findByCourseId(int courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    @Transactional
    public void updateCourse(int courseId, int createdByUseID, String courseDriveLink, String title, int subjectId, int gradeId, CourseStatus courseStatus, MultipartFile courseImageUrl, String description) throws GeneralSecurityException, IOException {
        //add note to course when sth change
        String courseNote = "";
        int countChange = 0;
        Course course = courseRepository.findById(courseId).orElse(null);

        //if course has just been created, add old note of create new course in course note
        if(course.getNote() != null){
            if(course.getNote().contains("Create new")){
                courseNote += course.getNote();
            }
        }

        if(!title.equalsIgnoreCase(course.getTitle())){
            countChange++;
            courseNote += "set new title to '" + title + " '.";
        }
        course.setTitle(title);
        course.setSubject(subjectService.getSubjectById(subjectId));

        if(courseDriveLink != null && !courseDriveLink.isEmpty()){
            googleDriveService.renameFolder(courseDriveLink, title);
        }

        if(!description.equalsIgnoreCase(course.getDescription())){
            countChange++;
            if(course.getDescription().trim().isEmpty()){
                courseNote += "add description: " + description + ".";
            }else{
                courseNote += "set new description to '" + description + " '.";
            }
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
            countChange++;
            courseNote += "change course image.";
            String newCourseImage = googleDriveService.uploadFileCourseImage(courseImageUrl);
            course.setCourseImageUrl(newCourseImage);
        }

        //change course status to pending and set note to admin to check change if sth change
        if(countChange != 0){
            course.setNote(courseNote);
            course.setCourseStatus(CourseStatus.PENDING);
        }
        courseRepository.save(course);
    }

    public List<Course> findCoursesByGradeId(int gradeId) {
        return courseRepository.findCoursesByGradeId(gradeId);
    }

    public boolean checkUpdateChange(int courseId,String title, String description, String courseImageUrl,
                                     String oldTitle, String oldDescription, String oldCourseImageUrl){
        if(!title.equalsIgnoreCase(oldTitle)){
            return true;
        }
        if(!courseImageUrl.equalsIgnoreCase(oldCourseImageUrl)){
            return true;
        }
        if(!description.equalsIgnoreCase(oldDescription)){
            return true;
        }
        return false;
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
        newCourse.setNote("Create new " + subjectService.getSubjectById(subjectId).getName() +
                " course named: " + title + " of " + gradeService.findById(gradeId).getName() + ".");
        courseRepository.save(newCourse);
    }

    public List<String> getAllCourseInQuestions() {
        return courseRepository.getAllCourse();
    }

    @Transactional
    public void setPendingCourse(int courseId, String note){
        Course course = courseRepository.findById(courseId).orElse(null);
        course.setCourseStatus(CourseStatus.PENDING);
        if(course.getNote() != null){
            course.setNote(course.getNote() + note);
        }else{
            course.setNote(note);
        }
        courseRepository.save(course);
    }

    public List<Course> pendingCourseListByUserId(int userId){
        return courseRepository.pendingCourseListByUserId(userId, CourseStatus.PENDING);
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
        course.setCourseStatus(CourseStatus.REJECTED);
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

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public int getTotalCoursesByUserId(int userId) {
        return courseRepository.getTotalCoursesByUserId(userId);
    }

    public int getTotalVideosByUserId(int userId) {
        return courseRepository.getTotalVideosByUserId(userId);
    }

    public int getTotalDocsByUserId(int userId) {
        return courseRepository.getTotalDocsByUserId(userId);
    }

    public int getTotalStudentsByUserId(int userId) {
        return courseRepository.getTotalStudentsByUserId(userId);
    }

    public int getTotalCourseWithStatusByUserId(int userId, CourseStatus status) {
        return courseRepository.getTotalCourseWithStatusByUserId(userId, status);
    }

    public List<Course> getTop3CoursesListByUserId(int userId){
        return courseRepository.getTop3CoursesListByUserId(userId);
    }

    public List<CourseStatsDTO> getCourseAndScoreByUserId(int userId){
        return courseRepository.getCourseAndScoreByUserId(userId);
    }

    public List<CourseLearnedStatsDTO> getCourseLearnedStatsByUserId(int userId){
        return courseRepository.getCourseLearnedStatsByUserId(userId);
    }

    public Integer numberOfChapter(Integer courseId){
        return courseRepository.numberOfChapter(courseId);
    }
    public Integer numberOfVideos(Integer courseId){
        return courseRepository.numberOfVideos(courseId);
    }
    public Integer numberOfPDFs(Integer courseId){
        return courseRepository.numberOfPDFs(courseId);
    }
}
