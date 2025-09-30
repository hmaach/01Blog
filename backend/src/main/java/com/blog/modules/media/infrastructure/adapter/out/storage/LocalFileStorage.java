package com.blog.modules.media.infrastructure.adapter.out.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.blog.modules.media.domain.port.out.FileStorage;

import jakarta.annotation.PostConstruct;

@Component
public class LocalFileStorage implements FileStorage {

    @Value("${app.upload.dir:uploads}")
    private String uploadBasePath;

    private Path uploadDir;

    @PostConstruct
    public void init() {
        this.uploadDir = Paths.get(System.getProperty("user.dir"), uploadBasePath);
    }

    @Override
    public String store(MultipartFile file, String relativePath) throws IOException {
        Path targetPath = Paths.get(uploadDir.toString(), relativePath);

        Files.createDirectories(targetPath.getParent());

        file.transferTo(targetPath.toFile());

        return targetPath.toString();
    }

    @Override
    public void delete(String filePath) throws IOException {
        Path targetPath = Paths.get(uploadDir.toString(), filePath);
        if (Files.exists(targetPath)) {
            Files.delete(targetPath);
        }
    }

    @Override
    public byte[] retrieve(String filePath) throws IOException {
        Path path = Paths.get(uploadDir.toString(), filePath);
        return Files.readAllBytes(path);
    }
}
