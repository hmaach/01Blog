package com.letsplay.application.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String label) {
        super("User doesn't exists: " + label);
    }
}
