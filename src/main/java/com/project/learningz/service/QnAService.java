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

    public String getAnswer(String question,MultipartFile image) {
        try {
            // 🔥 Lấy dữ liệu từ SQL Server
            String vipPackageData = getVipPackagesFromDB();

            // 🔥 Đọc nội dung từ file `.txt`
            String documentText = readFixedTextFile();

            // 🔥 Tạo nội dung câu hỏi đầy đủ
            String fullQuestion = documentText + "\n\n" + vipPackageData + "\n\nCâu hỏi: " + question;

            // 🔥 Encode ảnh nếu có
            String base64Image = encodeImage(image);

            // 🔥 Tạo request gửi đến Gemini
            Map<String, Object> requestBody = buildRequest(fullQuestion, base64Image);

            // 🔥 Gửi request
            String response = webClient.post()
                    .uri(geminiApiUrl + geminiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return extractAnswer(response);
        } catch (Exception e) {
            return "Lỗi xử lý: " + e.getMessage();
        }
    }

    private String encodeImage(MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) return null;
        return Base64.getEncoder().encodeToString(image.getBytes());
    }

    private String readFixedTextFile() throws IOException {
        //  Đọc file `.txt` trong resources
        ClassPathResource resource = new ClassPathResource("document/PromptAI.txt");

        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private Map<String, Object> buildRequest(String question, String base64Image) {
        if (base64Image != null) {
            return Map.of("contents", new Object[]{
                    Map.of("parts", new Object[]{
                            Map.of("text", question),
                            Map.of("inline_data", Map.of(
                                    "mime_type", "image/jpeg",
                                    "data", base64Image
                            ))
                    })
            });
        } else {
            return Map.of("contents", new Object[]{
                    Map.of("parts", new Object[]{
                            Map.of("text", question)
                    })
            });
        }
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
