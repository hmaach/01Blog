package com.blog.modules.post.domain.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String email) {
        super("Post dousn't exist: " + email);
    }
}
