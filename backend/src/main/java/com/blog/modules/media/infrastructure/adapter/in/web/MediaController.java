package com.blog.modules.media.infrastructure.adapter.in.web;

import java.util.UUID;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.modules.media.domain.port.in.MediaService;
import com.blog.modules.media.domain.port.out.FileStorage;
import com.blog.shared.infrastructure.exception.ApiException;
import com.blog.shared.infrastructure.security.JwtService;
import com.blog.shared.validation.MediaValidator;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService mediaService;
    private final JwtService jwtService;
    private final FileStorage fileStorage;
    private final MediaValidator mediaValidator;

    public MediaController(JwtService jwtService,
            MediaService mediaService, FileStorage fileStorage,
            MediaValidator mediaValidator) {
        this.mediaService = mediaService;
        this.jwtService = jwtService;
        this.fileStorage = fileStorage;
        this.mediaValidator = mediaValidator;
    }

    @GetMapping("/avatars/{filename:.+}")
    public ResponseEntity<ByteArrayResource> getAvatar(@PathVariable String filename)
            throws IOException, java.io.IOException {

        String path = "avatars/" + filename;

        byte[] fileBytes = fileStorage.retrieve(path);
        MediaType mediaType = mediaService.getMediaType(path);

        return ResponseEntity.ok()
                .contentLength(fileBytes.length)
                .contentType(mediaType)
                .body(new ByteArrayResource(fileBytes));
    }

    @PostMapping("/avatar")
    public ResponseEntity<UUID> uploadAvatar(
            HttpServletRequest request,
            @RequestParam("file") MultipartFile file) {

        mediaValidator.validateAvatar(file);

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

    @GetMapping("/posts/{filename:.+}")
    public ResponseEntity<ByteArrayResource> getPostMedia(@PathVariable String filename)
            throws IOException, java.io.IOException {

        String path = "posts/" + filename;

        byte[] fileBytes = fileStorage.retrieve(path);
        MediaType mediaType = mediaService.getMediaType(path);

        return ResponseEntity.ok()
                .contentLength(fileBytes.length)
                .contentType(mediaType)
                .body(new ByteArrayResource(fileBytes));
    }
}
