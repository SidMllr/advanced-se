package com.fitnessstudio.planner.domain.model.session;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

public record TimeSlot(DayOfWeek dayOfWeek, LocalTime startTime, int durationMinutes) {

    public static final int MIN_DURATION = 15;
    public static final int MAX_DURATION = 480;

    public TimeSlot {
        Objects.requireNonNull(dayOfWeek, "DayOfWeek must not be null");
        Objects.requireNonNull(startTime, "StartTime must not be null");
        if (durationMinutes < MIN_DURATION) {
            throw new IllegalArgumentException(
                    "Duration must be at least " + MIN_DURATION + " minutes");
        }
        if (durationMinutes > MAX_DURATION) {
            throw new IllegalArgumentException(
                    "Duration must not exceed " + MAX_DURATION + " minutes");
        }
    }

    public static TimeSlot of(DayOfWeek dayOfWeek, LocalTime startTime, int durationMinutes) {
        return new TimeSlot(dayOfWeek, startTime, durationMinutes);
    }



    public LocalTime endTime() {
        return startTime.plusMinutes(durationMinutes);
    }



    public boolean overlaps(TimeSlot other) {
        if (this.dayOfWeek != other.dayOfWeek) {
            return false;
        }
        LocalTime thisEnd = this.endTime();
        LocalTime otherEnd = other.endTime();
        return this.startTime.isBefore(otherEnd) && other.startTime.isBefore(thisEnd);
    }
}
