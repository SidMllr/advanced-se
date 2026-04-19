package com.fitnessstudio.planner.infrastructure.persistence.mapper;

import com.fitnessstudio.planner.domain.model.course.CourseCategory;
import com.fitnessstudio.planner.domain.model.course.CourseId;
import com.fitnessstudio.planner.domain.model.course.CourseName;
import com.fitnessstudio.planner.domain.model.course.MaxParticipants;
import com.fitnessstudio.planner.domain.model.course.SportsCourse;
import com.fitnessstudio.planner.infrastructure.persistence.entity.SportsCourseJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class SportsCourseMapper {

    public SportsCourseJpaEntity toJpaEntity(SportsCourse course) {
        return new SportsCourseJpaEntity(
                course.getId().value(),
                course.getName().value(),
                course.getCategory().name(),
                course.getDescription(),
                course.getMaxParticipants().value(),
                course.isActive()
        );
    }

    public SportsCourse toDomain(SportsCourseJpaEntity entity) {
        return new SportsCourse(
                CourseId.of(entity.getId()),
                CourseName.of(entity.getName()),
                CourseCategory.valueOf(entity.getCategory()),
                entity.getDescription(),
                MaxParticipants.of(entity.getMaxParticipants()),
                entity.isActive()
        );
    }
}
