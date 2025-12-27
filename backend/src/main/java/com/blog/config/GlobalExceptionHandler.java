package com.blog.config;

import java.nio.file.NoSuchFileException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.blog.shared.infrastructure.adapter.in.web.dto.ErrorResponse;
import com.blog.shared.infrastructure.exception.BaseException;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

        // Custom exceptions
        @ExceptionHandler(BaseException.class)
        public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
                return ResponseEntity
                                .status(ex.getStatus())
                                .body(new ErrorResponse(ex.getCode(), ex.getMessage()));
        }

        // Security exceptions
        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(new ErrorResponse("ACCESS_DENIED",
                                                "You do not have permission to access this resource."));
        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ErrorResponse("INVALID_CREDENTIALS", "Invalid email or password provided."));
        }

        // Validation & Parsing exceptions
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
                String errors = ex.getBindingResult().getFieldErrors().stream()
                                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                                .collect(Collectors.joining(", "));
                return ResponseEntity.badRequest()
                                .body(new ErrorResponse("VALIDATION_FAILED", "Validation failed: " + errors));
        }

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
                if (ex.getRequiredType() == UUID.class) {
                        return ResponseEntity.badRequest()
                                        .body(Map.of("error", "Invalid UUID format: " + ex.getValue()));
                }
                return ResponseEntity.badRequest()
                                .body(Map.of("error", "Invalid parameter: " + ex.getName()));
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ErrorResponse("VALIDATION_FAILED",
                                                "Constraint validation failed for input parameters."));
        }

        @ExceptionHandler(DisabledException.class)
        public ResponseEntity<ErrorResponse> handleUserDisabledException(DisabledException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ErrorResponse("USER_DISABLED", "This account is currently disabled."));
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<Map<String, String>> handleInvalidJson(HttpMessageNotReadableException ex) {
                return ResponseEntity.badRequest()
                                .body(Map.of("error", "Invalid JSON format: " + ex.getMessage()));
        }

        @ExceptionHandler(IOException.class)
        public ResponseEntity<Map<String, String>> handleInvalidJson(IOException ex) {
                return ResponseEntity.badRequest()
                                .body(Map.of("error", "Invalid JSON format: " + ex.getMessage()));
        }

        @ExceptionHandler(MissingServletRequestParameterException.class)
        public ResponseEntity<ErrorResponse> handleMissingRequestParameter(MissingServletRequestParameterException ex) {
                return ResponseEntity.badRequest()
                                .body(new ErrorResponse("MISSING_PARAMETER",
                                                "Required parameter is missing: " + ex.getParameterName()));
        }

        @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
        public ResponseEntity<ErrorResponse> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ErrorResponse("MEDIA_TYPE_NOT_SUPPORTED",
                                                "The media type provided is not supported."));
        }

        @ExceptionHandler(MissingServletRequestPartException.class)
        public ResponseEntity<ErrorResponse> handleMissingFilePart(MissingServletRequestPartException ex) {
                return ResponseEntity.badRequest()
                                .body(new ErrorResponse("MISSING_FILE_PART",
                                                "Required file part is missing from the request."));
        }

        @ExceptionHandler(JwtException.class)
        public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex) {
                return ResponseEntity.badRequest()
                                .body(new ErrorResponse("JWT_ERROR",
                                                "Error processing the JWT token: " + ex.getMessage()));
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
                return ResponseEntity.badRequest()
                                .body(new ErrorResponse("DATA_INTEGRITY_ERROR",
                                                "Data integrity violation occurred: " + ex.getMessage()));
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
                return ResponseEntity.badRequest()
                                .body(new ErrorResponse("INVALID_ARGUMENT",
                                                "Invalid argument provided: " + ex.getMessage()));
        }

        // Request-related exceptions
        @ExceptionHandler(NoHandlerFoundException.class)
        public ResponseEntity<ErrorResponse> handleEndpointNotFound(NoHandlerFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ErrorResponse("NOT_FOUND", "Endpoint not found: " + ex.getRequestURL()));
        }

        @ExceptionHandler(NoResourceFoundException.class)
        public ResponseEntity<ErrorResponse> handleResourceNotFound(NoResourceFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ErrorResponse("RESOURCE_NOT_FOUND",
                                                "Requested resource could not be found."));
        }

        @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
        public ResponseEntity<ErrorResponse> handleMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                                .body(new ErrorResponse("MEDIA_TYPE_NOT_ACCEPTABLE",
                                                "The media type requested is not acceptable."));
        }

        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(new ErrorResponse("UNAUTHORIZED", "Authentication failed: " + ex.getMessage()));
        }

        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
                String message = String.format("HTTP method %s is not supported for this endpoint.", ex.getMethod());
                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                                .body(new ErrorResponse("METHOD_NOT_ALLOWED", message));
        }

        @ExceptionHandler(MissingPathVariableException.class)
        public ResponseEntity<ErrorResponse> handleMissingPathVariable(MissingPathVariableException ex) {
                return ResponseEntity.badRequest()
                                .body(new ErrorResponse("MISSING_PATH_VARIABLE",
                                                "A required path variable is missing."));
        }

        @ExceptionHandler(MultipartException.class)
        public ResponseEntity<Map<String, String>> handleMultipartException(MultipartException ex) {
                return ResponseEntity.badRequest()
                                .body(Map.of("error", "Multipart request error: " + ex.getMessage()));
        }

        @ExceptionHandler(MaxUploadSizeExceededException.class)
        public ResponseEntity<Map<String, String>> handleFileSizeExceeded(MaxUploadSizeExceededException ex) {
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                                .body(Map.of("error", "FILE_TOO_LARGE", "message",
                                                "The uploaded file exceeds the allowed size limit."));
        }

        @ExceptionHandler(NoSuchFileException.class)
        public ResponseEntity<ErrorResponse> handleFileNotFound(NoSuchFileException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ErrorResponse("FILE_NOT_FOUND", "The requested file could not be found."));
        }

        // Fallback
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ErrorResponse("INTERNAL_SERVER_ERROR",
                                                "An unexpected error occurred. Please try again later."));
        }
}
