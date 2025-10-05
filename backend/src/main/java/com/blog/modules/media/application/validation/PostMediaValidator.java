package com.blog.modules.media.application.validation;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PostMediaValidator extends MediaValidator {

    private static final long MAX_POST_MEDIA_SIZE = 10 * 1024 * 1024; // 10 MB

    public void validate(MultipartFile file) {
        validateNotEmpty(file);
        validateSize(file, MAX_POST_MEDIA_SIZE);
        validateContentType(file, merge(IMAGE_TYPES, VIDEO_TYPES));
    }
}
