package com.fitnessstudio.planner.domain.repository;

import com.fitnessstudio.planner.domain.model.course.CourseId;
import com.fitnessstudio.planner.domain.model.course.CourseName;
import com.fitnessstudio.planner.domain.model.course.SportsCourse;

import java.util.List;
import java.util.Optional;

public interface SportsCourseRepository {

    SportsCourse save(SportsCourse course);

    Optional<SportsCourse> findById(CourseId id);

    Optional<SportsCourse> findByName(CourseName name);

    List<SportsCourse> findAll();

    List<SportsCourse> findAllActive();

    boolean existsByName(CourseName name);

    void deleteById(CourseId id);
}
