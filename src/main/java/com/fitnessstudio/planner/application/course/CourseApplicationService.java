package com.fitnessstudio.planner.application.course;

import com.fitnessstudio.planner.application.course.dto.CourseDto;
import com.fitnessstudio.planner.application.course.dto.CreateCourseCommand;
import com.fitnessstudio.planner.application.exception.CourseNotFoundException;
import com.fitnessstudio.planner.application.exception.DuplicateCourseNameException;
import com.fitnessstudio.planner.domain.model.course.CourseId;
import com.fitnessstudio.planner.domain.model.course.CourseName;
import com.fitnessstudio.planner.domain.model.course.MaxParticipants;
import com.fitnessstudio.planner.domain.model.course.SportsCourse;
import com.fitnessstudio.planner.domain.repository.SportsCourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CourseApplicationService {

    private final SportsCourseRepository courseRepository;

    public CourseApplicationService(SportsCourseRepository courseRepository) {
        this.courseRepository = Objects.requireNonNull(courseRepository);
    }



    public CourseDto createCourse(CreateCourseCommand command) {
        Objects.requireNonNull(command, "Command must not be null");

        CourseName name = CourseName.of(command.name());
        if (courseRepository.existsByName(name)) {
            throw new DuplicateCourseNameException(command.name());
        }

        SportsCourse course = SportsCourse.create(
                name,
                command.category(),
                command.description(),
                MaxParticipants.of(command.maxParticipants())
        );

        return CourseDto.from(courseRepository.save(course));
    }



    @Transactional(readOnly = true)
    public CourseDto getCourse(String id) {
        return courseRepository.findById(CourseId.of(id))
                .map(CourseDto::from)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }



    @Transactional(readOnly = true)
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(CourseDto::from)
                .toList();
    }



    @Transactional(readOnly = true)
    public List<CourseDto> getActiveCourses() {
        return courseRepository.findAllActive().stream()
                .map(CourseDto::from)
                .toList();
    }



    public CourseDto deactivateCourse(String id) {
        SportsCourse course = findCourseOrThrow(id);
        course.deactivate();
        return CourseDto.from(courseRepository.save(course));
    }

    private SportsCourse findCourseOrThrow(String id) {
        return courseRepository.findById(CourseId.of(id))
                .orElseThrow(() -> new CourseNotFoundException(id));
    }
}
