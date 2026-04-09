package com.fitnessstudio.planner.application.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String id) {
        super("Sports course not found: " + id);
    }
}
