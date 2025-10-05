package com.blog.shared.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends BaseException {

    public ResourceAlreadyExistsException(String message) {
        super("RESOURCE_ALREADY_EXISTS", message, HttpStatus.CONFLICT);
    }
}
