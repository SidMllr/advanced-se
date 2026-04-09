package com.fitnessstudio.planner.application.session.dto;

import com.fitnessstudio.planner.domain.model.session.TrainingSession;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record SessionDto(
        String id,
        String courseId,
        String trainerId,
        String roomId,
        DayOfWeek dayOfWeek,
        LocalTime startTime,
        int durationMinutes,
        int maxParticipants,
        int enrollmentCount,
        int waitlistSize
) {
    public static SessionDto from(TrainingSession session) {
        return new SessionDto(
                session.getId().toString(),
                session.getCourseId().toString(),
                session.getTrainerId().toString(),
                session.getRoomId().toString(),
                session.getTimeSlot().dayOfWeek(),
                session.getTimeSlot().startTime(),
                session.getTimeSlot().durationMinutes(),
                session.getMaxParticipants().value(),
                session.currentEnrollmentCount(),
                session.waitlistSize()
        );
    }
}
