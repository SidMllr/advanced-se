package com.fitnessstudio.planner.domain.model.session;

import com.fitnessstudio.planner.domain.model.course.CourseId;
import com.fitnessstudio.planner.domain.model.course.MaxParticipants;
import com.fitnessstudio.planner.domain.model.member.MemberId;
import com.fitnessstudio.planner.domain.model.room.RoomId;
import com.fitnessstudio.planner.domain.model.trainer.TrainerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TrainingSessionTest {

    private TrainingSession session;
    private final MemberId memberA = MemberId.generate();
    private final MemberId memberB = MemberId.generate();
    private final MemberId memberC = MemberId.generate();

    @BeforeEach
    void setUp() {
        session = TrainingSession.builder()
                .id(SessionId.generate())
                .courseId(CourseId.generate())
                .trainerId(TrainerId.generate())
                .roomId(RoomId.generate())
                .timeSlot(TimeSlot.of(DayOfWeek.MONDAY, LocalTime.of(10, 0), 60))
                .maxParticipants(MaxParticipants.of(2))
                .build();
    }

    @Test
    @DisplayName("should directly enroll member when capacity is available")
    void shouldEnrollMemberDirectlyWhenCapacityAvailable() {
        boolean enrolled = session.enroll(memberA);

        assertThat(enrolled).isTrue();
        assertThat(session.isEnrolled(memberA)).isTrue();
        assertThat(session.currentEnrollmentCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("should place member on waitlist when session is full")
    void shouldPlaceMemberOnWaitlistWhenFull() {
        session.enroll(memberA);
        session.enroll(memberB);

        boolean enrolled = session.enroll(memberC);

        assertThat(enrolled).isFalse();
        assertThat(session.isOnWaitlist(memberC)).isTrue();
        assertThat(session.waitlistSize()).isEqualTo(1);
    }

    @Test
    @DisplayName("should not allow a member to enroll twice")
    void shouldPreventDuplicateEnrollment() {
        session.enroll(memberA);

        assertThatThrownBy(() -> session.enroll(memberA))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already enrolled");
    }

    @Test
    @DisplayName("should not allow a member already on waitlist to enroll again")
    void shouldPreventDuplicateWaitlistEntry() {
        session.enroll(memberA);
        session.enroll(memberB);
        session.enroll(memberC);

        assertThatThrownBy(() -> session.enroll(memberC))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already on the waitlist");
    }

    @Test
    @DisplayName("should promote first waitlist member when enrollment is cancelled")
    void shouldPromoteFromWaitlistOnCancellation() {
        session.enroll(memberA);
        session.enroll(memberB);
        session.enroll(memberC);

        session.cancelEnrollment(memberA);

        assertThat(session.isEnrolled(memberC)).isTrue();
        assertThat(session.waitlistSize()).isEqualTo(0);
        assertThat(session.currentEnrollmentCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("cancelling a non-existent enrollment should throw")
    void shouldThrowWhenCancellingNonExistentEnrollment() {
        assertThatThrownBy(() -> session.cancelEnrollment(memberA))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("no enrollment");
    }

    @Test
    @DisplayName("should correctly remove a member from the waitlist")
    void shouldRemoveMemberFromWaitlist() {
        session.enroll(memberA);
        session.enroll(memberB);
        session.enroll(memberC);

        session.cancelWaitlistEntry(memberC);

        assertThat(session.isOnWaitlist(memberC)).isFalse();
        assertThat(session.waitlistSize()).isEqualTo(0);
    }

    @Test
    @DisplayName("Builder should create TrainingSession with all required fields")
    void builderShouldCreateSessionWithAllFields() {
        SessionId id = SessionId.generate();
        CourseId courseId = CourseId.generate();
        TrainerId trainerId = TrainerId.generate();
        RoomId roomId = RoomId.generate();
        TimeSlot timeSlot = TimeSlot.of(DayOfWeek.FRIDAY, LocalTime.of(18, 0), 90);
        MaxParticipants max = MaxParticipants.of(10);

        TrainingSession built = TrainingSession.builder()
                .id(id)
                .courseId(courseId)
                .trainerId(trainerId)
                .roomId(roomId)
                .timeSlot(timeSlot)
                .maxParticipants(max)
                .build();

        assertThat(built.getId()).isEqualTo(id);
        assertThat(built.getCourseId()).isEqualTo(courseId);
        assertThat(built.getTrainerId()).isEqualTo(trainerId);
        assertThat(built.getRoomId()).isEqualTo(roomId);
        assertThat(built.getTimeSlot()).isEqualTo(timeSlot);
        assertThat(built.getMaxParticipants()).isEqualTo(max);
    }

    @Test
    @DisplayName("Builder should throw when a required field is missing")
    void builderShouldThrowForMissingRequiredField() {
        assertThatThrownBy(() -> TrainingSession.builder()
                .id(SessionId.generate())
                .courseId(CourseId.generate())

                .roomId(RoomId.generate())
                .timeSlot(TimeSlot.of(DayOfWeek.MONDAY, LocalTime.of(10, 0), 60))
                .maxParticipants(MaxParticipants.of(5))
                .build())
                .isInstanceOf(NullPointerException.class);
    }
}
