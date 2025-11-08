package com.blog.modules.media.infrastructure.exception;

import org.springframework.http.HttpStatus;

import com.blog.shared.infrastructure.exception.BaseException;

public class EmptyMediaFileException extends BaseException {

    public EmptyMediaFileException() {
        super("EMPTY_MEDIA", "Uploaded file cannot be empty", HttpStatus.BAD_REQUEST);
    }
}
