package com.blog.modules.user.domain.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String label) {
        super("User doesn't exists: " + label);
    }
}
