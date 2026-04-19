package com.fitnessstudio.planner.infrastructure.persistence.jpa;

import com.fitnessstudio.planner.infrastructure.persistence.entity.MemberJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, UUID> {

    Optional<MemberJpaEntity> findByEmail(String email);
}
