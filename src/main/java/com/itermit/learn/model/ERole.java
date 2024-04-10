package com.itermit.learn.model;

public enum ERole {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String name;

    ERole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
