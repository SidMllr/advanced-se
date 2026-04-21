package com.fitnessstudio.planner.domain.service;

import com.fitnessstudio.planner.domain.model.course.CourseId;
import com.fitnessstudio.planner.domain.model.course.MaxParticipants;
import com.fitnessstudio.planner.domain.model.room.RoomId;
import com.fitnessstudio.planner.domain.model.session.SessionId;
import com.fitnessstudio.planner.domain.model.session.TimeSlot;
import com.fitnessstudio.planner.domain.model.session.TrainingSession;
import com.fitnessstudio.planner.domain.model.trainer.TrainerId;
import com.fitnessstudio.planner.domain.repository.TrainingSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SchedulingServiceTest {

    @Mock
    private TrainingSessionRepository sessionRepository;

    private SchedulingService schedulingService;

    private final TrainerId trainerId = TrainerId.generate();
    private final RoomId roomId = RoomId.generate();

    @BeforeEach
    void setUp() {
        schedulingService = new SchedulingService(sessionRepository);
    }

    private TrainingSession sessionWithTimeSlot(TrainerId trainer, RoomId room, TimeSlot slot) {
        return TrainingSession.builder()
                .id(SessionId.generate())
                .courseId(CourseId.generate())
                .trainerId(trainer)
                .roomId(room)
                .timeSlot(slot)
                .maxParticipants(MaxParticipants.of(10))
                .build();
    }

    @Test
    @DisplayName("should detect trainer conflict for overlapping time slot")
    void shouldDetectTrainerConflict() {
        TimeSlot existing = TimeSlot.of(DayOfWeek.MONDAY, LocalTime.of(10, 0), 60);
        TimeSlot conflicting = TimeSlot.of(DayOfWeek.MONDAY, LocalTime.of(10, 30), 60);

        when(sessionRepository.findByTrainerId(trainerId))
                .thenReturn(List.of(sessionWithTimeSlot(trainerId, roomId, existing)));

        boolean hasConflict = schedulingService.hasTrainerConflict(trainerId, conflicting);

        assertThat(hasConflict).isTrue();
    }

    @Test
    @DisplayName("should not detect trainer conflict for non-overlapping time slot")
    void shouldNotDetectTrainerConflictForNonOverlappingSlot() {
        TimeSlot existing = TimeSlot.of(DayOfWeek.MONDAY, LocalTime.of(9, 0), 60);
        TimeSlot proposed = TimeSlot.of(DayOfWeek.MONDAY, LocalTime.of(10, 0), 60);

        when(sessionRepository.findByTrainerId(trainerId))
                .thenReturn(List.of(sessionWithTimeSlot(trainerId, roomId, existing)));

        boolean hasConflict = schedulingService.hasTrainerConflict(trainerId, proposed);

        assertThat(hasConflict).isFalse();
    }

    @Test
    @DisplayName("should detect room conflict for overlapping time slot")
    void shouldDetectRoomConflict() {
        TimeSlot existing = TimeSlot.of(DayOfWeek.TUESDAY, LocalTime.of(14, 0), 90);
        TimeSlot conflicting = TimeSlot.of(DayOfWeek.TUESDAY, LocalTime.of(15, 0), 60);

        when(sessionRepository.findByRoomId(roomId))
                .thenReturn(List.of(sessionWithTimeSlot(trainerId, roomId, existing)));

        boolean hasConflict = schedulingService.hasRoomConflict(roomId, conflicting);

        assertThat(hasConflict).isTrue();
    }

    @Test
    @DisplayName("should not detect room conflict on a different day")
    void shouldNotDetectRoomConflictOnDifferentDay() {
        TimeSlot monday = TimeSlot.of(DayOfWeek.MONDAY, LocalTime.of(10, 0), 60);
        TimeSlot tuesday = TimeSlot.of(DayOfWeek.TUESDAY, LocalTime.of(10, 0), 60);

        when(sessionRepository.findByRoomId(roomId))
                .thenReturn(List.of(sessionWithTimeSlot(trainerId, roomId, monday)));

        boolean hasConflict = schedulingService.hasRoomConflict(roomId, tuesday);

        assertThat(hasConflict).isFalse();
    }

    @Test
    @DisplayName("assertNoConflict should throw for trainer conflict")
    void assertNoConflictShouldThrowForTrainerConflict() {
        TimeSlot existing = TimeSlot.of(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), 60);
        TimeSlot conflicting = TimeSlot.of(DayOfWeek.WEDNESDAY, LocalTime.of(10, 30), 60);

        when(sessionRepository.findByTrainerId(trainerId))
                .thenReturn(List.of(sessionWithTimeSlot(trainerId, roomId, existing)));

        assertThatThrownBy(() -> schedulingService.assertNoConflict(trainerId, roomId, conflicting))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Trainer");
    }

    @Test
    @DisplayName("assertNoConflict should pass when no conflicts exist")
    void assertNoConflictShouldPassWithNoConflicts() {
        TimeSlot proposed = TimeSlot.of(DayOfWeek.FRIDAY, LocalTime.of(18, 0), 60);

        when(sessionRepository.findByTrainerId(trainerId)).thenReturn(List.of());
        when(sessionRepository.findByRoomId(roomId)).thenReturn(List.of());


        schedulingService.assertNoConflict(trainerId, roomId, proposed);
    }
}
