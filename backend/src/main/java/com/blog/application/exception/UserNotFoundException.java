package com.blog.application.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String label) {
        super("User doesn't exists: " + label);
    }
}
