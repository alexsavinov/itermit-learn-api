package com.itermit.learn.exception;

public class SessionFinishedException extends RuntimeException {

    public SessionFinishedException(String message) {
        super(message);
    }
}
