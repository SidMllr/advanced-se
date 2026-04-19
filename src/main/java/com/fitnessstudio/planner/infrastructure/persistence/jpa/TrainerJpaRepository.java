package com.fitnessstudio.planner.infrastructure.persistence.jpa;

import com.fitnessstudio.planner.infrastructure.persistence.entity.TrainerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrainerJpaRepository extends JpaRepository<TrainerJpaEntity, UUID> {}
