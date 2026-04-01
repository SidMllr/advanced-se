package com.fitnessstudio.planner.domain.model.course;

import java.util.Objects;
import java.util.UUID;

public record CourseId(UUID value) {

    public CourseId {
        Objects.requireNonNull(value, "CourseId value must not be null");
    }

    public static CourseId generate() {
        return new CourseId(UUID.randomUUID());
    }

    public static CourseId of(UUID value) {
        return new CourseId(value);
    }

    public static CourseId of(String value) {
        return new CourseId(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
