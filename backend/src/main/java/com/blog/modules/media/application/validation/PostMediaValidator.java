package com.blog.modules.media.application.validation;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.blog.modules.media.infrastructure.exception.SizeUploadsExceededException;

@Component
public class PostMediaValidator extends MediaValidator {

    private static final long MAX_POST_MEDIA_SIZE = 10 * 1024 * 1024; // 10 MB
    private static final long MAX_USER_UPLOADS_SIZE = 100 * 1024 * 1024; // 100 MB

    public void validate(MultipartFile file) {
        validateNotEmpty(file);
        validateSize(file, MAX_POST_MEDIA_SIZE);
        validateContentType(file, merge(IMAGE_TYPES, VIDEO_TYPES));
    }

    public void validateUploadsSize(long userUploadsSize, MultipartFile file) {
        if (userUploadsSize + file.getSize() > MAX_USER_UPLOADS_SIZE) {
            throw new SizeUploadsExceededException();
        }
    }
}
