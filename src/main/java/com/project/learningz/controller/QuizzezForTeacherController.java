package com.project.learningz.controller;

import com.project.learningz.dto.QuestionDetailDTO;
import com.project.learningz.dto.QuizDetailDTO;
import com.project.learningz.dto.QuizJoinToGradeDTO;
import com.project.learningz.entity.*;
import com.project.learningz.repository.QuestionExpertRepository;
import com.project.learningz.repository.QuizQuestionRepository;
import com.project.learningz.repository.QuizRepository;
import com.project.learningz.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.embedded.NettyWebServerFactoryCustomizer;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/teacher")
@Controller
public class QuizzezForTeacherController {

    @Autowired
    QuizQuestionBankService quizQuestionBankService;
    @Autowired
    QuestionExpertService questionService;
    @Autowired
    QuizService quizService;
    @Autowired
    private UserManagementService userManagementService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private NettyWebServerFactoryCustomizer nettyWebServerFactoryCustomizer;
    @Autowired
    private QuestionExpertRepository questionExpertRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @GetMapping("/{lessonId}/quizzes")
    public String getAllQuizzes(@PathVariable Integer lessonId,
                                @RequestParam(name = "keyword", required = false) String keyword,
                                @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                @AuthenticationPrincipal OAuth2User userOAuth2,
                                Model model) {
        String username = null;

        if (user != null) {
            username = user.getUsername();
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userManagementService.findUserNameByEmail(email);
        }
        String avatarUrl = userManagementService.getAvtByUsername(username);
        if (avatarUrl == null) {
            avatarUrl = "/image/AvartaDefault.jpg";
        }

        Page<QuizDetailDTO> quizzes = quizService.getQuizzesByLessonIdAndKey(lessonId, keyword, page, size);

        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "teacherPage/quizzesList";
    }

    @GetMapping("/questions/{quizId}")
    public String getAllQuestions(@PathVariable Integer quizId,
                                  @RequestParam(name = "keyword", required = false) String keyword,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                  @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                  @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                  @AuthenticationPrincipal OAuth2User userOAuth2,
                                  Model model) {
        String username = null;

        if (user != null) {
            username = user.getUsername();
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userManagementService.findUserNameByEmail(email);
        }
        String avatarUrl = userManagementService.getAvtByUsername(username);
        if (avatarUrl == null) {
            avatarUrl = "/image/AvartaDefault.jpg";
        }

        Page<QuestionDetailDTO> questions = questionService.filterQuestionByQuizIdAndKeyword(quizId, keyword, page, size);

        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("questions", questions);
        model.addAttribute("quizId", quizId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "teacherPage/questionsOfQuiz";
    }

    @GetMapping("/questions/{quizId}/{questionId}")
    public String getDetailQuestions(@PathVariable Integer quizId,
                                     @PathVariable Integer questionId,
                                     @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                     @AuthenticationPrincipal OAuth2User userOAuth2,
                                     Model model) {
        String username = null;

        if (user != null) {
            username = user.getUsername();
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userManagementService.findUserNameByEmail(email);
        }
        String avatarUrl = userManagementService.getAvtByUsername(username);
        if (avatarUrl == null) {
            avatarUrl = "/image/AvartaDefault.jpg";
        }

        List<String> lessons = lessonService.getAllLessonInQuestions();

        QuestionDetailDTO question = questionService.getQuestionDetail(questionId);


        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("quizId", quizId);
        model.addAttribute("question", question);
        model.addAttribute("lessons", lessons);
        model.addAttribute("selectedLesson", question.getLessonTitle());

        return "teacherPage/questionDetail";
    }

    @PostMapping("/{quizId}/{questionId}/deleteQuestion")
    public String deleteQuestions(@PathVariable Integer quizId,
                                  @PathVariable Integer questionId,
                                  RedirectAttributes redirectAttributes) {

        QuestionDetailDTO question = questionService.getQuestionDetail(questionId);
        if (question != null) {
            quizQuestionBankService.deleteQuestionByQuizIdAndQuestionId(quizId, questionId);
            questionService.deleteQuestion(questionId);
            redirectAttributes.addFlashAttribute("successMessage", "Question deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Question not found.");
        }

        return "redirect:/teacher/questions/" + quizId;
    }

    @PostMapping("/{quizId}/{questionId}/updateQuestion")
    public String updateQuestions(@PathVariable Integer quizId,
                                  @PathVariable Integer questionId,
                                  RedirectAttributes redirectAttributes) {

        QuestionBank question = questionService.getQuestionBankById(questionId);
        try {
            questionService.updateQuestion(question);
            redirectAttributes.addFlashAttribute("successMessage", "Question updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Question not found.");
        }
        return "redirect:/teacher/questions/" + quizId;
    }

    @GetMapping("/{quizId}/createQuestion")
    public String createQuestionForm(@PathVariable Integer quizId,
                                     @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                     @AuthenticationPrincipal OAuth2User userOAuth2,
                                     Model model) {
        String username = null;

        if (user != null) {
            username = user.getUsername();
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userManagementService.findUserNameByEmail(email);
        }
        String avatarUrl = userManagementService.getAvtByUsername(username);
        if (avatarUrl == null) {
            avatarUrl = "/image/AvartaDefault.jpg";
        }

        QuizDetailDTO currentQuiz = quizService.getQuizDetailById(quizId);

        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("currentQuiz", currentQuiz);

        return "teacherPage/questionCreate";
    }

    @PostMapping("/{quizId}/createQuestion")
    public String createQuestion(@PathVariable Integer quizId,
                                 @RequestParam(value = "content", required = false) String content,
                                 @RequestParam(value = "option1", required = false) String correctOption,
                                 @RequestParam(value = "option1", required = false) String option1,
                                 @RequestParam(value = "option2", required = false) String option2,
                                 @RequestParam(value = "option3", required = false) String option3,
                                 @RequestParam(value = "option4", required = false) String option4,
                                 RedirectAttributes attributes) {
        Quiz quiz = quizService.getQuizById(quizId);
        try {
            questionService.createQuestion(content, correctOption, option1, option2, option3, option4, quiz);
            attributes.addFlashAttribute("successMessage", "Question created successfully.");
        } catch (Exception e) {
            attributes.addFlashAttribute("errorMessage", "Question has failed created successfully");
        }
        return "redirect:/teacher/questions/" + quizId;
    }

    @GetMapping("/{lessonId}/quizzesCreate")
    public String createQuizForm(@PathVariable Integer lessonId,
                                 @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                 @AuthenticationPrincipal OAuth2User userOAuth2,
                                 Model model) {
        String username = null;

        if (user != null) {
            username = user.getUsername();
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userManagementService.findUserNameByEmail(email);
        }
        String avatarUrl = userManagementService.getAvtByUsername(username);
        if (avatarUrl == null) {
            avatarUrl = "/image/AvartaDefault.jpg";
        }

        Lesson lesson = lessonService.getLessonById(lessonId);
        Chapter chapter = lesson.getChapter();
        Course course = chapter.getCourse();
        Grade grade = course.getGrade();
        Subject subject = course.getSubject();

        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("lesson", lesson);
        model.addAttribute("chapter", chapter);
        model.addAttribute("course", course);
        model.addAttribute("grade", grade);
        model.addAttribute("subject", subject);

        return "teacherPage/quizzesCreate";
    }

    @PostMapping("/{lessonId}/quizzesCreate")
    public String createQuiz(@PathVariable Integer lessonId,
                             @RequestParam(value = "quizTitle", required = false) String quizTitle,
                             @RequestParam(value = "timeLimit", required = false) Integer timeLimit,
                             @RequestParam(value = "totalQuestions", required = false) Integer totalQuestions,
                             @RequestParam(value = "question", required = false) List<String> question,
                             @RequestParam(value = "correctAnswer", required = false) List<String> correctAnswer,
                             @RequestParam(value = "optionA", required = false) List<String> optionA,
                             @RequestParam(value = "optionB", required = false) List<String> optionB,
                             @RequestParam(value = "optionC", required = false) List<String> optionC,
                             @RequestParam(value = "optionD", required = false) List<String> optionD,
                             RedirectAttributes attributes) {

        System.out.println("question no" + question.size());

        Lesson lesson = lessonService.getLessonById(lessonId);

        Quiz quiz = new Quiz();
        quiz.setTitle(quizTitle);
        quiz.setTimeLimit(timeLimit);
        quiz.setTotalQuestions(totalQuestions);
        quiz.setLesson(lesson);
        quizRepository.save(quiz);

        System.out.println("quiz no" + quiz.getId());

        for (int i = 0; i < question.size(); i++) {
            QuestionBank questionBank = new QuestionBank();
            questionBank.setCorrectOption(correctAnswer.get(i));
            questionBank.setOption1(optionA.get(i));
            questionBank.setOption2(optionB.get(i));
            questionBank.setOption3(optionC.get(i));
            questionBank.setOption4(optionD.get(i));
            questionBank.setContent(question.get(i));
            questionBank = questionExpertRepository.save(questionBank); // Lưu và cập nhật ID
            System.out.println("Saved question ID: " + questionBank.getId()); // Debug

            QuizQuestion quizQuestion = new QuizQuestion();
            QuizQuestionId quizQuestionId = new QuizQuestionId(quiz.getId(), questionBank.getId());
            quizQuestion.setId(quizQuestionId);
            quizQuestion.setQuiz(quiz);
            quizQuestion.setQuestion(questionBank);
            quizQuestionRepository.save(quizQuestion);
            System.out.println("quizquestion no" + quizQuestionId.toString());

        }
        attributes.addFlashAttribute("successMessage", "Quiz has created successfully");
        attributes.addAttribute("lessonId", lessonId);
        return "redirect:/teacher/" + lessonId + "/quizzes";
    }


}
