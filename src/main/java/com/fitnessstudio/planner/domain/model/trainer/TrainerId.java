package com.fitnessstudio.planner.domain.model.trainer;

import java.util.Objects;
import java.util.UUID;

public record TrainerId(UUID value) {

    public TrainerId {
        Objects.requireNonNull(value, "TrainerId value must not be null");
    }

    public static TrainerId generate() {
        return new TrainerId(UUID.randomUUID());
    }

    public static TrainerId of(UUID value) {
        return new TrainerId(value);
    }

    public static TrainerId of(String value) {
        return new TrainerId(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
