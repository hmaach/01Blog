package com.blog.shared.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException {

    public UnauthorizedException() {
        super(
                "UNAUTHORIZED",
                "Authentication is required",
                HttpStatus.UNAUTHORIZED
        );
    }
}
