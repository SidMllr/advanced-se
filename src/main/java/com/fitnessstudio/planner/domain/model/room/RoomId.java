package com.fitnessstudio.planner.domain.model.room;

import java.util.Objects;
import java.util.UUID;

public record RoomId(UUID value) {

    public RoomId {
        Objects.requireNonNull(value, "RoomId value must not be null");
    }

    public static RoomId generate() {
        return new RoomId(UUID.randomUUID());
    }

    public static RoomId of(UUID value) {
        return new RoomId(value);
    }

    public static RoomId of(String value) {
        return new RoomId(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
