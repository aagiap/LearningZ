package com.project.learningz.service;


import com.project.learningz.entity.VipPackage;
import com.project.learningz.repository.VipPackageRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QnAService {

    @Autowired
    private VipPackageRepository vipPackageRepository;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;

    public QnAService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    private String getVipPackagesFromDB() {
        List<VipPackage> vipPackages = vipPackageRepository.findAll();


        return vipPackages.stream()
                .map(vipPackage -> "package name: " + vipPackage.getPackageName() + " - " + "Price: " + vipPackage.getPrice() + " - " + "Druration(month): " + vipPackage.getDuration())
                .collect(Collectors.joining("\n"));
    }

    public String getAnswerWithHistory(String question, List<Map<String, String>> chatHistory) {
        try {
            // Read the base prompt from file
            String basePrompt = readFixedTextFile();

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

            // Add the current question
            conversationContext.append("User: ").append(question).append("\n");
            conversationContext.append("Assistant: ");

            // Create request for Gemini
            Map<String, Object> requestBody = buildRequest(conversationContext.toString());

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
            Map<String, Object> requestBody = buildRequest(fullQuestion);

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

    private String readFixedTextFile() throws IOException {
        // Read .txt file from resources
        ClassPathResource resource = new ClassPathResource("document/PromptAI.txt");

        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private Map<String, Object> buildRequest(String question) {
        return Map.of("contents", new Object[]{
                Map.of("parts", new Object[]{
                        Map.of("text", question)
                })
        });
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
