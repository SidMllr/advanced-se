package com.fitnessstudio.planner.domain.model.session;

import com.fitnessstudio.planner.domain.model.member.MemberId;

import java.time.LocalDateTime;
import java.util.Objects;

public class Enrollment {

    private final EnrollmentId id;
    private final MemberId memberId;
    private final LocalDateTime enrolledAt;

    public Enrollment(EnrollmentId id, MemberId memberId, LocalDateTime enrolledAt) {
        this.id = Objects.requireNonNull(id, "EnrollmentId must not be null");
        this.memberId = Objects.requireNonNull(memberId, "MemberId must not be null");
        this.enrolledAt = Objects.requireNonNull(enrolledAt, "EnrolledAt must not be null");
    }

    public static Enrollment create(MemberId memberId) {
        return new Enrollment(EnrollmentId.generate(), memberId, LocalDateTime.now());
    }

    public EnrollmentId getId() { return id; }
    public MemberId getMemberId() { return memberId; }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enrollment that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
