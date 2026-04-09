package com.fitnessstudio.planner.application.course.dto;

import com.fitnessstudio.planner.domain.model.course.CourseCategory;
import com.fitnessstudio.planner.domain.model.course.SportsCourse;

public record CourseDto(
        String id,
        String name,
        CourseCategory category,
        String description,
        int maxParticipants,
        boolean active
) {
    public static CourseDto from(SportsCourse course) {
        return new CourseDto(
                course.getId().toString(),
                course.getName().value(),
                course.getCategory(),
                course.getDescription(),
                course.getMaxParticipants().value(),
                course.isActive()
        );
    }
}
