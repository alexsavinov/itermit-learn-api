package com.itermit.learn.exception;

public class ResourceReferencedFromTables extends RuntimeException {

    public ResourceReferencedFromTables(String message) {
        super(message);
    }
}
