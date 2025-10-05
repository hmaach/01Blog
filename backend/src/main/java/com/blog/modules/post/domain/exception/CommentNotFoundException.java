package com.blog.modules.post.domain.exception;

import org.springframework.http.HttpStatus;

import com.blog.shared.infrastructure.exception.BaseException;

public class CommentNotFoundException extends BaseException {
    public CommentNotFoundException(String id) {
        super("COMMENT_NOT_FOUND", "Comment doesn't exist: " + id, HttpStatus.NOT_FOUND);
    }
}
