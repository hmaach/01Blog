package com.blog.modules.user.infrastructure.adapter.in.web.dto;

import org.springframework.web.multipart.MultipartFile;

public record UploadAvatarCommand(
        MultipartFile file
        ) {

}
