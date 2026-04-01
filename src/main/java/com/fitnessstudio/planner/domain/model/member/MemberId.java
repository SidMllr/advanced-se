package com.fitnessstudio.planner.domain.model.member;

import java.util.Objects;
import java.util.UUID;

public record MemberId(UUID value) {

    public MemberId {
        Objects.requireNonNull(value, "MemberId value must not be null");
    }

    public static MemberId generate() {
        return new MemberId(UUID.randomUUID());
    }

    public static MemberId of(UUID value) {
        return new MemberId(value);
    }

    public static MemberId of(String value) {
        return new MemberId(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
