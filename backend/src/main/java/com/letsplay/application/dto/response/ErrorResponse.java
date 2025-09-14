package com.letsplay.application.dto.response;

public record ErrorResponse(
        String error,
        String message
        ) {

}
