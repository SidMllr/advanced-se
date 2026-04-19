package com.fitnessstudio.planner.infrastructure.persistence.adapter;

import com.fitnessstudio.planner.domain.model.course.CourseId;
import com.fitnessstudio.planner.domain.model.course.CourseName;
import com.fitnessstudio.planner.domain.model.course.SportsCourse;
import com.fitnessstudio.planner.domain.repository.SportsCourseRepository;
import com.fitnessstudio.planner.infrastructure.persistence.entity.SportsCourseJpaEntity;
import com.fitnessstudio.planner.infrastructure.persistence.jpa.SportsCourseJpaRepository;
import com.fitnessstudio.planner.infrastructure.persistence.mapper.SportsCourseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SportsCourseRepositoryAdapter implements SportsCourseRepository {

    private final SportsCourseJpaRepository jpaRepository;
    private final SportsCourseMapper mapper;

    public SportsCourseRepositoryAdapter(SportsCourseJpaRepository jpaRepository,
                                          SportsCourseMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public SportsCourse save(SportsCourse course) {
        SportsCourseJpaEntity entity = mapper.toJpaEntity(course);
        SportsCourseJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<SportsCourse> findById(CourseId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<SportsCourse> findByName(CourseName name) {
        return jpaRepository.findByName(name.value()).map(mapper::toDomain);
    }

    @Override
    public List<SportsCourse> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<SportsCourse> findAllActive() {
        return jpaRepository.findAllByActiveTrue().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByName(CourseName name) {
        return jpaRepository.existsByName(name.value());
    }

    @Override
    public void deleteById(CourseId id) {
        jpaRepository.deleteById(id.value());
    }
}
