package com.fitnessstudio.planner.application.course.dto;

import com.fitnessstudio.planner.domain.model.course.CourseCategory;

public record CreateCourseCommand(
        String name,
        CourseCategory category,
        String description,
        int maxParticipants
) {}
