package com.fitnessstudio.planner.domain.repository;

import com.fitnessstudio.planner.domain.model.course.CourseId;
import com.fitnessstudio.planner.domain.model.room.RoomId;
import com.fitnessstudio.planner.domain.model.session.TrainingSession;
import com.fitnessstudio.planner.domain.model.session.SessionId;
import com.fitnessstudio.planner.domain.model.trainer.TrainerId;

import java.util.List;
import java.util.Optional;

public interface TrainingSessionRepository {

    TrainingSession save(TrainingSession session);

    Optional<TrainingSession> findById(SessionId id);

    List<TrainingSession> findAll();

    List<TrainingSession> findByCourseId(CourseId courseId);

    List<TrainingSession> findByTrainerId(TrainerId trainerId);

    List<TrainingSession> findByRoomId(RoomId roomId);

    void deleteById(SessionId id);
}
