package com.fitnessstudio.planner.infrastructure.persistence.jpa;

import com.fitnessstudio.planner.infrastructure.persistence.entity.SportsCourseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SportsCourseJpaRepository extends JpaRepository<SportsCourseJpaEntity, UUID> {

    Optional<SportsCourseJpaEntity> findByName(String name);

    boolean existsByName(String name);

    List<SportsCourseJpaEntity> findAllByActiveTrue();
}
