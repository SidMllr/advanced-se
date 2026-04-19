package com.fitnessstudio.planner.infrastructure.persistence.jpa;

import com.fitnessstudio.planner.infrastructure.persistence.entity.RoomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomJpaRepository extends JpaRepository<RoomJpaEntity, UUID> {}
