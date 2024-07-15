package com.itermit.learn.controller.advice;

import com.itermit.learn.exception.*;
import com.itermit.learn.model.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.*;


@Slf4j
@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException exception) {
        log.warn("Cannot find resource: {}", exception.getMessage());
        ErrorResponse responseBody = ErrorResponse.of(exception.getMessage(), 40001);

        return ResponseEntity.status(NOT_FOUND).body(responseBody);
    }

    @ExceptionHandler(ResourceReferencedFromTables.class)
    public ResponseEntity<ErrorResponse> handleResourceReferencedFromTables(ResourceReferencedFromTables exception) {
        log.warn("Resource exists in another tables: {}", exception.getMessage());
        ErrorResponse responseBody = ErrorResponse.of(exception.getMessage(), 40002);

        return ResponseEntity.status(CONFLICT).body(responseBody);
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException exception, WebRequest request) {
        log.warn("Refresh token not found error: {} {}", exception.getMessage(), request.getDescription(false));
        ErrorResponse responseBody = ErrorResponse.of(exception.getMessage(), 10002);

        return ResponseEntity.status(CONFLICT).body(responseBody);
    }

    @ExceptionHandler(SessionAnswerExistException.class)
    public ResponseEntity<ErrorResponse> handleSessionAnswerExist(SessionAnswerExistException exception) {
        log.warn("Answer already exists in session: {}", exception.getMessage());
        ErrorResponse responseBody = ErrorResponse.of(exception.getMessage(), 40704);

        return ResponseEntity.status(CONFLICT).body(responseBody);
    }

    @ExceptionHandler(SessionFinishedException.class)
    public ResponseEntity<ErrorResponse> handleSessionFinished(SessionFinishedException exception) {
        log.warn("Session already finished: {}", exception.getMessage());
        ErrorResponse responseBody = ErrorResponse.of(exception.getMessage(), 40705);

        return ResponseEntity.status(CONFLICT).body(responseBody);
    }

    @ExceptionHandler(SessionAnotherStartedException.class)
    public ResponseEntity<ErrorResponse> handleSessionAnotherStarted(SessionAnotherStartedException exception) {
        log.warn("Another session already started: {}", exception.getMessage());
        ErrorResponse responseBody = ErrorResponse.of(exception.getMessage(), 40706);

        return ResponseEntity.status(CONFLICT).body(responseBody);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException exception) {
        log.warn("User already exists: {}", exception.getMessage());
        ErrorResponse responseBody = ErrorResponse.of(exception.getMessage(), 40903);

        return ResponseEntity.status(CONFLICT).body(responseBody);
    }

    @ExceptionHandler(UserIncorrectException.class)
    public ResponseEntity<ErrorResponse> handleUserIdIncorrect(UserIncorrectException exception) {
        log.warn("User's id belongs to another user: {}", exception.getMessage());
        ErrorResponse responseBody = ErrorResponse.of(exception.getMessage(), 41003);

        return ResponseEntity.status(CONFLICT).body(responseBody);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenRefreshException(RefreshTokenExpiredException exception, WebRequest request) {
        log.warn("Refresh token expired error: {} {}", exception.getMessage(), request.getDescription(false));
        ErrorResponse responseBody = ErrorResponse.of(exception.getMessage(), 10001);

        return ResponseEntity.status(CONFLICT).body(responseBody);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<ErrorResponse> handleNoPermissionsException(UnauthenticatedException exception, WebRequest request) {
        log.warn("Permission denied: {} {}", exception.getMessage(), request.getDescription(false));
        ErrorResponse responseBody = ErrorResponse.of(exception.getMessage(), 10401);

        return ResponseEntity.status(UNAUTHORIZED).body(responseBody);
    }
}
