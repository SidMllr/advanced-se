package com.fitnessstudio.planner.infrastructure.persistence.adapter;

import com.fitnessstudio.planner.domain.model.course.CourseId;
import com.fitnessstudio.planner.domain.model.room.RoomId;
import com.fitnessstudio.planner.domain.model.session.SessionId;
import com.fitnessstudio.planner.domain.model.session.TrainingSession;
import com.fitnessstudio.planner.domain.model.trainer.TrainerId;
import com.fitnessstudio.planner.domain.repository.TrainingSessionRepository;
import com.fitnessstudio.planner.infrastructure.persistence.jpa.TrainingSessionJpaRepository;
import com.fitnessstudio.planner.infrastructure.persistence.mapper.TrainingSessionMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainingSessionRepositoryAdapter implements TrainingSessionRepository {

    private final TrainingSessionJpaRepository jpaRepository;
    private final TrainingSessionMapper mapper;

    public TrainingSessionRepositoryAdapter(TrainingSessionJpaRepository jpaRepository,
                                             TrainingSessionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public TrainingSession save(TrainingSession session) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpaEntity(session)));
    }

    @Override
    public Optional<TrainingSession> findById(SessionId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<TrainingSession> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<TrainingSession> findByCourseId(CourseId courseId) {
        return jpaRepository.findByCourseId(courseId.value()).stream()
                .map(mapper::toDomain).toList();
    }

    @Override
    public List<TrainingSession> findByTrainerId(TrainerId trainerId) {
        return jpaRepository.findByTrainerId(trainerId.value()).stream()
                .map(mapper::toDomain).toList();
    }

    @Override
    public List<TrainingSession> findByRoomId(RoomId roomId) {
        return jpaRepository.findByRoomId(roomId.value()).stream()
                .map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(SessionId id) {
        jpaRepository.deleteById(id.value());
    }
}
