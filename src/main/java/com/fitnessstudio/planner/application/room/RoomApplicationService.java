package com.fitnessstudio.planner.application.room;

import com.fitnessstudio.planner.application.exception.RoomNotFoundException;
import com.fitnessstudio.planner.application.room.dto.CreateRoomCommand;
import com.fitnessstudio.planner.application.room.dto.RoomDto;
import com.fitnessstudio.planner.domain.model.room.Room;
import com.fitnessstudio.planner.domain.model.room.RoomId;
import com.fitnessstudio.planner.domain.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class RoomApplicationService {

    private final RoomRepository roomRepository;

    public RoomApplicationService(RoomRepository roomRepository) {
        this.roomRepository = Objects.requireNonNull(roomRepository);
    }

    public RoomDto createRoom(CreateRoomCommand command) {
        Objects.requireNonNull(command, "Command must not be null");
        Room room = Room.create(command.name(), command.capacity());
        return RoomDto.from(roomRepository.save(room));
    }

    @Transactional(readOnly = true)
    public RoomDto getRoom(String id) {
        return roomRepository.findById(RoomId.of(id))
                .map(RoomDto::from)
                .orElseThrow(() -> new RoomNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomDto::from)
                .toList();
    }

    public void deleteRoom(String id) {
        roomRepository.findById(RoomId.of(id))
                .orElseThrow(() -> new RoomNotFoundException(id));
        roomRepository.deleteById(RoomId.of(id));
    }
}
