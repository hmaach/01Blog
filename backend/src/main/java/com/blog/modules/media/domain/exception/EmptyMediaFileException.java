package com.blog.modules.media.domain.exception;

public class EmptyMediaFileException extends RuntimeException {

    public EmptyMediaFileException() {
        super("Uploaded file cannot be empty");
    }
}
