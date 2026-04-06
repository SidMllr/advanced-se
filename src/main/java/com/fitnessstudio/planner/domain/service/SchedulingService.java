package com.fitnessstudio.planner.domain.service;

import com.fitnessstudio.planner.domain.model.room.RoomId;
import com.fitnessstudio.planner.domain.model.session.TimeSlot;
import com.fitnessstudio.planner.domain.model.session.TrainingSession;
import com.fitnessstudio.planner.domain.model.trainer.TrainerId;
import com.fitnessstudio.planner.domain.repository.TrainingSessionRepository;

import java.util.List;
import java.util.Objects;

public class SchedulingService {

    private final TrainingSessionRepository sessionRepository;

    public SchedulingService(TrainingSessionRepository sessionRepository) {
        this.sessionRepository = Objects.requireNonNull(sessionRepository);
    }



    public boolean hasTrainerConflict(TrainerId trainerId, TimeSlot timeSlot) {
        Objects.requireNonNull(trainerId, "TrainerId must not be null");
        Objects.requireNonNull(timeSlot, "TimeSlot must not be null");

        List<TrainingSession> sessions = sessionRepository.findByTrainerId(trainerId);
        return sessions.stream()
                .anyMatch(s -> s.getTimeSlot().overlaps(timeSlot));
    }



    public boolean hasRoomConflict(RoomId roomId, TimeSlot timeSlot) {
        Objects.requireNonNull(roomId, "RoomId must not be null");
        Objects.requireNonNull(timeSlot, "TimeSlot must not be null");

        List<TrainingSession> sessions = sessionRepository.findByRoomId(roomId);
        return sessions.stream()
                .anyMatch(s -> s.getTimeSlot().overlaps(timeSlot));
    }



    public void assertNoConflict(TrainerId trainerId, RoomId roomId, TimeSlot timeSlot) {
        if (hasTrainerConflict(trainerId, timeSlot)) {
            throw new IllegalStateException(
                    "Trainer " + trainerId + " already has a session at " + timeSlot);
        }
        if (hasRoomConflict(roomId, timeSlot)) {
            throw new IllegalStateException(
                    "Room " + roomId + " is already booked at " + timeSlot);
        }
    }
}
