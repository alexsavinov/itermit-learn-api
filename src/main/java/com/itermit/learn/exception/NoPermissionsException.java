package com.itermit.learn.exception;

public class NoPermissionsException extends RuntimeException {

    public NoPermissionsException(String message) {
        super(message);
    }
}
