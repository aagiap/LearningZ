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
    private static final String COURSES_FOLDER_ID = "1VPA7JP72n0qjdp2tObhnjW49m5ypzh7_";
    private static final String SLIDE_FOLDER_ID = "1SUXO6c-VHXVo_1lhf0NHoAUwD5nDAUzz";
    private static final String COURSE_LOGO_FOLDER_ID = "1OmNEeRfUV16knh6ONH1x6OdQjQhBtP6N";
    private static final String POST_IMAGE_FOLDER_ID = "16KOxhheevyHN5Z5w4x-fzy0xQDIQVtah";

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

    public String uploadFileCourseImage(MultipartFile file) throws IOException, GeneralSecurityException {
        Drive driveService = getDriveService();
        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setParents(Collections.singletonList(COURSE_LOGO_FOLDER_ID));

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

    public String uploadFileDocument(MultipartFile file, String folderId) throws IOException, GeneralSecurityException {
        Drive driveService = getDriveService();

        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setParents(Collections.singletonList(folderId));

        InputStreamContent content = new InputStreamContent(file.getContentType(), file.getInputStream());

        File uploadedFile = driveService.files().create(fileMetadata, content)
                .setFields("id, webViewLink")
                .execute();

        Permission permission = new Permission();
        permission.setType("anyone");
        permission.setRole("reader");
        driveService.permissions().create(uploadedFile.getId(), permission).execute();

        return "https://drive.google.com/file/d/" + uploadedFile.getId() + "/preview";
    }


    public String uploadFileVideo(MultipartFile file, String videoFolder) throws IOException, GeneralSecurityException {
        Drive driveService = getDriveService();
        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setParents(Collections.singletonList(videoFolder));

        InputStreamContent content = new InputStreamContent(file.getContentType(), file.getInputStream());
        File uploadedFile = driveService.files().create(fileMetadata, content)
                .setFields("id,webViewLink")
                .execute();

        Permission permission = new Permission();
        permission.setType("anyone");
        permission.setRole("reader");
        driveService.permissions().create(uploadedFile.getId(), permission).execute();

        return "https://drive.google.com/file/d/" + uploadedFile.getId() + "/preview";
    }

    public void deleteFile(String id) throws IOException, GeneralSecurityException {
        Drive driveService = getDriveService();
        driveService.files().delete(id).execute();
    }

    public boolean fiLeExists(String fileId) throws GeneralSecurityException, IOException {
        Drive driveService = getDriveService();
        try{
            driveService.files().get(fileId).execute();
            return true;
        } catch (com.google.api.client.googleapis.json.GoogleJsonResponseException e) {
            if(e.getStatusCode() == 404){
                return false;
            }
            throw e;
        }
    }

    public String getCoursesFolderId(){
        return COURSES_FOLDER_ID;
    }

    public String createFolder(String folderName, String parentFolderId) throws GeneralSecurityException, IOException {
        Drive driveService = getDriveService();

        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        fileMetadata.setParents(Collections.singletonList(parentFolderId));

        File folder = driveService.files().create(fileMetadata)
                .setFields("id, webViewLink")
                .execute();

        return folder.getId();
    }

    public void renameFolder(String folderId, String newName) throws GeneralSecurityException, IOException {
        Drive driveService = getDriveService();

        File fileMetadata = new File();
        fileMetadata.setName(newName);

        File updatedFolder = driveService.files().update(folderId, fileMetadata)
                .setFields("id, name")
                .execute();
    }

    public String uploadBannerFile(MultipartFile file) throws IOException, GeneralSecurityException {
        Drive driveService = getDriveService();
        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setParents(Collections.singletonList(SLIDE_FOLDER_ID));

        InputStreamContent content = new InputStreamContent(file.getContentType(), file.getInputStream());
        File uploadedFile = driveService.files().create(fileMetadata, content)
                .setFields("id, webViewLink")
                .execute();

        Permission permission = new Permission();
        permission.setType("anyone");
        permission.setRole("reader");
        driveService.permissions().create(uploadedFile.getId(), permission).execute();

        return "https://lh3.googleusercontent.com/d/" + uploadedFile.getId();
    }

    public String getGoogleDriveFileId(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return null;
        }
        if (imageUrl.contains("/d/")) {
            return imageUrl.split("/d/")[1].split("/")[0];
        }
        return null;
    }

    public String uploadPostImage(MultipartFile file) throws IOException, GeneralSecurityException {
        Drive driveService = getDriveService();
        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setParents(Collections.singletonList(POST_IMAGE_FOLDER_ID));

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

}