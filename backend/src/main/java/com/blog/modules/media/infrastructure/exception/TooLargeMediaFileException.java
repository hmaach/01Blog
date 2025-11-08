package com.blog.modules.media.infrastructure.exception;

import org.springframework.http.HttpStatus;

import com.blog.shared.infrastructure.exception.BaseException;

public class TooLargeMediaFileException extends BaseException {

    public TooLargeMediaFileException(long maxSize) {
        super("MEDIA_TOO_LARGE", "File size must not exceed " + (maxSize / (1024 * 1024)) + " MB", HttpStatus.BAD_REQUEST);
    }
}
