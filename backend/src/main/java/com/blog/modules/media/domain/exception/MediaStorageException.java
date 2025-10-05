package com.blog.modules.media.domain.exception;

import org.springframework.http.HttpStatus;

import com.blog.shared.infrastructure.exception.BaseException;

public class MediaStorageException extends BaseException {

    public MediaStorageException(String msg) {
        super("MEDIA_STORAGE_ERROR", "Error while storing media: " + msg, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
