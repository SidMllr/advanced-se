package com.fitnessstudio.planner.domain.model.course;

public record MaxParticipants(int value) {

    public static final int MIN_VALUE = 1;
    public static final int MAX_VALUE = 500;

    public MaxParticipants {
        if (value < MIN_VALUE) {
            throw new IllegalArgumentException(
                    "MaxParticipants must be at least " + MIN_VALUE);
        }
        if (value > MAX_VALUE) {
            throw new IllegalArgumentException(
                    "MaxParticipants must not exced " + MAX_VALUE);
        }
    }

    public static MaxParticipants of(int value) {
        return new MaxParticipants(value);
    }

    public boolean isExceededBy(int count) {
        return count >= value;
    }
}
