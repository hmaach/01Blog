package com.blog.modules.user.domain.exception;

import org.springframework.http.HttpStatus;

import com.blog.shared.infrastructure.exception.BaseException;

public class UsernameAlreadyExistsException extends BaseException {

    public UsernameAlreadyExistsException(String username) {
        super("USERNAME_EXISTS", "Username already exists: " + username, HttpStatus.CONFLICT);
    }
}
