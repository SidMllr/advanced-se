package com.fitnessstudio.planner.domain.model.session;

import com.fitnessstudio.planner.domain.model.course.CourseId;
import com.fitnessstudio.planner.domain.model.course.MaxParticipants;
import com.fitnessstudio.planner.domain.model.member.MemberId;
import com.fitnessstudio.planner.domain.model.room.RoomId;
import com.fitnessstudio.planner.domain.model.trainer.TrainerId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TrainingSession {

    private final SessionId id;
    private final CourseId courseId;
    private final TrainerId trainerId;
    private final RoomId roomId;
    private final TimeSlot timeSlot;
    private final MaxParticipants maxParticipants;
    private final List<Enrollment> enrollments;
    private final List<WaitlistEntry> waitlist;

    private TrainingSession(Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "SessionId must not be null");
        this.courseId = Objects.requireNonNull(builder.courseId, "CourseId must not be null");
        this.trainerId = Objects.requireNonNull(builder.trainerId, "TrainerId must not be null");
        this.roomId = Objects.requireNonNull(builder.roomId, "RoomId must not be null");
        this.timeSlot = Objects.requireNonNull(builder.timeSlot, "TimeSlot must not be null");
        this.maxParticipants = Objects.requireNonNull(builder.maxParticipants, "MaxParticipants must not be null");
        this.enrollments = new ArrayList<>(Objects.requireNonNullElseGet(builder.enrollments, ArrayList::new));
        this.waitlist = new ArrayList<>(Objects.requireNonNullElseGet(builder.waitlist, ArrayList::new));
    }

    public boolean enroll(MemberId memberId) {
        Objects.requireNonNull(memberId, "MemberId must not be null");

        if (isEnrolled(memberId)) {
            throw new IllegalStateException("Member " + memberId + " is already enrolled in session " + id);
        }
        if (isOnWaitlist(memberId)) {
            throw new IllegalStateException("Member " + memberId + " is already on the waitlist for session " + id);
        }

        if (hasCapacity()) {
            enrollments.add(Enrollment.create(memberId));
            return true;
        } else {
            waitlist.add(WaitlistEntry.create(memberId));
            return false;
        }
    }

    public void cancelEnrollment(MemberId memberId) {
        Objects.requireNonNull(memberId, "MemberId must not be null");

        boolean removed = enrollments.removeIf(e -> e.getMemberId().equals(memberId));
        if (!removed) {
            throw new IllegalStateException("Member " + memberId + " has no enrollment to cancel in session " + id);
        }
        promoteFromWaitlistIfPossible();
    }

    public void cancelWaitlistEntry(MemberId memberId) {
        Objects.requireNonNull(memberId, "MemberId must not be null");

        boolean removed = waitlist.removeIf(e -> e.getMemberId().equals(memberId));
        if (!removed) {
            throw new IllegalStateException("Member " + memberId + " is not on the waitlist for session " + id);
        }
    }

    private void promoteFromWaitlistIfPossible() {
        if (waitlist.isEmpty() || !hasCapacity()) {
            return;
        }
        WaitlistEntry first = waitlist.stream()
                .min(Comparator.comparing(WaitlistEntry::getAddedAt))
                .orElseThrow();
        waitlist.remove(first);
        enrollments.add(Enrollment.create(first.getMemberId()));
    }

    public boolean isEnrolled(MemberId memberId) {
        return enrollments.stream().anyMatch(e -> e.getMemberId().equals(memberId));
    }

    public boolean isOnWaitlist(MemberId memberId) {
        return waitlist.stream().anyMatch(e -> e.getMemberId().equals(memberId));
    }

    public boolean hasCapacity() {
        return enrollments.size() < maxParticipants.value();
    }

    public int currentEnrollmentCount() {
        return enrollments.size();
    }

    public int waitlistSize() {
        return waitlist.size();
    }

    public Optional<Integer> waitlistPosition(MemberId memberId) {
        List<WaitlistEntry> sorted = waitlist.stream()
                .sorted(Comparator.comparing(WaitlistEntry::getAddedAt))
                .toList();
        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i).getMemberId().equals(memberId)) {
                return Optional.of(i + 1);
            }
        }
        return Optional.empty();
    }

    public SessionId getId() { return id; }
    public CourseId getCourseId() { return courseId; }
    public TrainerId getTrainerId() { return trainerId; }
    public RoomId getRoomId() { return roomId; }
    public TimeSlot getTimeSlot() { return timeSlot; }
    public MaxParticipants getMaxParticipants() { return maxParticipants; }

    public List<Enrollment> getEnrollments() {
        return Collections.unmodifiableList(enrollments);
    }

    public List<WaitlistEntry> getWaitlist() {
        return Collections.unmodifiableList(waitlist);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainingSession that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TrainingSession{id=" + id + ", courseId=" + courseId
                + ", timeSlot=" + timeSlot + "}";
    }

    public static class Builder {

        private SessionId id;
        private CourseId courseId;
        private TrainerId trainerId;
        private RoomId roomId;
        private TimeSlot timeSlot;
        private MaxParticipants maxParticipants;
        private List<Enrollment> enrollments;
        private List<WaitlistEntry> waitlist;

        public Builder id(SessionId id) {
            this.id = id;
            return this;
        }

        public Builder courseId(CourseId courseId) {
            this.courseId = courseId;
            return this;
        }

        public Builder trainerId(TrainerId trainerId) {
            this.trainerId = trainerId;
            return this;
        }

        public Builder roomId(RoomId roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder timeSlot(TimeSlot timeSlot) {
            this.timeSlot = timeSlot;
            return this;
        }

        public Builder maxParticipants(MaxParticipants maxParticipants) {
            this.maxParticipants = maxParticipants;
            return this;
        }

        public Builder enrollments(List<Enrollment> enrollments) {
            this.enrollments = enrollments;
            return this;
        }

        public Builder waitlist(List<WaitlistEntry> waitlist) {
            this.waitlist = waitlist;
            return this;
        }

        public TrainingSession build() {
            Objects.requireNonNull(id, "SessionId is required");
            Objects.requireNonNull(courseId, "CourseId is required");
            Objects.requireNonNull(trainerId, "TrainerId is required");
            Objects.requireNonNull(roomId, "RoomId is required");
            Objects.requireNonNull(timeSlot, "TimeSlot is required");
            Objects.requireNonNull(maxParticipants, "MaxParticipants is required");
            return new TrainingSession(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
