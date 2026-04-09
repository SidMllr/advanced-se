package com.fitnessstudio.planner.application.session.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record CreateSessionCommand(
        String courseId,
        String trainerId,
        String roomId,
        DayOfWeek dayOfWeek,
        LocalTime startTime,
        int durationMinutes
) {}
