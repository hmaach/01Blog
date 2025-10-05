package com.blog.modules.media.application.validation;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AvatarMediaValidator extends MediaValidator {

    private static final long MAX_AVATAR_SIZE = 2 * 1024 * 1024; // 2 MB

    public void validate(MultipartFile file) {
        validateNotEmpty(file);
        validateSize(file, MAX_AVATAR_SIZE);
        validateContentType(file, IMAGE_TYPES);
    }
}
