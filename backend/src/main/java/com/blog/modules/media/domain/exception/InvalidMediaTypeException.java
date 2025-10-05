package com.blog.modules.media.domain.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.blog.shared.infrastructure.exception.BaseException;

public class InvalidMediaTypeException extends BaseException {

    public InvalidMediaTypeException(List<String> allowedMediaTypes) {
        super(
                "INVALID_MEDIA_TYPE",
                "Only " + String.join(",", allowedMediaTypes) + " files are allowed for post media",
                HttpStatus.BAD_REQUEST
        );
    }
}
