package com.project.learningz.controller;

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
public class ExpertController {

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

    @GetMapping(path = "/expert")
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
            model.addAttribute("notification","Empty List");
        }else{
            model.addAttribute("courseList",courseList);
        }

        return "/expertPage/courseListExpert";
    }

    @GetMapping(path = "/expert/search")
    public String courseListSearch(Model model,
                                   String courseSearchKey,
                                   String subject) {
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
        if (courseSearchKey != null) {
            courseList = courseService.findCourses(user.getId(), subject, courseSearchKey);
        }else{
            courseList = courseService.findCourses(user.getId(), subject, "");
        }
        if(courseList.isEmpty()){
            model.addAttribute("notification","Empty List");
        }else{
            model.addAttribute("courseList",courseList);
        }
        return "/expertPage/courseListExpert";
    }

    @PostMapping(path = "/expert/edit")
    public String editCourse(Model model,
                             @RequestParam("courseId") int courseId){
        Course course = courseService.findByCourseId(courseId);
        model.addAttribute("course",course);
        return "/expertPage/editCourse";
    }

    @PostMapping(path = "/expert/edit/update")
    public String updateCourse(Model model,
                               @RequestParam("id") int id,
                               @RequestParam("createdByUserId") int createdByUseID,
                               @RequestParam("courseDriveLink") String courseDriveLink,
                               @RequestParam("title") String title,
                               @RequestParam("subject") String subject,
                               @RequestParam("gradeId") int gradeId,
                               @RequestParam("courseImageUrl") MultipartFile courseImageUrl,
                               @RequestParam("description") String description){
        if(title.isEmpty() || title.trim().isEmpty()){
            model.addAttribute("error","Please fill title");
            model.addAttribute("course",courseService.findByCourseId(id));
        }else{
            try{
                courseService.updateCourse(id, createdByUseID, courseDriveLink, title,
                        subject, gradeId, courseImageUrl,description);
            }catch(Exception e){
                model.addAttribute("error","Course Update Failed");
                model.addAttribute("course",courseService.findByCourseId(id));
                return "/expertPage/editCourse";
            }
            model.addAttribute("course",courseService.findByCourseId(id));
            model.addAttribute("notification","Update Course Successfully");
        }
        return "/expertPage/editCourse";
    }

    @GetMapping(path = "/expert/add_course")
    public String addCourse(Model model){
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
        model.addAttribute("createdById",user.getId());
        return "/expertPage/addCourse";
    }

    @PostMapping(path = "/expert/add_course/create")
    public String confirmAddCourse(Model model,
                                   @RequestParam("createdById") int createdById,
                                   @RequestParam("title") String title,
                                   @RequestParam("subject") String subject,
                                   @RequestParam("gradeId") int gradeId,
                                   @RequestParam("courseImageUrl") MultipartFile courseImageUrl,
                                   @RequestParam("description") String description){
        if(title.isEmpty() || title.trim().isEmpty()){
            model.addAttribute("error","Please fill title");
            return "/expertPage/addCourse";
        }
        try{
            courseService.createCourse(createdById, gradeId, subject, title, description, courseImageUrl);
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("error","Course Creation Failed");
            return "/expertPage/addCourse";
        }
        model.addAttribute("notification","Course Creation Successfully");
        return "/expertPage/addCourse";
    }

    @GetMapping(path = "/expert/chapter")
    public String chapterList(Model model,
                              @RequestParam("courseId") int courseId){
        List<ChapterDetailDTO> chapterList = new ArrayList<>();
        chapterList = chapterService.allChaptersByCourseId(courseId);
        if(chapterList.isEmpty()){
            model.addAttribute("courseId", courseId);
            model.addAttribute("notification","Empty List");
        }else {
            model.addAttribute("courseId", courseId);
            model.addAttribute("chapterList",chapterList);
        }
        return "/expertPage/chapterList";
    }

    @GetMapping(path = "/expert/chapter/search")
    public String chapterListSearch(Model model,
                                    int courseId,
                                    String chapterSearchKey) {
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
        return "/expertPage/chapterList";
    }

    @PostMapping(path = "/expert/chapter/edit")
    public String editChapter(Model model,
                              int chapterId){
        Chapter chapter = chapterService.getChapterById(chapterId);
        model.addAttribute("chapter",chapter);
        return "expertPage/editChapter";
    }

    @PostMapping(path = "/expert/chapter/edit/update")
    public String updateChapter(Model model,
                                @RequestParam("id") int id,
                                @RequestParam("courseId") int courseId,
                                @RequestParam("chapterDriveLink") String chapterDriveLink,
                                @RequestParam("order") int order,
                                @RequestParam("title") String title,
                                @RequestParam("description") String description){
        String error = chapterService.checkUpdate(id,courseId,order,title);
        if(error.equals("")){
            try{
                chapterService.updateChapter(id,order,title,description);
            }catch(Exception e){
                model.addAttribute("error","Chapter Update Failed");
            }
            model.addAttribute("notification","Chapter Update Successfully");
            model.addAttribute("chapter", chapterService.getChapterById(id));
        }else{
            model.addAttribute("error",error);
            model.addAttribute("chapter", chapterService.getChapterById(id));
        }
        return "/expertPage/editChapter";
    }

    @GetMapping(path = "/expert/chapter/addChapter")
    public String addChapter(Model model,
                             @RequestParam("courseId") int courseId){
        model.addAttribute("courseId", courseId);
        return "/expertPage/addChapter";
    }

    @PostMapping(path = "/expert/chapter/addChapter/create")
    public String confirmAddChapter(Model model,
                                    @RequestParam("courseId") int courseId,
                                    @RequestParam("order") int order,
                                    @RequestParam("title") String title,
                                    @RequestParam("description") String description){
        String error = chapterService.checkCreate(courseId,order,title);
        if(error.equals("")){
            try{
                chapterService.createChapter(courseId, order, title, description);
            }catch(Exception e){
                model.addAttribute("error","Chapter Creation Failed");
                model.addAttribute("courseId", courseId);
                return "/expertPage/addChapter";
            }
        }else{
            model.addAttribute("error",error);
            model.addAttribute("courseId", courseId);
            return "/expertPage/addChapter";
        }
        model.addAttribute("notification","Chapter Creation Successfully");
        model.addAttribute("courseId", courseId);
        return "/expertPage/addChapter";
    }

    @GetMapping(path = "/expert/chapter/lesson")
    public String lessonList(Model model,
                             @RequestParam("chapterId") int chapterId){
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
        return "/expertPage/lessonList";
    }

    @GetMapping(path = "/expert/chapter/lesson/search")
    public String lessonListSearch(Model model,
                                   @RequestParam("chapterId") int chapterId,
                                   @RequestParam("lessonSearchKey") String lessonSearchKey ){
        List<LessonDetailDTO> lessonList = new ArrayList<>();
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
        return "/expertPage/lessonList";
    }

    @PostMapping(path = "/expert/chapter/lesson/edit")
    public String editLesson(Model model,
                             @RequestParam("lessonId") int lessonId){
        Lesson lesson = lessonService.getLessonById(lessonId);
        List<QuizType> typeList = new ArrayList<>();
        typeList.add(QuizType.PRACTICE);typeList.add(QuizType.EXAM);
        model.addAttribute("chapterId", lessonService.getLessonById(lessonId).getChapter().getId());
        model.addAttribute("quizTypeList",typeList);
        model.addAttribute("lesson",lesson);
        return "expertPage/editLesson";
    }

    @PostMapping(path = "/expert/chapter/lesson/edit/update")
    public String updateLesson(Model model,
                               @RequestParam("lessonId") int lessonId,
                               @RequestParam("chapterId") int chapterId,
                               @RequestParam("lessonDriveLink") String lessonDriveLink,
                               @RequestParam("documentFolderLink") String documentFolderLink,
                               @RequestParam("videoFolderLink") String videoFolderLink,
                               @RequestParam("quizImageLink") String quizImageLink,
                               @RequestParam("lessonTitle") String lessonTitle,
                               @RequestParam("quizType") QuizType quizType,
                               @RequestParam("description") String description){
        List<QuizType> typeList = new ArrayList<>();
        typeList.add(QuizType.PRACTICE);typeList.add(QuizType.EXAM);
        if(lessonTitle.trim().isEmpty()){
            model.addAttribute("error","Empty Title");
            model.addAttribute("chapterId",chapterId);
            model.addAttribute("lesson",lessonService.getLessonById(lessonId));
            model.addAttribute("quizTypeList",quizType);
        }else{
            lessonService.updateLesson(lessonId,chapterId,lessonDriveLink,documentFolderLink,videoFolderLink,
                    quizImageLink,lessonTitle,quizType,description);
            model.addAttribute("lesson",lessonService.getLessonById(lessonId));
            model.addAttribute("quizTypeList",quizType);
            model.addAttribute("chapterId",chapterId);
            model.addAttribute("notification","Update Chapter Successfully");
        }
        return "expertPage/editLesson";
    }

    @GetMapping(path = "/expert/chapter/lesson/addLesson")
    public String addLesson(Model model,
                            @RequestParam("chapterId") int chapterId){
        List<QuizType> typeList = new ArrayList<>();
        typeList.add(QuizType.PRACTICE);typeList.add(QuizType.EXAM);
        model.addAttribute("quizTypeList",typeList);
        model.addAttribute("chapterId", chapterId);
        return "/expertPage/addLesson";
    }

    @PostMapping(path = "/expert/chapter/lesson/addLesson/create")
    public String confirmAddLesson(Model model,
                                   @RequestParam("chapterId") int chapterId,
                                   @RequestParam("lessonTitle") String lessonTitle,
                                   @RequestParam("quizType") QuizType quizType,
                                   @RequestParam("description") String description){
        if(lessonTitle.trim().isEmpty()){
            model.addAttribute("error","Empty Title");
            model.addAttribute("chapterId",chapterId);
        }else{
            try{
                lessonService.createLesson(chapterId,lessonTitle,quizType,description);
            }catch(Exception e){
                model.addAttribute("chapterId",chapterId);
                model.addAttribute("error","Lesson Creation Failed");
            }
            model.addAttribute("notification","Lesson Creation Successfully");
            model.addAttribute("chapterId",chapterId);
        }
        return "/expertPage/addLesson";
    }

    @GetMapping(path = "/expert/chapter/lesson/videos")
    public String videoList(Model model,
                            @RequestParam("lessonId") int lessonId){
        List<Video> videoList = new ArrayList<>();
        videoList = videoService.getVideoListByLessonId(lessonId);
        if(videoList.isEmpty()){
            model.addAttribute("chapterId",lessonService.getLessonById(lessonId).getChapter().getId());
            model.addAttribute("lessonId", lessonId);
            model.addAttribute("notification","Empty Video");
        }else{
            model.addAttribute("chapterId",lessonService.getLessonById(lessonId).getChapter().getId());
            model.addAttribute("lessonId",lessonId);
            model.addAttribute("videoList",videoList);
        }
        return "/expertPage/videoList";
    }

    @GetMapping(path = "/expert/chapter/lesson/videos/search")
    public String videoListSearch(Model model,
                                  @RequestParam("videoSearchKey") String keyWord,
                                  @RequestParam("lessonId") int lessonId){
        List<Video> videoList = new ArrayList<>();
        videoList = videoService.findVideo(lessonId, keyWord);
        if(videoList.isEmpty()){
            model.addAttribute("chapterId",lessonService.getLessonById(lessonId).getChapter().getId());
            model.addAttribute("lessonId", lessonId);
            model.addAttribute("notification","Empty Video");
        }else{
            model.addAttribute("chapterId",lessonService.getLessonById(lessonId).getChapter().getId());
            model.addAttribute("lessonId",lessonId);
            model.addAttribute("videoList",videoList);
        }
        return "/expertPage/videoList";
    }

    @GetMapping(path = "/expert/chapter/lesson/videos/addVideo")
    public String addVideo(Model model,
                           @RequestParam("lessonId") int lessonId){
        model.addAttribute("lessonId", lessonId);
        return "/expertPage/addVideo";
    }

    @PostMapping(path = "/expert/chapter/lesson/videos/addVideo/create")
    public String confirmAddVideo(Model model,
                                  @RequestParam("lessonId") int lessonId,
                                  @RequestParam("videoTitle") String videoTitle,
                                  @RequestParam("videoUrl") MultipartFile videoUrl) {
        String error = videoService.videoCheck(videoTitle, videoUrl);
        if (error.isEmpty()) {
            try {
                videoService.createVideo(lessonId, videoTitle, videoUrl);
            } catch (Exception e) {
                model.addAttribute("lessonId", lessonId);
                model.addAttribute("error", "Video Creation Failed");
                return "/expertPage/addVideo";
            }
            model.addAttribute("notification", "Video Creation Successfully");
            model.addAttribute("lessonId", lessonId);
            return "/expertPage/addVideo";
        } else {
            model.addAttribute("lessonId", lessonId);
            model.addAttribute("error", error);
            return "/expertPage/addVideo";
        }
    }

    @PostMapping(path = "/expert/chapter/lesson/videos/edit")
    public String editVideo(Model model,
                            @RequestParam("videoId") int videoId){
        Video video = videoService.getVideoById(videoId);
        model.addAttribute("video", video);
        return "/expertPage/editVideo";
    }

    @PostMapping(path = "/expert/chapter/lesson/videos/edit/update")
    public String updateVideo(Model model,
                              @RequestParam("lessonId") int lessonId,
                              @RequestParam("videoId") int videoId,
                              @RequestParam("videoTitle") String videoTitle,
                              @RequestParam("videoUrl") MultipartFile videoUrl) {
        if(videoTitle.trim().isEmpty()){
            model.addAttribute("error","Empty Title");
        }else{
            try{
                videoService.updateVideo(lessonId,videoId,videoTitle,videoUrl);
            }catch(Exception e){
                model.addAttribute("error","Video Update Failed");
                Video video = videoService.getVideoById(videoId);
                model.addAttribute("video", video);
                return "/expertPage/editVideo";
            }
            model.addAttribute("notification","Video Update Successfully");
            Video video = videoService.getVideoById(videoId);
            model.addAttribute("video", video);
        }
        Video video = videoService.getVideoById(videoId);
        model.addAttribute("video", video);
        return "/expertPage/editVideo";
    }

    @GetMapping(path = "/expert/chapter/lesson/docs")
    public String docList(Model model,
                          @RequestParam("lessonId") int lessonId){
        List<PDF> pdfList = new ArrayList<>();
        pdfList = pdfService.findListByLessonId(lessonId);
        if(pdfList.isEmpty()){
            model.addAttribute("notification","Empty Document");
            model.addAttribute("lessonId",lessonId);
            model.addAttribute("chapterId",lessonService.getLessonById(lessonId).getChapter().getId());
        }else{
            model.addAttribute("lessonId",lessonId);
            model.addAttribute("chapterId",lessonService.getLessonById(lessonId).getChapter().getId());
            model.addAttribute("pdfList",pdfList);
        }
        return "/expertPage/docList";
    }

    @GetMapping(path = "/expert/chapter/lesson/docs/search")
    public String docListSearch(Model model,
                                @RequestParam("lessonId") int lessonId,
                                @RequestParam("docSearchKey") String docSearchKey){
        List<PDF> pdfList = new ArrayList<>();
        pdfList = pdfService.findDocs(lessonId, docSearchKey);
        if(pdfList.isEmpty()){
            model.addAttribute("notification","Empty Document");
            model.addAttribute("lessonId",lessonId);
            model.addAttribute("chapterId",lessonService.getLessonById(lessonId).getChapter().getId());
        }else{
            model.addAttribute("lessonId",lessonId);
            model.addAttribute("chapterId",lessonService.getLessonById(lessonId).getChapter().getId());
            model.addAttribute("pdfList",pdfList);
        }
        return "/expertPage/docList";
    }

    @GetMapping(path = "/expert/chapter/lesson/docs/addDoc")
    public String addDoc(Model model,
                         @RequestParam("lessonId") int lessonId){
        model.addAttribute("lessonId", lessonId);
        return "/expertPage/addDoc";
    }

    @PostMapping(path = "/expert/chapter/lesson/docs/addDoc/create")
    public String confirmAddDoc(Model model,
                                @RequestParam("lessonId") int lessonId,
                                @RequestParam("docTitle") String docTitle,
                                @RequestParam("docUrl") MultipartFile docUrl) {
        String error = pdfService.docCheck(docTitle, docUrl);
        if(error.isEmpty()){
            try{
                pdfService.createDoc(lessonId,docTitle,docUrl);
            }catch(Exception e){
                model.addAttribute("lessonId",lessonId);
                model.addAttribute("error","Document Creation Failed");
                return "/expertPage/addDoc";
            }
            model.addAttribute("notification","Document Creation Successfully");
            model.addAttribute("lessonId",lessonId);
            return "/expertPage/addDoc";
        }else{
            model.addAttribute("lessonId",lessonId);
            model.addAttribute("error",error);
            return "/expertPage/addDoc";
        }
    }

    @PostMapping(path = "/expert/chapter/lesson/docs/edit")
    public String editDoc(Model model,
                          @RequestParam("pdfId") int pdfId){
        PDF pdf = pdfService.getPdfById(pdfId);
        model.addAttribute("pdf", pdf);
        return "/expertPage/editDoc";
    }

    @PostMapping(path = "/expert/chapter/lesson/docs/edit/update")
    public String updateDoc(Model model,
                            @RequestParam("lessonId") int lessonId,
                            @RequestParam("docId") int docId,
                            @RequestParam("docTitle") String docTitle,
                            @RequestParam("docUrl") MultipartFile docUrl) {
        if(docTitle.trim().isEmpty()){
            model.addAttribute("error","Empty Title");
        }else{
            try{
                pdfService.updateDoc(lessonId,docId,docTitle,docUrl);
            }catch(Exception e){
                model.addAttribute("error","Document Update Failed");
                PDF pdf = pdfService.getPdfById(docId);
                model.addAttribute("pdf", pdf);
                return "/expertPage/editDoc";
            }
            model.addAttribute("notification","Document Update Successfully");
            PDF pdf = pdfService.getPdfById(docId);
            model.addAttribute("pdf", pdf);
        }
        PDF pdf = pdfService.getPdfById(docId);
        model.addAttribute("pdf", pdf);
        return "/expertPage/editDoc";
    }
}
