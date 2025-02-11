package com.project.learningz.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleDriveService {
    private static final String APPLICATION_NAME = "LearningZ";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String AVATAR_FOLDER_ID = "1aU0PQpMUjlAraFb4hCu7MWkKB7tL9CUB";

    private Drive getDriveService() throws GeneralSecurityException, IOException {
        InputStream in = getClass().getResourceAsStream("/learningz-450206-bee3f9a78097.json");
        if (in == null) {
            throw new IOException("Credential file not found");
        }
        GoogleCredentials credential = GoogleCredentials.fromStream(in)
                .createScoped(Collections.singleton(DriveScopes.DRIVE_FILE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, new HttpCredentialsAdapter(credential))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public String uploadFileAvatar(MultipartFile file) throws IOException, GeneralSecurityException {
        Drive driveService = getDriveService();
        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setParents(Collections.singletonList(AVATAR_FOLDER_ID));

        InputStreamContent content = new InputStreamContent(file.getContentType(), file.getInputStream());
        File uploadedFile = driveService.files().create(fileMetadata, content)
                .setFields("id,webViewLink")
                .execute();

        Permission permission = new Permission();
        permission.setType("anyone");
        permission.setRole("reader");
        driveService.permissions().create(uploadedFile.getId(), permission).execute();

        return "https://lh3.googleusercontent.com/d/" + uploadedFile.getId();
    }

    public void deleteFile(String id) throws IOException, GeneralSecurityException {
        Drive driveService = getDriveService();
        driveService.files().delete(id).execute();
    }
}