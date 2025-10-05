package com.blog.shared.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends BaseException {

    public InternalServerErrorException() {
        super(
                "INTERNAL_SERVER_ERROR",
                "Something went wrong. Please try again later.",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    public InternalServerErrorException(String message) {
        super(
                "INTERNAL_SERVER_ERROR",
                message,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
