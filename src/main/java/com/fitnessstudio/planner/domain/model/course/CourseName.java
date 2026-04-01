package com.fitnessstudio.planner.domain.model.course;

import java.util.Objects;

public record CourseName(String value) {

    public static final int MAX_LENGTH = 100;

    public CourseName {
        Objects.requireNonNull(value, "CourseName must not be null");
        value = value.trim();
        if (value.isBlank()) {
            throw new IllegalArgumentException("CourseName must not be blank");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "CourseName must not exceed " + MAX_LENGTH + " characters");
        }
    }
    public static CourseName of(String value) {
        return new CourseName(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
