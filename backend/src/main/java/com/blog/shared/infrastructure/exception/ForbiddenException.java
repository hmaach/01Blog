package com.blog.shared.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {

    public ForbiddenException() {
        super(
                "FORBIDDEN",
                "You do not have permission to perform this action",
                HttpStatus.FORBIDDEN
        );
    }

    public ForbiddenException(String label) {
        super(
                "FORBIDDEN",
                label,
                HttpStatus.FORBIDDEN
        );
    }
}
