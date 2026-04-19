package com.fitnessstudio.planner.infrastructure.persistence.mapper;

import com.fitnessstudio.planner.domain.model.room.Room;
import com.fitnessstudio.planner.domain.model.room.RoomId;
import com.fitnessstudio.planner.infrastructure.persistence.entity.RoomJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public RoomJpaEntity toJpaEntity(Room room) {
        return new RoomJpaEntity(room.getId().value(), room.getName(), room.getCapacity());
    }

    public Room toDomain(RoomJpaEntity entity) {
        return new Room(RoomId.of(entity.getId()), entity.getName(), entity.getCapacity());
    }
}
