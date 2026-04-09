package com.fitnessstudio.planner.application.exception;

public class TrainerNotFoundException extends RuntimeException {
    public TrainerNotFoundException(String id) {
        super("Trainer not found: " + id);
    }
}
