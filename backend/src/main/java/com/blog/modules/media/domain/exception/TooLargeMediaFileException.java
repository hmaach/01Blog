package com.blog.modules.media.domain.exception;

public class TooLargeMediaFileException extends RuntimeException {

    public TooLargeMediaFileException(long maxSize) {
        super("File size must not exceed " + (maxSize / (1024 * 1024)) + " MB");
    }
}
