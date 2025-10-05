package com.blog.shared.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException {

    public ConflictException(String message) {
        super("CONFLICT", message, HttpStatus.CONFLICT);
    }

    public ConflictException() {
        this("A conflict occurred while processing your request");
    }
}
