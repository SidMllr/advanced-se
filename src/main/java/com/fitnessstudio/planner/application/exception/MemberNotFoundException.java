package com.fitnessstudio.planner.application.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String id) {
        super("Member not found: " + id);
    }
}
