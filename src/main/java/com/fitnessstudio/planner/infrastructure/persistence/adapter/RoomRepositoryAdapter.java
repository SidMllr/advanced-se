package com.fitnessstudio.planner.infrastructure.persistence.adapter;

import com.fitnessstudio.planner.domain.model.room.Room;
import com.fitnessstudio.planner.domain.model.room.RoomId;
import com.fitnessstudio.planner.domain.repository.RoomRepository;
import com.fitnessstudio.planner.infrastructure.persistence.jpa.RoomJpaRepository;
import com.fitnessstudio.planner.infrastructure.persistence.mapper.RoomMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoomRepositoryAdapter implements RoomRepository {

    private final RoomJpaRepository jpaRepository;
    private final RoomMapper mapper;

    public RoomRepositoryAdapter(RoomJpaRepository jpaRepository, RoomMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Room save(Room room) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpaEntity(room)));
    }

    @Override
    public Optional<Room> findById(RoomId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<Room> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(RoomId id) {
        jpaRepository.deleteById(id.value());
    }
}
