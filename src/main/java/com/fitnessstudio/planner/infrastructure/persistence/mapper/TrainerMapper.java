package com.fitnessstudio.planner.infrastructure.persistence.mapper;

import com.fitnessstudio.planner.domain.model.trainer.Trainer;
import com.fitnessstudio.planner.domain.model.trainer.TrainerId;
import com.fitnessstudio.planner.domain.model.trainer.TrainerQualification;
import com.fitnessstudio.planner.infrastructure.persistence.entity.TrainerJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrainerMapper {

    public TrainerJpaEntity toJpaEntity(Trainer trainer) {
        List<String> qualifications = trainer.getQualifications().stream()
                .map(TrainerQualification::title)
                .toList();
        return new TrainerJpaEntity(
                trainer.getId().value(),
                trainer.getName(),
                qualifications,
                trainer.getContactEmail()
        );
    }

    public Trainer toDomain(TrainerJpaEntity entity) {
        List<TrainerQualification> qualifications = entity.getQualifications().stream()
                .map(TrainerQualification::of)
                .toList();
        return new Trainer(
                TrainerId.of(entity.getId()),
                entity.getName(),
                qualifications,
                entity.getContactEmail()
        );
    }
}
