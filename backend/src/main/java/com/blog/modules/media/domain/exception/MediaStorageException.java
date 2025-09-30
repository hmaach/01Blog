package com.blog.modules.media.domain.exception;

public class MediaStorageException extends RuntimeException {

    public MediaStorageException(String msg) {
        super("Error while storing media: " + msg);
    }
}
