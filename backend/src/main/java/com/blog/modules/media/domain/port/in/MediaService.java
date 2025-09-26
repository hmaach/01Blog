package com.blog.modules.media.domain.port.in;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.io.IOException;

public interface MediaService {

    UUID uploadAvatar(UUID userId, MultipartFile file) throws IOException, IllegalStateException, java.io.IOException;

    void deleteFile(String url) throws IOException, java.io.IOException;
}
