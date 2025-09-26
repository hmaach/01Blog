package com.blog.modules.media.infrastructure.adapter.in.web;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.modules.media.domain.port.in.MediaService;
import com.blog.shared.infrastructure.exception.ApiException;
import com.blog.shared.infrastructure.security.JwtService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService mediaService;
    private final JwtService jwtService;

    public MediaController(JwtService jwtService, MediaService mediaService) {
        this.mediaService = mediaService;
        this.jwtService = jwtService;
    }

    @PostMapping("/avatar")
    public ResponseEntity<UUID> uploadAvatar(
            HttpServletRequest request,
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            throw new ApiException(
                    "EMPTY_AVATAR",
                    "Avatar cannot be empty",
                    HttpStatus.BAD_REQUEST
            );
        }

        // Max size = 2 MB
        long maxSize = 2 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            throw new ApiException(
                    "BIG_SIZED_AVATAR",
                    "Avatar size must not exceed 2 MB",
                    HttpStatus.BAD_REQUEST
            );
        }

        // Validate content type
        String contentType = file.getContentType();
        if (contentType == null
                || !(contentType.equalsIgnoreCase("image/jpeg")
                || contentType.equalsIgnoreCase("image/png")
                || contentType.equalsIgnoreCase("image/jpg"))) {
            throw new ApiException(
                    "INVALID_MEDIA_TYPE",
                    "Only JPG, JPEG, and PNG images are allowed",
                    HttpStatus.BAD_REQUEST
            );
        }

        UUID userId = UUID.fromString(jwtService.extractUserIdFromRequest(request));

        try {
            UUID mediaId = mediaService.uploadAvatar(userId, file);
            return ResponseEntity.ok(mediaId);
        } catch (IOException | java.io.IOException | IllegalStateException e) {
            throw new ApiException(
                    "INTERNAL_SERVER_ERROR",
                    "Failed to upload avatar: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
