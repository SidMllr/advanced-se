package com.fitnessstudio.planner.domain.repository;

import com.fitnessstudio.planner.domain.model.room.Room;
import com.fitnessstudio.planner.domain.model.room.RoomId;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {

    Room save(Room room);

    Optional<Room> findById(RoomId id);

    List<Room> findAll();

    void deleteById(RoomId id);
}
