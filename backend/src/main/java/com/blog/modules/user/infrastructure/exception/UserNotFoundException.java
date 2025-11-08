package com.blog.modules.user.infrastructure.exception;

import org.springframework.http.HttpStatus;

import com.blog.shared.infrastructure.exception.BaseException;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String label) {
        super("USER_NOT_FOUND", "User doesn't exist: " + label, HttpStatus.NOT_FOUND);
    }
}
