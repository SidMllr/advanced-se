package com.fitnessstudio.planner.domain.model.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimeSlotTest {

    @Test
    @DisplayName("two overlapping time slots on the same day should be detected")
    void shouldDetectOverlapOnSameDay() {
        TimeSlot slot1 = TimeSlot.of(DayOfWeek.MONDAY, LocalTime.of(10, 0), 60);
        TimeSlot slot2 = TimeSlot.of(DayOfWeek.MONDAY, LocalTime.of(10, 30), 60);

        assertThat(slot1.overlaps(slot2)).isTrue();
        assertThat(slot2.overlaps(slot1)).isTrue();
    }

    @Test
    @DisplayName("two adjacent time slots on the same day should not overlap")
    void shouldNotOverlapForAdjacentSlots() {
        TimeSlot slot1 = TimeSlot.of(DayOfWeek.MONDAY, LocalTime.of(9, 0), 60);
        TimeSlot slot2 = TimeSlot.of(DayOfWeek.MONDAY, LocalTime.of(10, 0), 60);

        assertThat(slot1.overlaps(slot2)).isFalse();
        assertThat(slot2.overlaps(slot1)).isFalse();
    }

    @Test
    @DisplayName("same slot on different days should not overlap")
    void shouldNotOverlapOnDifferentDays() {
        TimeSlot monday = TimeSlot.of(DayOfWeek.MONDAY, LocalTime.of(10, 0), 60);
        TimeSlot tuesday = TimeSlot.of(DayOfWeek.TUESDAY, LocalTime.of(10, 0), 60);

        assertThat(monday.overlaps(tuesday)).isFalse();
    }

    @Test
    @DisplayName("exactly contained slot should overlap")
    void shouldDetectOverlapForContainedSlot() {
        TimeSlot outer = TimeSlot.of(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), 120);
        TimeSlot inner = TimeSlot.of(DayOfWeek.WEDNESDAY, LocalTime.of(9, 30), 30);

        assertThat(outer.overlaps(inner)).isTrue();
    }

    @Test
    @DisplayName("should correctly compute end time")
    void shouldComputeEndTime() {
        TimeSlot slot = TimeSlot.of(DayOfWeek.FRIDAY, LocalTime.of(18, 0), 90);
        assertThat(slot.endTime()).isEqualTo(LocalTime.of(19, 30));
    }

    @Test
    @DisplayName("should reject duration below minimum")
    void shouldRejectTooShortDuration() {
        assertThatThrownBy(() -> TimeSlot.of(DayOfWeek.MONDAY, LocalTime.of(10, 0), 10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("identical time slots should overlap with themselves")
    void identicalTimeSlotsOverlap() {
        TimeSlot slot = TimeSlot.of(DayOfWeek.THURSDAY, LocalTime.of(14, 0), 60);
        assertThat(slot.overlaps(slot)).isTrue();
    }
}
