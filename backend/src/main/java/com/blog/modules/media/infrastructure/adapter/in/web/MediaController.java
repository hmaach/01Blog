package com.blog.modules.media.infrastructure.adapter.in.web;

import java.nio.file.NoSuchFileException;
import java.util.UUID;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.modules.media.domain.port.in.MediaService;
import com.blog.modules.media.domain.port.out.FileStorage;
import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.domain.port.in.PostService;
import com.blog.shared.infrastructure.exception.ApiException;
import com.blog.shared.infrastructure.security.JwtService;
import com.blog.shared.validation.MediaValidator;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService mediaService;
    private final PostService postService;
    private final JwtService jwtService;
    private final FileStorage fileStorage;
    private final MediaValidator mediaValidator;

    public MediaController(JwtService jwtService,
            MediaService mediaService, FileStorage fileStorage,
            MediaValidator mediaValidator, PostService postService) {
        this.mediaService = mediaService;
        this.postService = postService;
        this.jwtService = jwtService;
        this.fileStorage = fileStorage;
        this.mediaValidator = mediaValidator;
    }

    @GetMapping("/avatars/{filename:.+}")
    public ResponseEntity<ByteArrayResource> getAvatar(@PathVariable String filename)
            throws NoSuchFileException {

        String path = "avatars/" + filename;
        byte[] fileBytes;

        try {
            fileBytes = fileStorage.retrieve(path);
        } catch (java.nio.file.NoSuchFileException e) {
            throw e;
        } catch (IOException | java.io.IOException | IllegalStateException e) {
            throw new ApiException(
                    "INTERNAL_SERVER_ERROR",
                    "Failed to retrieve avatar: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
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
            throws NoSuchFileException {

        String path = "posts/" + filename;
        byte[] fileBytes;

        try {
            fileBytes = fileStorage.retrieve(path);
        } catch (java.nio.file.NoSuchFileException e) {
            throw e;
        } catch (IOException | IllegalStateException | java.io.IOException e) {
            throw new ApiException(
                    "INTERNAL_SERVER_ERROR",
                    "Failed to retrieve post media: " + e.toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        MediaType mediaType = mediaService.getMediaType(path);

        return ResponseEntity.ok()
                .contentLength(fileBytes.length)
                .contentType(mediaType)
                .body(new ByteArrayResource(fileBytes));
    }

    @PostMapping("/posts/{postId}")
    public ResponseEntity<UUID> addMediaToPost(
            HttpServletRequest request,
            @PathVariable UUID postId,
            @RequestParam("file") MultipartFile file
    ) {
        mediaValidator.validateAvatar(file);
        Post post = postService.findById(postId);
        UUID userId = UUID.fromString(jwtService.extractUserIdFromRequest(request));

        try {
            UUID mediaId = mediaService.savePostMedia(userId, post.getId(), file);
            return ResponseEntity.ok(mediaId);
        } catch (IOException | java.io.IOException | IllegalStateException e) {
            throw new ApiException(
                    "INTERNAL_SERVER_ERROR",
                    "Failed to upload avatar: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/posts/{postId}/{mediaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMediaFromPost(
            HttpServletRequest request,
            @PathVariable UUID postId,
            @PathVariable UUID mediaId
    ) {
        UUID currentUserId = UUID.fromString(jwtService.extractUserIdFromRequest(request));

        try {
            mediaService.deleteMediaFromPost(currentUserId, postId, mediaId);
        } catch (IOException | IllegalStateException | java.io.IOException e) {
            throw new ApiException(
                    "INTERNAL_SERVER_ERROR",
                    "Failed to delete media from the post: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
