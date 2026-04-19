package com.fitnessstudio.planner.infrastructure.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "training_sessions")
public class TrainingSessionJpaEntity {

    @Id
    private UUID id;

    @Column(name = "course_id", nullable = false)
    private UUID courseId;

    @Column(name = "trainer_id", nullable = false)
    private UUID trainerId;

    @Column(name = "room_id", nullable = false)
    private UUID roomId;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "duration_minutes", nullable = false)
    private int durationMinutes;

    @Column(name = "max_participants", nullable = false)
    private int maxParticipants;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<EnrollmentJpaEntity> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<WaitlistEntryJpaEntity> waitlistEntries = new ArrayList<>();

    protected TrainingSessionJpaEntity() {}

    public TrainingSessionJpaEntity(UUID id, UUID courseId, UUID trainerId, UUID roomId,
                                     DayOfWeek dayOfWeek, LocalTime startTime,
                                     int durationMinutes, int maxParticipants) {
        this.id = id;
        this.courseId = courseId;
        this.trainerId = trainerId;
        this.roomId = roomId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.maxParticipants = maxParticipants;
    }

    public UUID getId() { return id; }
    public UUID getCourseId() { return courseId; }
    public UUID getTrainerId() { return trainerId; }
    public UUID getRoomId() { return roomId; }
    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public LocalTime getStartTime() { return startTime; }
    public int getDurationMinutes() { return durationMinutes; }
    public int getMaxParticipants() { return maxParticipants; }
    public List<EnrollmentJpaEntity> getEnrollments() { return enrollments; }
    public List<WaitlistEntryJpaEntity> getWaitlistEntries() { return waitlistEntries; }

    public void setEnrollments(List<EnrollmentJpaEntity> enrollments) { this.enrollments = enrollments; }
    public void setWaitlistEntries(List<WaitlistEntryJpaEntity> waitlistEntries) { this.waitlistEntries = waitlistEntries; }
}
