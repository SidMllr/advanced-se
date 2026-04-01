package com.fitnessstudio.planner.domain.model.session;

import java.util.Objects;
import java.util.UUID;

public record WaitlistEntryId(UUID value) {

    public WaitlistEntryId {
        Objects.requireNonNull(value, "WaitlistEntryId value must not be null");
    }

    public static WaitlistEntryId generate() {
        return new WaitlistEntryId(UUID.randomUUID());
    }

    public static WaitlistEntryId of(UUID value) {
        return new WaitlistEntryId(value);
    }

    public static WaitlistEntryId of(String value) {
        return new WaitlistEntryId(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
