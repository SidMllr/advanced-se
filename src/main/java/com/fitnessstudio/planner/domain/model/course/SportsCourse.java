package com.fitnessstudio.planner.domain.model.course;

import java.util.Objects;

public class SportsCourse {

    private final CourseId id;
    private CourseName name;
    private CourseCategory category;
    private String description;
    private MaxParticipants maxParticipants;
    private boolean active;

    public SportsCourse(CourseId id,
                        CourseName name,
                        CourseCategory category,
                        String description,
                        MaxParticipants maxParticipants) {
        this(id, name, category, description, maxParticipants, true);
    }

    public SportsCourse(CourseId id,
                        CourseName name,
                        CourseCategory category,
                        String description,
                        MaxParticipants maxParticipants,
                        boolean active) {
        this.id = Objects.requireNonNull(id, "CourseId must not be null");
        this.name = Objects.requireNonNull(name, "CourseName must not be null");
        this.category = Objects.requireNonNull(category, "CourseCategory must not be null");
        this.description = Objects.requireNonNull(description, "Description must not be null");
        this.maxParticipants = Objects.requireNonNull(maxParticipants, "MaxParticipants must not be null");
        this.active = active;
    }

    public static SportsCourse create(CourseName name,
                                      CourseCategory category,
                                      String description,
                                      MaxParticipants maxParticipants) {
        return new SportsCourse(CourseId.generate(), name, category, description, maxParticipants);
    }

    public CourseId getId() { return id; }
    public CourseName getName() { return name; }
    public CourseCategory getCategory() { return category; }
    public String getDescription() { return description; }
    public MaxParticipants getMaxParticipants() { return maxParticipants; }
    public boolean isActive() { return active; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SportsCourse that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SportsCourse{id=" + id + ", name=" + name + ", category=" + category + "}";
    }
}
