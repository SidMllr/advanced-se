package com.fitnessstudio.planner.domain.model.course;

public enum CourseCategory {

    YOGA("Yoga"),
    PILATES("Pilates"),
    HIIT("HIIT"),
    STRENGTH_TRAINING("Strength Training"),
    CARDIO("Cardio"),
    CYCLING("Cycling"),
    SWIMMING("Swimming"),
    DANCE("Dance"),
    BOXING("Boxing"),
    REHABILITATION("Rehabilitation");

    private final String displayName;

    CourseCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
