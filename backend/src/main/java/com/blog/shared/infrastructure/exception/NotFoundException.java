package com.blog.shared.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

    public NotFoundException(String message) {
        super("NOT_FOUND", message, HttpStatus.NOT_FOUND);
    }

    public NotFoundException() {
        this("The requested resource was not found");
    }
}
