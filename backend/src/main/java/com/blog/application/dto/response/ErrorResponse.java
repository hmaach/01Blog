package com.blog.application.dto.response;

public record ErrorResponse(
        String error,
        String message
        ) {

}
