package org.example.springfsexam.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    private final String uploadDir = "/Users/abc/SpringFsExam/src/main/java/org/example/springfsexam/uploads/";

    public FileStorageService() {
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.write(filePath, file.getBytes());
        return fileName; // Return only the filename instead of full path
    }

    public void deleteFile(String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            System.out.println("filename :"+fileName);
            Path filePath = Paths.get(uploadDir + fileName);
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Error deleting file: " + fileName, e);
            }
        }
    }
}
