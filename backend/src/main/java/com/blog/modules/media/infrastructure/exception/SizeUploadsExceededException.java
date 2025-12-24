package com.blog.modules.media.infrastructure.exception;

import org.springframework.http.HttpStatus;

import com.blog.shared.infrastructure.exception.BaseException;

public class SizeUploadsExceededException extends BaseException {

    public SizeUploadsExceededException() {
        super("MEDIA_TOO_LARGE", "Your max media storage is exceeded ", HttpStatus.BAD_REQUEST);
    }
}
