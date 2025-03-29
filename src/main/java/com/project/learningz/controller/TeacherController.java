package com.project.learningz.controller;

import com.project.learningz.constant.CourseStatus;
import com.project.learningz.constant.QuizType;
import com.project.learningz.dto.ChapterDetailDTO;
import com.project.learningz.dto.CourseDetailsDTO;
import com.project.learningz.dto.LessonDetailDTO;
import com.project.learningz.entity.*;
import com.project.learningz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TeacherController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private UserService userService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private SystemSettingService systemSettingService;

    @GetMapping(path = "/teacher")
    public String courseList(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        String username;
        if (principal instanceof OAuth2User) {
            OAuth2User oauthUser = (OAuth2User) principal;
            email = oauthUser.getAttribute("email");
            User user = userService.findByEmail(email);
            if (user != null) {
                username = user.getUsername();
            } else {
                username = oauthUser.getAttribute("name");
            }
        } else {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        User user = userService.findByUsername(username);
        List<CourseDetailsDTO> courseList = new ArrayList<>();
        courseList = courseService.allCoursesByUserID(user.getId());
        if(courseList.isEmpty()){
            model.addAttribute("user",user);
            model.addAttribute("notification","Empty List");
        }else{
            model.addAttribute("user",user);
            model.addAttribute("courseList",courseList);
        }

        List<Subject> subjectList = new ArrayList<>();
        subjectList = subjectService.getAllSubjects();
        model.addAttribute("subjectList", subjectList);

        List<CourseStatus> statusList = new ArrayList<>();
        statusList.add(CourseStatus.ACTIVE);statusList.add(CourseStatus.INACTIVE);
        statusList.add(CourseStatus.REJECTED);statusList.add(CourseStatus.PENDING);
        model.addAttribute("statusList", statusList);

        List<Grade> gradeList = gradeService.getAllGrades();
        model.addAttribute("gradeList", gradeList);
        return "/teacherPage/courseListTeacher";
    }

    @GetMapping(path = "/teacher/search")
    public String courseListSearch(Model model,
                                   @RequestParam("courseSearchKey") String courseSearchKey,
                                   @RequestParam("subjectId") int subjectId,
                                   @RequestParam("gradeId") int gradeId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = new User();
        String email;
        String username;
        if (principal instanceof OAuth2User) {
            OAuth2User oauthUser = (OAuth2User) principal;
            email = oauthUser.getAttribute("email");
            user = userService.findByEmail(email);
            if (user != null) {
                username = user.getUsername();
            } else {
                username = oauthUser.getAttribute("name");
            }
        } else {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
            user = userService.findByUsername(username);
        }
        List<CourseDetailsDTO> courseList = new ArrayList<>();
        courseList = courseService.findCourses(user.getId(), subjectId, gradeId,courseSearchKey);
        if(courseList.isEmpty()){
            model.addAttribute("user",user);
            model.addAttribute("notification","Empty List");
        }else{
            model.addAttribute("user",user);
            model.addAttribute("courseList",courseList);
        }

        List<CourseStatus> statusList = new ArrayList<>();
        statusList.add(CourseStatus.ACTIVE);statusList.add(CourseStatus.INACTIVE);
        statusList.add(CourseStatus.REJECTED);statusList.add(CourseStatus.PENDING);
        model.addAttribute("statusList", statusList);

        List<Subject> subjectList = new ArrayList<>();
        subjectList = subjectService.getAllSubjects();
        model.addAttribute("subjectList", subjectList);

        List<Grade> gradeList = gradeService.getAllGrades();
        model.addAttribute("gradeList", gradeList);
        return "/teacherPage/courseListTeacher";
    }

    @PostMapping(path = "/teacher/edit")
    public String editCourse(Model model,
                             @RequestParam("numberOfLessons") int numberOfLessons,
                             @RequestParam("courseId") int courseId){
        Course course = courseService.findByCourseId(courseId);

        List<CourseStatus> statusList = new ArrayList<>();
        statusList.add(CourseStatus.ACTIVE);statusList.add(CourseStatus.INACTIVE);
        statusList.add(CourseStatus.REJECTED);statusList.add(CourseStatus.PENDING);

        List<Subject> subjectList = new ArrayList<>();
        subjectList = subjectService.getAllSubjects();

        model.addAttribute("numberOfLessons", numberOfLessons);
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("user", userService.getUserById(course.getCreatedBy().getId()));
        model.addAttribute("course",course);
        model.addAttribute("statusList", statusList);

        return "/teacherPage/editCourse";
    }

    @PostMapping(path = "/teacher/edit/update")
    public String updateCourse(Model model,
                               @RequestParam("id") int id,
                               @RequestParam("createdByUserId") int createdByUseID,
                               @RequestParam("courseDriveLink") String courseDriveLink,
                               @RequestParam("title") String title,
                               @RequestParam("subjectId") int subjectId,
                               @RequestParam("gradeId") int gradeId,
                               @RequestParam("status") CourseStatus courseStatus,
                               @RequestParam("courseImageUrl") MultipartFile courseImageUrl,
                               @RequestParam("description") String description){
        List<CourseStatus> statusList = new ArrayList<>();
        statusList.add(CourseStatus.ACTIVE);statusList.add(CourseStatus.INACTIVE);
        statusList.add(CourseStatus.REJECTED);statusList.add(CourseStatus.PENDING);

        Course course = courseService.getCourseById(id);
        String oldTitle = course.getTitle();
        String oldDescription = course.getDescription();
        String oldCourseImageUrl = course.getCourseImageUrl();

        List<Subject> subjectList = new ArrayList<>();
        subjectList = subjectService.getAllSubjects();
        if(title.isEmpty() || title.trim().isEmpty()){
            model.addAttribute("error","Please fill title");
            model.addAttribute("course",courseService.findByCourseId(id));
        }else if(courseService.checkExistCourseTitleWhenEdit(title, id)){
            model.addAttribute("error","Title Already Exists");
            model.addAttribute("course",courseService.findByCourseId(id));
        }else{
            try{
                courseService.updateCourse(id, createdByUseID, courseDriveLink, title,
                        subjectId, gradeId, courseStatus, courseImageUrl, description);
            }catch(Exception e){
                e.printStackTrace();
                model.addAttribute("error","Course Update Failed");
                model.addAttribute("subjectList", subjectList);
                model.addAttribute("user",userService.getUserById(createdByUseID));
                model.addAttribute("statusList", statusList);
                model.addAttribute("course",courseService.findByCourseId(id));
                return "/teacherPage/editCourse";
            }
            model.addAttribute("course",courseService.findByCourseId(id));
            model.addAttribute("notification","Update change successfully");
        }
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("statusList", statusList);
        model.addAttribute("user",userService.getUserById(createdByUseID));
        return "/teacherPage/editCourse";
    }

    @GetMapping(path = "/teacher/add_course")
    public String addCourse(Model model,
                            @RequestParam("userId") int userId){
        User user = userService.findById(userId);
        List<CourseStatus> statusList = new ArrayList<>();
        statusList.add(CourseStatus.ACTIVE);statusList.add(CourseStatus.INACTIVE);
        statusList.add(CourseStatus.REJECTED);statusList.add(CourseStatus.PENDING);
        List<Subject> subjectList = new ArrayList<>();
        subjectList = subjectService.getAllSubjects();

        model.addAttribute("subjectList", subjectList);
        model.addAttribute("statusList", statusList);
        model.addAttribute("user", user);
        model.addAttribute("createdById",user.getId());
        return "/teacherPage/addCourse";
    }

    @PostMapping(path = "/teacher/add_course/create")
    public String confirmAddCourse(Model model,
                                   @RequestParam("createdById") int createdById,
                                   @RequestParam("title") String title,
                                   @RequestParam("subjectId") int subjectId,
                                   @RequestParam("gradeId") int gradeId,
                                   @RequestParam("status") CourseStatus courseStatus,
                                   @RequestParam("courseImageUrl") MultipartFile courseImageUrl,
                                   @RequestParam("description") String description){
        User user = userService.findById(createdById);
        List<CourseStatus> statusList = new ArrayList<>();
        statusList.add(CourseStatus.ACTIVE);statusList.add(CourseStatus.INACTIVE);
        statusList.add(CourseStatus.REJECTED);statusList.add(CourseStatus.PENDING);
        List<Subject> subjectList = new ArrayList<>();
        subjectList = subjectService.getAllSubjects();

        if(title.isEmpty() || title.trim().isEmpty()){
            model.addAttribute("error","Please fill title");
        }else if(courseService.checkExistCourseTitleWhenAdd(title)){
            model.addAttribute("error","Title Already Exists");
        }else{
            try{
                courseService.createCourse(createdById, gradeId, subjectId, title, courseStatus, description, courseImageUrl);
            }catch(Exception e){
                e.printStackTrace();
                model.addAttribute("subjectList", subjectList);
                model.addAttribute("statusList", statusList);
                model.addAttribute("user", user);
                model.addAttribute("createdById",user.getId());
                model.addAttribute("error","Course Creation Failed");
                return "/teacherPage/addCourse";
            }
            model.addAttribute("notification","Course Creation Successfully");
        }
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("statusList", statusList);
        model.addAttribute("user", user);
        model.addAttribute("createdById",user.getId());
        return "/teacherPage/addCourse";
    }

    @GetMapping(path = "/teacher/chapter")
    public String chapterList(Model model,
                              @RequestParam("userId") int userId,
                              @RequestParam("courseId") int courseId){

        List<CourseDetailsDTO> courseList = courseService.findCourses(userId, 0, 0,
                courseService.findByCourseId(courseId).getTitle());

        List<ChapterDetailDTO> chapterList = new ArrayList<>();
        chapterList = chapterService.allChaptersByCourseId(courseId);
        if(chapterList.isEmpty()){
            model.addAttribute("course", courseService.findByCourseId(courseId));
            model.addAttribute("courseId", courseId);
            model.addAttribute("notification","Empty List");
        }else {
            model.addAttribute("course", courseService.findByCourseId(courseId));
            model.addAttribute("courseId", courseId);
            model.addAttribute("chapterList",chapterList);
        }
        List<CourseStatus> statusList = new ArrayList<>();
        statusList.add(CourseStatus.ACTIVE);statusList.add(CourseStatus.INACTIVE);
        statusList.add(CourseStatus.REJECTED);statusList.add(CourseStatus.PENDING);
        model.addAttribute("statusList", statusList);
        model.addAttribute("user",userService.getUserById(userId));
        model.addAttribute("system_setting", systemSettingService.getAllSystemSetting());
        model.addAttribute("numberOfChapters", courseList.get(0).getNumberOfChapters());
        return "/teacherPage/chapterList";
    }

    @GetMapping(path = "/teacher/chapter/search")
    public String chapterListSearch(Model model,
                                    int courseId,
                                    String chapterSearchKey,
                                    @RequestParam("userId") int userId) {

        List<CourseDetailsDTO> courseList = courseService.findCourses(userId, 0, 0,
                courseService.findByCourseId(courseId).getTitle());

        List<ChapterDetailDTO> chapterList = new ArrayList<>();
        if (chapterSearchKey != null) {
            chapterList = chapterService.findChapters(courseId,chapterSearchKey);
        }else{
            chapterList = chapterService.findChapters(courseId,"");
        }
        if(chapterList.isEmpty()){
            model.addAttribute("courseId", courseId);
            model.addAttribute("notification","Empty List");
        }else{
            model.addAttribute("courseId", courseId);
            model.addAttribute("chapterList",chapterList);
        }
        List<CourseStatus> statusList = new ArrayList<>();
        statusList.add(CourseStatus.ACTIVE);statusList.add(CourseStatus.INACTIVE);
        statusList.add(CourseStatus.REJECTED);statusList.add(CourseStatus.PENDING);
        model.addAttribute("statusList", statusList);
        model.addAttribute("course", courseService.findByCourseId(courseId));
        model.addAttribute("user",userService.getUserById(userId));
        model.addAttribute("system_setting", systemSettingService.getAllSystemSetting());
        model.addAttribute("numberOfChapters", courseList.get(0).getNumberOfChapters());
        model.addAttribute("numberOfLessons", courseList.get(0).getNumberOfLessons());
        return "/teacherPage/chapterList";
    }

    @PostMapping(path = "/teacher/chapter/edit")
    public String editChapter(Model model,
                              int chapterId,
                              @RequestParam("userId") int userId){
        Chapter chapter = chapterService.getChapterById(chapterId);
        model.addAttribute("chapter",chapter);
        model.addAttribute("user",userService.getUserById(userId));
        return "teacherPage/editChapter";
    }

    @PostMapping(path = "/teacher/chapter/edit/update")
    public String updateChapter(Model model,
                                @RequestParam("id") int id,
                                @RequestParam("courseId") int courseId,
                                @RequestParam("chapterDriveLink") String chapterDriveLink,
                                @RequestParam("order") int order,
                                @RequestParam("title") String title,
                                @RequestParam("description") String description,
                                @RequestParam("userId") int userId){
        String error = chapterService.checkUpdate(id,courseId,order,title);
        if(error.equals("")){
            try{
                chapterService.updateChapter(id,order,title,description, chapterDriveLink);
            }catch(Exception e){
                e.printStackTrace();
                model.addAttribute("error","Chapter Update Failed");
                model.addAttribute("user",userService.getUserById(userId));
                model.addAttribute("chapter", chapterService.getChapterById(id));
                return "/teacherPage/editChapter";
            }
            model.addAttribute("notification","Update changes successfully");
        }else{
            model.addAttribute("error",error);
        }
        model.addAttribute("chapter", chapterService.getChapterById(id));
        model.addAttribute("user",userService.getUserById(userId));
        return "/teacherPage/editChapter";
    }

    @GetMapping(path = "/teacher/chapter/addChapter")
    public String addChapter(Model model,
                             @RequestParam("courseId") int courseId,
                             @RequestParam("userId") int userId){
        model.addAttribute("courseId", courseId);
        model.addAttribute("user",userService.getUserById(userId));
        return "/teacherPage/addChapter";
    }

    @PostMapping(path = "/teacher/chapter/addChapter/create")
    public String confirmAddChapter(Model model,
                                    @RequestParam("courseId") int courseId,
                                    @RequestParam("order") int order,
                                    @RequestParam("title") String title,
                                    @RequestParam("description") String description,
                                    @RequestParam("userId") int userId){

        List<CourseDetailsDTO> courseList = courseService.findCourses(userId, 0, 0,
                courseService.findByCourseId(courseId).getTitle());

        String error = chapterService.checkCreate(courseId,order,title);
        if(error.equals("") && courseList.get(0).getNumberOfChapters() < systemSettingService.getAllSystemSetting().get(2).getSettingValue()){
            try{
                chapterService.createChapter(courseId, order, title, description);
            }catch(Exception e){
                e.printStackTrace();
                model.addAttribute("error","Chapter Creation Failed");
                model.addAttribute("courseId", courseId);
                model.addAttribute("user",userService.getUserById(userId));
                model.addAttribute("system_setting", systemSettingService.getAllSystemSetting());
                return "/teacherPage/addChapter";
            }
            model.addAttribute("notification","Create chapter successfully");
        }else if(courseList.get(0).getNumberOfChapters() >= systemSettingService.getAllSystemSetting().get(2).getSettingValue()){
            model.addAttribute("error","The number of chapters has reached the limit (" + systemSettingService.getAllSystemSetting().get(2).getSettingValue() + ")");
        }
        else{
            model.addAttribute("error",error);
        }
        model.addAttribute("courseId", courseId);
        model.addAttribute("user",userService.getUserById(userId));
        return "/teacherPage/addChapter";
    }

    @GetMapping(path = "/teacher/chapter/lesson")
    public String lessonList(Model model,
                             @RequestParam("chapterId") int chapterId,
                             @RequestParam("userId") int userId){

        List<LessonDetailDTO> lessonListOfChapter = lessonService.allLessonsByChapterId(chapterId);
        
        List<LessonDetailDTO> lessonList = new ArrayList<>();
        lessonList = lessonService.allLessonsByChapterId(chapterId);
        if(lessonList.isEmpty()){
            model.addAttribute("courseId", chapterService.getChapterById(chapterId).getCourse().getId());
            model.addAttribute("chapterId", chapterId);
            model.addAttribute("notification","Empty List");
        }else{
            model.addAttribute("courseId", chapterService.getChapterById(chapterId).getCourse().getId());
            model.addAttribute("chapterId", chapterId);
            model.addAttribute("lessonList",lessonList);
        }
        model.addAttribute("course", courseService.findByCourseId(chapterService.getChapterById(chapterId).getCourse().getId()));
        List<CourseStatus> statusList = new ArrayList<>();
        statusList.add(CourseStatus.ACTIVE);statusList.add(CourseStatus.INACTIVE);
        statusList.add(CourseStatus.REJECTED);statusList.add(CourseStatus.PENDING);
        model.addAttribute("statusList", statusList);
        model.addAttribute("user",userService.getUserById(userId));
        model.addAttribute("system_setting", systemSettingService.getAllSystemSetting());
        model.addAttribute("numberOfLessons", lessonListOfChapter.size());
        return "/teacherPage/lessonList";
    }

    @GetMapping(path = "/teacher/chapter/lesson/search")
    public String lessonListSearch(Model model,
                                   @RequestParam("chapterId") int chapterId,
                                   @RequestParam("lessonSearchKey") String lessonSearchKey,
                                   @RequestParam("userId") int userId){

        List<LessonDetailDTO> lessonListOfChapter = lessonService.allLessonsByChapterId(chapterId);

        List<LessonDetailDTO> lessonList = new ArrayList<>();
        if(lessonSearchKey.trim().equals("")){
            lessonSearchKey = "";
        }
        lessonList = lessonService.findLessons(chapterId,lessonSearchKey);
        if(lessonList.isEmpty()){
            model.addAttribute("courseId", chapterService.getChapterById(chapterId).getCourse().getId());
            model.addAttribute("chapterId", chapterId);
            model.addAttribute("notification","Empty List");
        }else{
            model.addAttribute("courseId", chapterService.getChapterById(chapterId).getCourse().getId());
            model.addAttribute("chapterId", chapterId);
            model.addAttribute("lessonList",lessonList);
        }
        model.addAttribute("course", courseService.findByCourseId(chapterService.getChapterById(chapterId).getCourse().getId()));
        List<CourseStatus> statusList = new ArrayList<>();
        statusList.add(CourseStatus.ACTIVE);statusList.add(CourseStatus.INACTIVE);
        statusList.add(CourseStatus.REJECTED);statusList.add(CourseStatus.PENDING);
        model.addAttribute("statusList", statusList);
        model.addAttribute("user",userService.getUserById(userId));
        model.addAttribute("system_setting", systemSettingService.getAllSystemSetting());
        model.addAttribute("numberOfLessons", lessonListOfChapter.size());
        return "/teacherPage/lessonList";
    }

    @PostMapping(path = "/teacher/chapter/lesson/edit")
    public String editLesson(Model model,
                             @RequestParam("lessonId") int lessonId,
                             @RequestParam("userId") int userId){
        Lesson lesson = lessonService.getLessonById(lessonId);
        List<QuizType> typeList = new ArrayList<>();
        typeList.add(QuizType.PRACTICE);typeList.add(QuizType.EXAM);
        model.addAttribute("chapterId", lessonService.getLessonById(lessonId).getChapter().getId());
        model.addAttribute("quizTypeList",typeList);
        model.addAttribute("lesson",lesson);
        model.addAttribute("user",userService.getUserById(userId));
        return "teacherPage/editLesson";
    }

    @PostMapping(path = "/teacher/chapter/lesson/edit/update")
    public String updateLesson(Model model,
                               @RequestParam("lessonId") int lessonId,
                               @RequestParam("chapterId") int chapterId,
                               @RequestParam("lessonDriveLink") String lessonDriveLink,
                               @RequestParam("documentFolderLink") String documentFolderLink,
                               @RequestParam("videoFolderLink") String videoFolderLink,
                               @RequestParam("quizImageLink") String quizImageLink,
                               @RequestParam("lessonTitle") String lessonTitle,
                               @RequestParam("quizType") QuizType quizType,
                               @RequestParam("description") String description,
                               @RequestParam("userId") int userId){
        List<QuizType> typeList = new ArrayList<>();
        typeList.add(QuizType.PRACTICE);typeList.add(QuizType.EXAM);
        if(lessonTitle.trim().isEmpty()){
            model.addAttribute("error","Empty Title");
        }else{
            try {
                lessonService.updateLesson(lessonId,chapterId,lessonDriveLink,documentFolderLink,videoFolderLink,
                        quizImageLink,lessonTitle,quizType,description);
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("error","Chapter Update Failed");
                model.addAttribute("lesson",lessonService.getLessonById(lessonId));
                model.addAttribute("quizTypeList",quizType);
                model.addAttribute("chapterId",chapterId);
                model.addAttribute("user",userService.getUserById(userId));
                return "/teacherPage/editLesson";
            }
            model.addAttribute("notification","Update changes successfully");
        }
        model.addAttribute("lesson",lessonService.getLessonById(lessonId));
        model.addAttribute("quizTypeList",quizType);
        model.addAttribute("chapterId",chapterId);
        model.addAttribute("user",userService.getUserById(userId));
        return "teacherPage/editLesson";
    }

    @GetMapping(path = "/teacher/chapter/lesson/addLesson")
    public String addLesson(Model model,
                            @RequestParam("chapterId") int chapterId,
                            @RequestParam("userId") int userId){
        List<QuizType> typeList = new ArrayList<>();
        typeList.add(QuizType.PRACTICE);typeList.add(QuizType.EXAM);
        model.addAttribute("quizTypeList",typeList);
        model.addAttribute("chapterId", chapterId);
        model.addAttribute("user",userService.getUserById(userId));
        return "/teacherPage/addLesson";
    }

    @PostMapping(path = "/teacher/chapter/lesson/addLesson/create")
    public String confirmAddLesson(Model model,
                                   @RequestParam("chapterId") int chapterId,
                                   @RequestParam("lessonTitle") String lessonTitle,
                                   @RequestParam("quizType") QuizType quizType,
                                   @RequestParam("description") String description,
                                   @RequestParam("userId") int userId){

        List<LessonDetailDTO> lessonListOfChapter = lessonService.allLessonsByChapterId(chapterId);

        if(lessonTitle.trim().isEmpty()){
            model.addAttribute("error","Empty Title");
        } else if (lessonListOfChapter.size() >= systemSettingService.getAllSystemSetting().get(3).getSettingValue()) {
            model.addAttribute("error", "The number of lessons has reached the limit!" +
                    "(" + systemSettingService.getAllSystemSetting().get(3).getSettingValue() + ")");
        } else{
            try{
                lessonService.createLesson(chapterId,lessonTitle,quizType,description);
            }catch(Exception e){
                e.printStackTrace();
                model.addAttribute("chapterId",chapterId);
                model.addAttribute("error","Lesson Creation Failed");
            }
            model.addAttribute("notification","Create Lesson Successfully");
        }
        List<QuizType> typeList = new ArrayList<>();
        typeList.add(QuizType.PRACTICE);typeList.add(QuizType.EXAM);
        model.addAttribute("quizTypeList",typeList);
        model.addAttribute("user",userService.getUserById(userId));
        model.addAttribute("chapterId",chapterId);
        return "/teacherPage/addLesson";
    }

    @GetMapping(path = "/teacher/chapter/lesson/videos")
    public String videoList(Model model,
                            @RequestParam("lessonId") int lessonId,
                            @RequestParam("userId") int userId){
        List<Video> videoList = new ArrayList<>();
        videoList = videoService.getVideoListByLessonId(lessonId);
        if(videoList.isEmpty()){
            model.addAttribute("notification","Empty Video");
        }else{
            model.addAttribute("videoList",videoList);
        }
        model.addAttribute("chapterId",lessonService.getLessonById(lessonId).getChapter().getId());
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("user",userService.getUserById(userId));
        return "/teacherPage/videoList";
    }

    @GetMapping(path = "/teacher/chapter/lesson/videos/search")
    public String videoListSearch(Model model,
                                  @RequestParam("videoSearchKey") String keyWord,
                                  @RequestParam("lessonId") int lessonId,
                                  @RequestParam("userId") int userId){
        List<Video> videoList = new ArrayList<>();
        videoList = videoService.findVideo(lessonId, keyWord);
        if(videoList.isEmpty()){
            model.addAttribute("notification","Empty Video");
        }else{
            model.addAttribute("videoList",videoList);
        }
        model.addAttribute("chapterId",lessonService.getLessonById(lessonId).getChapter().getId());
        model.addAttribute("lessonId",lessonId);
        model.addAttribute("user",userService.getUserById(userId));
        return "/teacherPage/videoList";
    }

    @GetMapping(path = "/teacher/chapter/lesson/videos/addVideo")
    public String addVideo(Model model,
                           @RequestParam("lessonId") int lessonId,
                           @RequestParam("userId") int userId){
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("user",userService.getUserById(userId));
        return "/teacherPage/addVideo";
    }

    @PostMapping(path = "/teacher/chapter/lesson/videos/addVideo/create")
    public String confirmAddVideo(Model model,
                                  @RequestParam("lessonId") int lessonId,
                                  @RequestParam("videoTitle") String videoTitle,
                                  @RequestParam("videoUrl") MultipartFile videoUrl,
                                  @RequestParam("userId") int userId) {
        String error = videoService.videoCheck(videoTitle, videoUrl);
        if (error.isEmpty()) {
            try {
                videoService.createVideo(lessonId, videoTitle, videoUrl);
            } catch (Exception e) {
                model.addAttribute("lessonId", lessonId);
                model.addAttribute("error", "Video Creation Failed");
                model.addAttribute("user",userService.getUserById(userId));
                return "/teacherPage/addVideo";
            }
            model.addAttribute("notification", "Create video successfully");
        } else {
            model.addAttribute("error", error);
        }
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("user",userService.getUserById(userId));
        return "/teacherPage/addVideo";
    }

    @PostMapping(path = "/teacher/chapter/lesson/videos/edit")
    public String editVideo(Model model,
                            @RequestParam("videoId") int videoId,
                            @RequestParam("userId") int userId){
        Video video = videoService.getVideoById(videoId);
        model.addAttribute("video", video);
        model.addAttribute("user", userService.getUserById(userId));
        return "/teacherPage/editVideo";
    }

    @PostMapping(path = "/teacher/chapter/lesson/videos/edit/update")
    public String updateVideo(Model model,
                              @RequestParam("lessonId") int lessonId,
                              @RequestParam("videoId") int videoId,
                              @RequestParam("videoTitle") String videoTitle,
                              @RequestParam("videoUrl") MultipartFile videoUrl,
                              @RequestParam("userId") int userId) {
        if(videoTitle.trim().isEmpty()){
            model.addAttribute("error","Empty Title");
        }else{
            try{
                videoService.updateVideo(lessonId,videoId,videoTitle,videoUrl);
            }catch(Exception e){
                model.addAttribute("error","Video Update Failed");
                Video updatedVideo = videoService.getVideoById(videoId);
                model.addAttribute("video", updatedVideo);
                model.addAttribute("user", userService.getUserById(userId));
                return "/teacherPage/editVideo";
            }
            model.addAttribute("notification", "Update change successfully");
        }
        Video updatedVideo = videoService.getVideoById(videoId);
        model.addAttribute("video", updatedVideo);
        model.addAttribute("user", userService.getUserById(userId));
        return "/teacherPage/editVideo";
    }

    @GetMapping(path = "/teacher/chapter/lesson/docs")
    public String docList(Model model,
                          @RequestParam("lessonId") int lessonId,
                          @RequestParam("userId") int userId){
        List<PDF> pdfList = new ArrayList<>();
        pdfList = pdfService.findListByLessonId(lessonId);
        if(pdfList.isEmpty()){
            model.addAttribute("notification","Empty Document");
        }else{
            model.addAttribute("pdfList",pdfList);
        }
        model.addAttribute("lessonId",lessonId);
        model.addAttribute("chapterId",lessonService.getLessonById(lessonId).getChapter().getId());
        model.addAttribute("user",userService.getUserById(userId));
        return "/teacherPage/docList";
    }

    @GetMapping(path = "/teacher/chapter/lesson/docs/search")
    public String docListSearch(Model model,
                                @RequestParam("lessonId") int lessonId,
                                @RequestParam("docSearchKey") String docSearchKey,
                                @RequestParam("userId") int userId){
        List<PDF> pdfList = new ArrayList<>();
        pdfList = pdfService.findDocs(lessonId, docSearchKey);
        if(pdfList.isEmpty()){
            model.addAttribute("notification","Empty Document");
        }else{
            model.addAttribute("pdfList",pdfList);
        }
        model.addAttribute("lessonId",lessonId);
        model.addAttribute("chapterId",lessonService.getLessonById(lessonId).getChapter().getId());
        model.addAttribute("user", userService.getUserById(userId));
        return "/teacherPage/docList";
    }

    @GetMapping(path = "/teacher/chapter/lesson/docs/addDoc")
    public String addDoc(Model model,
                         @RequestParam("lessonId") int lessonId,
                         @RequestParam("userId") int userId){
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("user", userService.getUserById(userId));
        return "/teacherPage/addDoc";
    }

    @PostMapping(path = "/teacher/chapter/lesson/docs/addDoc/create")
    public String confirmAddDoc(Model model,
                                @RequestParam("lessonId") int lessonId,
                                @RequestParam("docTitle") String docTitle,
                                @RequestParam("docUrl") MultipartFile docUrl,
                                @RequestParam("userId") int userId) {
        String error = pdfService.docCheck(docTitle, docUrl);
        if(error.isEmpty()){
            try{
                pdfService.createDoc(lessonId,docTitle,docUrl);
            }catch(Exception e){
                model.addAttribute("lessonId",lessonId);
                model.addAttribute("error","Create document failed");
                model.addAttribute("user", userService.getUserById(userId));
                return "/teacherPage/addDoc";
            }
            model.addAttribute("notification","Create document successfully");
        }else{
            model.addAttribute("error",error);
        }
        model.addAttribute("lessonId",lessonId);
        model.addAttribute("user", userService.getUserById(userId));
        return "/teacherPage/addDoc";
    }

    @PostMapping(path = "/teacher/chapter/lesson/docs/edit")
    public String editDoc(Model model,
                          @RequestParam("pdfId") int pdfId,
                          @RequestParam("userId") int userId){
        PDF pdf = pdfService.getPdfById(pdfId);
        model.addAttribute("pdf", pdf);
        model.addAttribute("user", userService.getUserById(userId));
        return "/teacherPage/editDoc";
    }

    @PostMapping(path = "/teacher/chapter/lesson/docs/edit/update")
    public String updateDoc(Model model,
                            @RequestParam("lessonId") int lessonId,
                            @RequestParam("docId") int docId,
                            @RequestParam("docTitle") String docTitle,
                            @RequestParam("docUrl") MultipartFile docUrl,
                            @RequestParam("userId") int userId) {
        if(docTitle.trim().isEmpty()){
            model.addAttribute("error","Empty Title");
        }else{
            try{
                pdfService.updateDoc(lessonId,docId,docTitle,docUrl);
            }catch(Exception e){
                model.addAttribute("error","Document Update Failed");
                PDF pdf = pdfService.getPdfById(docId);
                model.addAttribute("pdf", pdf);
                model.addAttribute("user", userService.getUserById(userId));
                return "/teacherPage/editDoc";
            }
            model.addAttribute("notification","Update changes successfully");
        }
        PDF pdf = pdfService.getPdfById(docId);
        model.addAttribute("pdf", pdf);
        model.addAttribute("user", userService.getUserById(userId));
        return "/teacherPage/editDoc";
    }

    @GetMapping(path = "/teacher/subject")
    public String subjectList(Model model,
                              @RequestParam("userId") int userId){
        List<Subject> subjectList = new ArrayList<>();
        subjectList = subjectService.getAllSubjects();

        model.addAttribute("user", userService.getUserById(userId));
        if(subjectList.isEmpty()){
            model.addAttribute("notification","Empty Subject");
        }else{
            model.addAttribute("subjectList",subjectList);
        }
        return "/teacherPage/subjectList";
    }

    @GetMapping(path = "/teacher/subject/search")
    public String subjectSearch(Model model,
                                @RequestParam("userId") int userId,
                                @RequestParam("subjectSearchKey") String subjectSearchKey){
        List<Subject> subjectList = new ArrayList<>();
        subjectList = subjectService.getSubjectsByKey(subjectSearchKey);
        if(subjectList.isEmpty()){
            model.addAttribute("notification","Empty Subject");
        }else{
            model.addAttribute("subjectList",subjectList);
        }
        model.addAttribute("user", userService.getUserById(userId));
        return "/teacherPage/subjectList";
    }

    @PostMapping(path = "/teacher/subject/edit")
    public String editSubject(Model model,
                              @RequestParam("userId") int userId,
                              @RequestParam("subjectId") int subjectId){
        model.addAttribute("user", userService.getUserById(userId));
        model.addAttribute("subject", subjectService.getSubjectById(subjectId));
        return "/teacherPage/editSubject";
    }

    @PostMapping(path = "/teacher/subject/edit/update")
    public String updateSubject(Model model,
                                @RequestParam("userId") int userId,
                                @RequestParam("subjectId") int subjectId,
                                @RequestParam("subjectName") String subjectName,
                                @RequestParam("description") String description){
        if(subjectName.trim().isEmpty()){
            model.addAttribute("error","Please fill subject name");
        } else if (subjectService.checkSubjectNameExistsWhenEdit(subjectName, subjectId)) {
            model.addAttribute("error","Subject name already exists");
        } else{
            subjectService.updateSubject(subjectId, subjectName, description);
            model.addAttribute("notification","Subject Update Successfully");
        }
        model.addAttribute("subject", subjectService.getSubjectById(subjectId));
        model.addAttribute("user", userService.getUserById(userId));
        return "/teacherPage/editSubject";
    }

    @GetMapping(path = "/teacher/subject/addSubject")
    public String addSubject(Model model,
                             @RequestParam("userId") int userId){
        model.addAttribute("user", userService.getUserById(userId));
        return "/teacherPage/addSubject";
    }

    @PostMapping(path = "/teacher/subject/addSubject/create")
    public String confirmAddSubject(Model model,
                                    @RequestParam("userId") int userId,
                                    @RequestParam("subjectName") String subjectName,
                                    @RequestParam("description") String description){
        if(subjectName.trim().isEmpty()){
            model.addAttribute("error","Please fill subject name");
        } else if (subjectService.checkSubjectNameExistsWhenAdd(subjectName)) {
            model.addAttribute("error","Subject name already exists");
        } else{
            subjectService.createSubject(subjectName, description);
            model.addAttribute("notification","Subject Creation Successfully");
        }
        model.addAttribute("user", userService.getUserById(userId));
        return "/teacherPage/addSubject";
    }

    @GetMapping(path = "teacher/dashboard")
    public String teacherDashboard(Model model,
                                   @RequestParam("userId") int userId){
        model.addAttribute("user", userService.getUserById(userId));
        model.addAttribute("totalCourses", courseService.getTotalCoursesByUserId(userId));
        model.addAttribute("totalVideos", courseService.getTotalVideosByUserId(userId));
        model.addAttribute("totalDocs", courseService.getTotalDocsByUserId(userId));
        model.addAttribute("totalStudents", courseService.getTotalStudentsByUserId(userId));
        model.addAttribute("totalActiveCourses", courseService.getTotalCourseWithStatusByUserId(userId, CourseStatus.ACTIVE));
        model.addAttribute("totalInactiveCourses", courseService.getTotalCourseWithStatusByUserId(userId, CourseStatus.INACTIVE));
        model.addAttribute("totalPendingCourses", courseService.getTotalCourseWithStatusByUserId(userId, CourseStatus.PENDING));
        model.addAttribute("totalRejectedCourses", courseService.getTotalCourseWithStatusByUserId(userId, CourseStatus.REJECTED));
        model.addAttribute("top3Courses", courseService.getTop3CoursesListByUserId(userId));
        model.addAttribute("top3Student", userService.getTop3UserByTeacherId(userId));
        model.addAttribute("courseScoreList", courseService.getCourseAndScoreByUserId(userId));
        model.addAttribute("courseLearnedList", courseService.getCourseLearnedStatsByUserId(userId));
        return "teacherPage/Dashboard";
    }

    @GetMapping(path = "teacher/inactiveCourseList")
    public String inactiveCourseList(Model model,
                                    @RequestParam("userId") int userId){
        model.addAttribute("user", userService.getUserById(userId));
        List<Course> inactiveCourseList = new ArrayList<>();
        inactiveCourseList = courseService.inactiveCourseListByUserId(userId);
        List<Course> rejectedeCourseList = new ArrayList<>();
        rejectedeCourseList = courseService.rejectedCourseListByUserId(userId);
        model.addAttribute("rejectedCourseList", rejectedeCourseList);
        model.addAttribute("inactiveCourseList", inactiveCourseList);
        return "teacherPage/inactiveCourseList";
    }

    @GetMapping(path = "teacher/inactiveCourseList/editNote")
    public String editInactiveCourseNote(Model model,
                                         @RequestParam("userId") int userId,
                                         @RequestParam("courseId") int courseId){
        model.addAttribute("user", userService.getUserById(userId));
        model.addAttribute("course", courseService.getCourseById(courseId));
        return "/teacherPage/editInactiveCourseNote";
    }

    @PostMapping(path = "/teacher/inactiveCourseList/editNote/updateNote")
    public String updateInactiveCourseNote(Model model,
                                           @RequestParam("userId") int userId,
                                           @RequestParam("courseId") int courseId,
                                           @RequestParam("courseNote") String courseNote){

        if(courseNote.trim().isEmpty()){
            model.addAttribute("error","Please fill course note");
        }else{
            courseService.updateInactiveCourseNote(courseId, courseNote);
            model.addAttribute("notification","Update Course Note Successfully");
        }
        model.addAttribute("user", userService.getUserById(userId));
        model.addAttribute("course", courseService.getCourseById(courseId));
        return "/teacherPage/editInactiveCourseNote";
    }

    @PostMapping(path = "teacher/inactiveCourseList/upload")
    public String activateCourse(Model model,
                               @RequestParam("courseId") int courseId,
                               @RequestParam("userId") int userId,
                               @RequestParam("courseNote") String courseNote){
        if(courseNote == null || courseNote.trim().isEmpty()){
            model.addAttribute("error","Please fill course note before uploading");
        }else{
            courseService.uploadCourse(courseId);
            model.addAttribute("notification","Your course is now pending approval from admin");
        }
        model.addAttribute("user", userService.getUserById(userId));
        List<Course> inactiveCourseList = new ArrayList<>();
        inactiveCourseList = courseService.inactiveCourseListByUserId(userId);
        model.addAttribute("inactiveCourseList", inactiveCourseList);
        return "/teacherPage/inactiveCourseList";
    }
}
