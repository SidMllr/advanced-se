package com.fitnessstudio.planner.domain.model.room;

import java.util.Objects;

public class Room {

    private final RoomId id;
    private String name;
    private int capacity;

    public Room(RoomId id, String name, int capacity) {
        this.id = Objects.requireNonNull(id, "RoomId must not be null");
        this.name = requireNonBlank(name, "Room name");
        this.capacity = requirePositive(capacity);
    }

    public static Room create(String name, int capacity) {
        return new Room(RoomId.generate(), name, capacity);
    }

    public void updateName(String name) {
        this.name = requireNonBlank(name, "Room name");
    }

    public void updateCapacity(int capacity) {
        this.capacity = requirePositive(capacity);
    }

    private static String requireNonBlank(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value.trim();
    }

    private static int requirePositive(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Room capacity must be a positive number");
        }
        return capacity;
    }

    public RoomId getId() { return id; }
    public String getName() { return name; }
    public int getCapacity() { return capacity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Room{id=" + id + ", name='" + name + "', capacity=" + capacity + "}";
    }
}
