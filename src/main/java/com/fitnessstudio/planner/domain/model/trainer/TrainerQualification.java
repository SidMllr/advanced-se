package com.fitnessstudio.planner.domain.model.trainer;

import java.util.Objects;

public record TrainerQualification(String title) {

    public static final int MAX_LENGTH = 150;

    public TrainerQualification {
        Objects.requireNonNull(title, "Qualification title must not be null");
        title = title.trim();
        if (title.isBlank()) {
            throw new IllegalArgumentException("Qualification title must not be blank");
        }
        if (title.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "Qualification title must not exceed " + MAX_LENGTH + " characters");
        }
    }

    public static TrainerQualification of(String title) {
        return new TrainerQualification(title);
    }

    @Override
    public String toString() {
        return title;
    }
}
