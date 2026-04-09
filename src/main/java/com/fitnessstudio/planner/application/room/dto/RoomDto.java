package com.fitnessstudio.planner.application.room.dto;

import com.fitnessstudio.planner.domain.model.room.Room;

public record RoomDto(String id, String name, int capacity) {

    public static RoomDto from(Room room) {
        return new RoomDto(room.getId().toString(), room.getName(), room.getCapacity());
    }
}
