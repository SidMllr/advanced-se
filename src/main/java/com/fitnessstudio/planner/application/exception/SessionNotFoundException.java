package com.fitnessstudio.planner.application.exception;

public class SessionNotFoundException extends RuntimeException {
    public SessionNotFoundException(String id) {
        super("Training session not found: " + id);
    }
}
