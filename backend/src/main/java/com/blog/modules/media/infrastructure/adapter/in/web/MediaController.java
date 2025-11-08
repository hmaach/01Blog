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

import com.blog.modules.media.application.validation.AvatarMediaValidator;
import com.blog.modules.media.application.validation.PostMediaValidator;
import com.blog.modules.media.domain.model.Media;
import com.blog.modules.media.domain.port.in.MediaService;
import com.blog.modules.media.domain.port.out.FileStorage;
import com.blog.modules.media.infrastructure.adapter.in.web.dto.MediaResponse;
import com.blog.modules.post.domain.port.in.PostService;
import com.blog.shared.infrastructure.exception.InternalServerErrorException;
import com.blog.shared.infrastructure.security.JwtService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService mediaService;
    private final PostService postService;
    private final JwtService jwtService;
    private final FileStorage fileStorage;
    private final PostMediaValidator postMediaValidator;
    private final AvatarMediaValidator avatarMediaValidator;

    public MediaController(
            JwtService jwtService,
            MediaService mediaService,
            PostService postService,
            PostMediaValidator postMediaValidator,
            AvatarMediaValidator avatarMediaValidator,
            FileStorage fileStorage
    ) {
        this.mediaService = mediaService;
        this.postService = postService;
        this.jwtService = jwtService;
        this.fileStorage = fileStorage;
        this.postMediaValidator = postMediaValidator;
        this.avatarMediaValidator = avatarMediaValidator;
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
            throw new InternalServerErrorException("Failed to retrieve avatar: " + e.getMessage());
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
            @RequestParam("file") MultipartFile file
    ) {

        avatarMediaValidator.validate(file);
        UUID userId = jwtService.extractUserIdFromRequest(request);

        try {
            UUID mediaId = mediaService.uploadAvatar(userId, file);
            return ResponseEntity.ok(mediaId);
        } catch (IOException | java.io.IOException | IllegalStateException e) {
            throw new InternalServerErrorException("Failed to upload avatar: " + e.getMessage());
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
            throw new InternalServerErrorException("Failed to retrieve post media: " + e.getMessage());
        }
        MediaType mediaType = mediaService.getMediaType(path);

        return ResponseEntity.ok()
                .contentLength(fileBytes.length)
                .contentType(mediaType)
                .body(new ByteArrayResource(fileBytes));
    }

    @PostMapping("/posts")
    public ResponseEntity<MediaResponse> uploadPostMedia(
            HttpServletRequest request,
            @RequestParam("file") MultipartFile file
    ) {
        postMediaValidator.validate(file);
        UUID userId = jwtService.extractUserIdFromRequest(request);

        try {
            Media media = mediaService.uploadPostMedia(userId, file);
            return ResponseEntity.ok(MediaResponse.fromDomain(media));
        } catch (IOException | java.io.IOException | IllegalStateException e) {
            throw new InternalServerErrorException("Failed to upload avatar: " + e.getMessage());
        }
    }

    // @PostMapping("/posts/{postId}")
    // public ResponseEntity<MediaResponse> uploadMediaToPost(
    //         HttpServletRequest request,
    //         @PathVariable UUID postId,
    //         @RequestParam("file") MultipartFile file
    // ) {
    //     postMediaValidator.validate(file);
    //     UUID userId = jwtService.extractUserIdFromRequest(request);

    //     try {
    //         Media media = mediaService.uploadMediaToPost(userId, postId, file);
    //         return ResponseEntity.ok(MediaResponse.fromDomain(media));
    //     } catch (IOException | java.io.IOException | IllegalStateException e) {
    //         throw new InternalServerErrorException("Failed to upload avatar: " + e.getMessage());
    //     }
    // }

    @DeleteMapping("/posts/{postId}/{mediaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMediaFromPost(
            HttpServletRequest request,
            @PathVariable UUID postId,
            @PathVariable UUID mediaId
    ) {
        UUID currentUserId = jwtService.extractUserIdFromRequest(request);

        try {
            mediaService.deleteMediaFromPost(currentUserId, postId, mediaId);
        } catch (IOException | IllegalStateException | java.io.IOException e) {
            throw new InternalServerErrorException("Failed to delete media from the post: " + e.getMessage());
        }
    }

    @DeleteMapping("/{mediaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMedia(
            HttpServletRequest request,
            @PathVariable UUID mediaId
    ) {
        UUID currentUserId = jwtService.extractUserIdFromRequest(request);

        try {
            mediaService.deleteMedia(currentUserId, mediaId);
        } catch (IOException | IllegalStateException | java.io.IOException e) {
            throw new InternalServerErrorException("Failed to delete media from the post: " + e.getMessage());
        }
    }
}
