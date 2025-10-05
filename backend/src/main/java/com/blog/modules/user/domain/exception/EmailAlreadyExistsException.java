package com.blog.modules.user.domain.exception;

import org.springframework.http.HttpStatus;

import com.blog.shared.infrastructure.exception.BaseException;

public class EmailAlreadyExistsException extends BaseException {

    public EmailAlreadyExistsException(String email) {
        super("EMAIL_EXISTS", "Email already exists: " + email, HttpStatus.CONFLICT);
    }
}
