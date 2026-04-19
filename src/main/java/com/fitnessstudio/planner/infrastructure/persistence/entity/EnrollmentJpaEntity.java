package com.fitnessstudio.planner.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "enrollments")
public class EnrollmentJpaEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private TrainingSessionJpaEntity session;

    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    @Column(name = "enrolled_at", nullable = false)
    private LocalDateTime enrolledAt;

    protected EnrollmentJpaEntity() {}

    public EnrollmentJpaEntity(UUID id, TrainingSessionJpaEntity session,
                                UUID memberId, LocalDateTime enrolledAt) {
        this.id = id;
        this.session = session;
        this.memberId = memberId;
        this.enrolledAt = enrolledAt;
    }

    public UUID getId() { return id; }
    public TrainingSessionJpaEntity getSession() { return session; }
    public UUID getMemberId() { return memberId; }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }
}
