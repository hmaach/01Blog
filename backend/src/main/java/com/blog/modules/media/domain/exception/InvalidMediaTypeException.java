package com.blog.modules.media.domain.exception;

import java.util.List;

public class InvalidMediaTypeException extends RuntimeException {

    public InvalidMediaTypeException(List<String> allowedMediaTypes) {
        super("Only " + String.join(",", allowedMediaTypes) + " files are allowed for post media");
    }
}
