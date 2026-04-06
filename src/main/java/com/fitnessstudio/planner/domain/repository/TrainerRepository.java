package com.fitnessstudio.planner.domain.repository;

import com.fitnessstudio.planner.domain.model.trainer.Trainer;
import com.fitnessstudio.planner.domain.model.trainer.TrainerId;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository {

    Trainer save(Trainer trainer);

    Optional<Trainer> findById(TrainerId id);

    List<Trainer> findAll();

    void deleteById(TrainerId id);
}
