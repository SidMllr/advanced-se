package com.fitnessstudio.planner.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "sports_courses")
public class SportsCourseJpaEntity {

    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "max_participants", nullable = false)
    private int maxParticipants;

    @Column(nullable = false)
    private boolean active;

    protected SportsCourseJpaEntity() {}

    public SportsCourseJpaEntity(UUID id, String name, String category,
                                  String description, int maxParticipants, boolean active) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.active = active;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public int getMaxParticipants() { return maxParticipants; }
    public boolean isActive() { return active; }

    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setMaxParticipants(int maxParticipants) { this.maxParticipants = maxParticipants; }
    public void setActive(boolean active) { this.active = active; }
}
