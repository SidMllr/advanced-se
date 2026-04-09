package com.fitnessstudio.planner.application.exception;

public class DuplicateCourseNameException extends RuntimeException {
    public DuplicateCourseNameException(String name) {
        super("A sports course with the name '" + name + "' already exists");
    }
}
