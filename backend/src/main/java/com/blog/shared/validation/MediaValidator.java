package com.blog.shared.validation;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.blog.shared.infrastructure.exception.ApiException;

@Component
public class MediaValidator {

    private static final long MAX_AVATAR_SIZE = 2 * 1024 * 1024; // 2 MB
    private static final long MAX_POST_MEDIA_SIZE = 50 * 1024 * 1024; // 50 MB

    private static final List<String> IMAGE_TYPES = List.of("image/jpeg", "image/png", "image/jpg");
    private static final List<String> VIDEO_TYPES = List.of("video/mp4", "video/quicktime", "video/x-msvideo");

    public void validateAvatar(MultipartFile file) {
        validateNotEmpty(file);
        validateSize(file, MAX_AVATAR_SIZE);
        validateContentType(file, IMAGE_TYPES, "Only JPG, JPEG, and PNG images are allowed for avatars");
    }

    public void validatePostMedia(MultipartFile file) {
        validateNotEmpty(file);
        validateSize(file, MAX_POST_MEDIA_SIZE);
        validateContentType(file, merge(IMAGE_TYPES, VIDEO_TYPES),
                "Only JPG, JPEG, PNG, MP4, MOV, and AVI files are allowed for post media");
    }

    private void validateNotEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ApiException(
                "EMPTY_MEDIA",
                "Uploaded file cannot be empty",
                HttpStatus.BAD_REQUEST
            );
        }
    }

    private void validateSize(MultipartFile file, long maxSize) {
        if (file.getSize() > maxSize) {
            throw new ApiException(
                "FILE_TOO_LARGE",
                "File size must not exceed " + (maxSize / (1024 * 1024)) + " MB",
                HttpStatus.BAD_REQUEST
            );
        }
    }

    private void validateContentType(MultipartFile file, List<String> allowedTypes, String errorMessage) {
        String contentType = file.getContentType();
        if (contentType == null || allowedTypes.stream().noneMatch(type -> type.equalsIgnoreCase(contentType))) {
            throw new ApiException("INVALID_MEDIA_TYPE", errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    private List<String> merge(List<String>... lists) {
        return Arrays.stream(lists).flatMap(List::stream).toList();
    }
}
