package com.letsplay.application.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String email) {
        super("Product dousn't exist: " + email);
    }
}
