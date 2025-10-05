package com.blog.modules.media.application.validation;

import java.util.Arrays;
import java.util.List;

import com.blog.modules.media.domain.exception.*;
import org.springframework.web.multipart.MultipartFile;

public abstract class MediaValidator {

    protected static final List<String> IMAGE_TYPES = List.of("image/jpeg", "image/png", "image/jpg");
    protected static final List<String> VIDEO_TYPES = List.of("video/mp4", "video/quicktime", "video/x-msvideo");

    protected void validateNotEmpty(MultipartFile file) {
        if (file.isEmpty()) throw new EmptyMediaFileException();
    }

    protected void validateSize(MultipartFile file, long maxSize) {
        if (file.getSize() > maxSize) throw new TooLargeMediaFileException(maxSize);
    }

    protected void validateContentType(MultipartFile file, List<String> allowedTypes) {
        String contentType = file.getContentType();
        if (contentType == null || allowedTypes.stream().noneMatch(type -> type.equalsIgnoreCase(contentType))) {
            throw new InvalidMediaTypeException(allowedTypes);
        }
    }

    protected List<String> merge(List<String>... lists) {
        return Arrays.stream(lists).flatMap(List::stream).toList();
    }
}
