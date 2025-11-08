package com.blog.modules.post.infrastructure.exception;

import org.springframework.http.HttpStatus;

import com.blog.shared.infrastructure.exception.BaseException;

public class PostNotFoundException extends BaseException {

    public PostNotFoundException(String id) {
        super("POST_NOT_FOUND", "Post doesn't exist: " + id, HttpStatus.NOT_FOUND);
    }
}
