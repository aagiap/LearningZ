package com.project.learningz.service;

import com.project.learningz.dto.TopCourseDTO;
import com.project.learningz.entity.Chapter;
import com.project.learningz.entity.Course;
import com.project.learningz.entity.VipPackage;
import com.project.learningz.repository.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QnAService {

    @Autowired
    private VipPackageRepository vipPackageRepository;

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UsersCourseService usersCourseService;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;

    public QnAService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    private String getVipPackagesFromDB() {
        List<VipPackage> vipPackages = vipPackageRepository.findVipAvitvatedPackages();

        for(VipPackage vipPackage : vipPackages){
            if(vipPackage.getDiscountedPrice() >0){
                vipPackage.setPrice(vipPackage.getDiscountedPrice());
            }
        }

        return vipPackages.stream()
                .map(vipPackage -> "package name: " + vipPackage.getPackageName() + " - " + "Price: " + vipPackage.getPrice() + "VND" + " - " + "Druration(month): " + vipPackage.getDuration())
                .collect(Collectors.joining("\n"));
    }

    private String getCoursesByGrade(String grade) {
        List<Course> courses = courseRepository.findCoursesByGrade(grade);
        return courses.stream()
                .map(course -> "Course title: " + course.getTitle() + " - " + "Course description: " + course.getDescription())
                .collect(Collectors.joining("\n"));
    }

    public String getPopularCourse(){
        Pageable topFive = PageRequest.of(0, 5);
        List<TopCourseDTO> topCourseDTOS = userCourseRepository.findPopularCourses(topFive);

        return topCourseDTOS.stream()
                .map(topCourseDTO -> "Course title: " + topCourseDTO.getCourse().getTitle() + " - " + "Number student: " + topCourseDTO.getEnrollmentCount() )
                .collect(Collectors.joining("\n"));
    }

    public String getCourseDetail(String title){
        Course course = courseRepository.findCourseByTitle(title);
        String data ="";
        if(course == null){
            return "Course not exist please write right title course!";
        } else {
            data += "Course title: " + course.getTitle() + "Course description: " + course.getDescription() +"\n";
            List<Chapter> chapters = course.getChapters();
            data += chapters.stream()
                    .map(chapter -> "Chapter title: " + chapter.getChapterTitle())
                    .collect(Collectors.joining("\n"));
            data += "Total lesson in course: " + lessonRepository.countNumberOfLesson(title) + "\n";
            data += "Total quiz in course: " + quizRepository.countNumberOfQuizzes(title) + "\n";
        }
        return data;
    }

    public String getStudentProgress(Integer userId){
        List<String> progress = usersCourseService.getStudentProgressForAI(userId);
        return progress.stream()
                .collect(Collectors.joining("\n"));
    }

    public String getDataFromAiResponse(String question){
        try{
            String functionChoosen = promptChooseFuction();
            StringBuilder conversationContext = new StringBuilder(functionChoosen);
            conversationContext.append("User: ").append(question).append("\n");
            conversationContext.append("Assistant: ");

            // Create request for Gemini
            Map<String, Object> requestBody = buildTextOnlyRequest(conversationContext.toString());

            // Send request
            String response = webClient.post()
                    .uri(geminiApiUrl + geminiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return extractAnswer(response);
        } catch (Exception e){
            return "Error processing: " + e.getMessage();
        }
    }

    public String getFunctionData(String aiResponse, Integer userId){
        if(aiResponse.contains("getVipPackagesFromDB")){
            return getVipPackagesFromDB();
        } else if (aiResponse.contains("getPopularCourse")) {
            return getPopularCourse();
        } else if (aiResponse.contains("getCourseDetail")) {
            String[] parts = aiResponse.trim().split(":");
            String title = parts[1];
            return getCourseDetail(title);
        } else if (aiResponse.contains("getCoursesByGrade")) {
            String[] parts = aiResponse.trim().split(":");
            String title = parts[1];
            return getCoursesByGrade(title);
        } else if (aiResponse.contains("getStudentProgress")) {
            return getStudentProgress(userId);
        } else{
            return "this question does not require data retrieval and you can answer it right away";
        }
    }

    private String promptChooseFuction(){
        return "You are AI assistant of learningZ, " + "\n"
                +"You can help students about vip package infomation, popular courses, List courses in a grade, course details." + "\n"
                + "You will have to choose the following functions to get the data to advise the students, analyze the question clearly and you are the counselor, choose only 1 appropriate function:" + "\n"
                + "If the user's question does not require data retrieval and you can answer it right away, answer No" + "\n"
                + "getVipPackagesFromDB() : Returns information of vip packages on the system" + "\n"
                + "getPopularCourse() : Returns the 5 most popular courses and the number of participants in that course" + "\n"
                + "getCourseDetail(String title) : Returns information of a specific course,choose this function if student ask about a course, the correct title must be filled in, the information includes the course name, description, list of chapters in that course, total number of lessons in the course, total number of quizzes in the course, if you choose this function, you must  Analyze the question and fill in the title of that course and answer according to the syntax: getCourseDetail:title of course" + "\n"
                + "getCoursesByGrade(String grade) : Returns list of course which have grade equal input, choose this function if student want to know about a grade, the correct grade name must be filled in, if you choose this function, you must  Analyze the question and fill in the grade name ( the grade name alway begin Grade + number) and answer according to the syntax: getCoursesByGrade:grade name" + "\n"
                + "getStudentProgress() : Returns a list of progress of courses that the student is taking" + "\n";

    }

    public String getAnswerWithHistory(String question, List<Map<String, String>> chatHistory, String dataforAnswer, String image) {
        try {
            // Read the base prompt from file
            String basePrompt = readFixedTextFile();
            if(!dataforAnswer.equalsIgnoreCase("this question does not require data retrieval and you can answer it right away")){
                basePrompt = "As an assistant for LearningZ, an e-learning platform for high school students (Grade 10, 11, 12), you will provide concise and friendly answers to students' questions,\n" +
                        "Answer Like a Friendly consultant, advise all the information that you can provide regarding the question\n" +
                       "You can help students about vip package infomation, popular courses, List courses in a grade, course details, check student learning progress." + "\n";
            }


            // Build the conversation context from chat history
            StringBuilder conversationContext = new StringBuilder(basePrompt);
            conversationContext.append("\n\nConversation history:\n");

            if (chatHistory != null && !chatHistory.isEmpty()) {
                for (Map<String, String> message : chatHistory) {
                    String role = message.get("role");
                    String content = message.get("content");

                    if ("user".equals(role)) {
                        conversationContext.append("User: ").append(content).append("\n");
                    } else if ("ai".equals(role)) {
                        conversationContext.append("Assistant: ").append(content).append("\n");
                    }
                }
            }

            //Add data for answer
            conversationContext.append("Data for answer: ").append(dataforAnswer).append("\n");

            // Add the current question
            conversationContext.append("User: ").append(question).append("\n");
            conversationContext.append("Assistant: ");

            // Create request for Gemini - with or without image
            Map<String, Object> requestBody;
            if (image != null && !image.isEmpty() && image.startsWith("data:image")) {
                requestBody = buildRequestWithImage(conversationContext.toString(), image);
            } else {
                requestBody = buildTextOnlyRequest(conversationContext.toString());
            }

            // Send request
            String response = webClient.post()
                    .uri(geminiApiUrl + geminiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return extractAnswer(response);
        } catch (Exception e) {
            return "Error processing: " + e.getMessage();
        }
    }

    // Original method kept for backward compatibility
    public String getAnswer(String question) {
        try {
            // Read content from file
            String documentText = readFixedTextFile();

            // Create full question
            String fullQuestion = documentText + "\n\nQuestion: " + question;

            // Create request for Gemini
            Map<String, Object> requestBody = buildTextOnlyRequest(fullQuestion);

            // Send request
            String response = webClient.post()
                    .uri(geminiApiUrl + geminiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return extractAnswer(response);
        } catch (Exception e) {
            return "Error processing: " + e.getMessage();
        }
    }

    public String readFixedTextFile() throws IOException {
        Path filePath = Path.of("document/PromptAI.txt");

        // Kiểm tra nếu file không tồn tại
        if (!Files.exists(filePath)) {
            return "File không tồn tại!";
        }

        return Files.lines(filePath, StandardCharsets.UTF_8)
                .collect(Collectors.joining("\n"));
    }

    // Method for text-only requests
    private Map<String, Object> buildTextOnlyRequest(String text) {
        return Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", text)
                        ))
                )
        );
    }

    // Method for requests with image
    private Map<String, Object> buildRequestWithImage(String text, String imageData) {
        List<Map<String, Object>> parts = new ArrayList<>();

        // Add text part
        parts.add(Map.of("text", text));

        // Add image part - extract base64 data from data URL
        if (imageData != null && !imageData.isEmpty()) {
            String base64Image = imageData.substring(imageData.indexOf(",") + 1);
            Map<String, Object> imagePart = new HashMap<>();
            imagePart.put("inline_data", Map.of(
                    "mime_type", getMimeTypeFromDataUrl(imageData),
                    "data", base64Image
            ));
            parts.add(imagePart);
        }

        return Map.of(
                "contents", List.of(
                        Map.of("parts", parts)
                )
        );
    }

    // Helper method to extract MIME type from data URL
    private String getMimeTypeFromDataUrl(String dataUrl) {
        if (dataUrl == null || !dataUrl.startsWith("data:")) {
            return "image/jpeg"; // Default
        }

        int endIndex = dataUrl.indexOf(";");
        if (endIndex > 5) { // "data:".length() == 5
            return dataUrl.substring(5, endIndex);
        }

        return "image/jpeg"; // Default if parsing fails
    }

    private String extractAnswer(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        return jsonObject
                .getJSONArray("candidates")
                .getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0)
                .getString("text");
    }
}

