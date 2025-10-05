package com.blog.modules.media.domain.exception;

import org.springframework.http.HttpStatus;

import com.blog.shared.infrastructure.exception.BaseException;

public class MediaNotFoundException extends BaseException {

    public MediaNotFoundException(String id) {
        super("MEDIA_NOT_FOUND", "Media doesn't exist: " + id, HttpStatus.NOT_FOUND);
    }
}
