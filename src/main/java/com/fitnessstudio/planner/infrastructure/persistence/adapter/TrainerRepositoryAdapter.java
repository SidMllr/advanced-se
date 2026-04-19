package com.fitnessstudio.planner.infrastructure.persistence.adapter;

import com.fitnessstudio.planner.domain.model.trainer.Trainer;
import com.fitnessstudio.planner.domain.model.trainer.TrainerId;
import com.fitnessstudio.planner.domain.repository.TrainerRepository;
import com.fitnessstudio.planner.infrastructure.persistence.jpa.TrainerJpaRepository;
import com.fitnessstudio.planner.infrastructure.persistence.mapper.TrainerMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainerRepositoryAdapter implements TrainerRepository {

    private final TrainerJpaRepository jpaRepository;
    private final TrainerMapper mapper;

    public TrainerRepositoryAdapter(TrainerJpaRepository jpaRepository, TrainerMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Trainer save(Trainer trainer) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpaEntity(trainer)));
    }

    @Override
    public Optional<Trainer> findById(TrainerId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<Trainer> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(TrainerId id) {
        jpaRepository.deleteById(id.value());
    }
}
