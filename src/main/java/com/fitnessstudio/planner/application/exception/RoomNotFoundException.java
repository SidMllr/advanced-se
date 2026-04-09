package com.fitnessstudio.planner.application.exception;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String id) {
        super("Room not found: " + id);
    }
}
