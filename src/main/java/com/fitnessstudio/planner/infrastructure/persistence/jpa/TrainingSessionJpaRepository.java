package com.fitnessstudio.planner.infrastructure.persistence.jpa;

import com.fitnessstudio.planner.infrastructure.persistence.entity.TrainingSessionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TrainingSessionJpaRepository extends JpaRepository<TrainingSessionJpaEntity, UUID> {

    List<TrainingSessionJpaEntity> findByCourseId(UUID courseId);

    List<TrainingSessionJpaEntity> findByTrainerId(UUID trainerId);

    List<TrainingSessionJpaEntity> findByRoomId(UUID roomId);
}
