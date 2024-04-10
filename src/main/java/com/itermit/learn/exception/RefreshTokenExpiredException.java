package com.itermit.learn.exception;

public class RefreshTokenExpiredException extends RuntimeException {

    public RefreshTokenExpiredException(String token, String message) {
        super(message);
    }
}
