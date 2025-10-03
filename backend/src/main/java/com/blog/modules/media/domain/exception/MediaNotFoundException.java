package com.blog.modules.media.domain.exception;

public class MediaNotFoundException extends RuntimeException {

    public MediaNotFoundException(String id) {
        super("Media doesn't exists: " + id);
    }
}
