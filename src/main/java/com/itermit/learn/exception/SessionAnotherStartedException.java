package com.itermit.learn.exception;

public class SessionAnotherStartedException extends RuntimeException {

    public SessionAnotherStartedException(String message) {
        super(message);
    }
}
