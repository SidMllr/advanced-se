package com.fitnessstudio.planner.application.course;

import com.fitnessstudio.planner.application.course.dto.CourseDto;
import com.fitnessstudio.planner.application.course.dto.CreateCourseCommand;
import com.fitnessstudio.planner.application.exception.CourseNotFoundException;
import com.fitnessstudio.planner.application.exception.DuplicateCourseNameException;
import com.fitnessstudio.planner.domain.model.course.CourseCategory;
import com.fitnessstudio.planner.domain.model.course.CourseId;
import com.fitnessstudio.planner.domain.model.course.CourseName;
import com.fitnessstudio.planner.domain.model.course.MaxParticipants;
import com.fitnessstudio.planner.domain.model.course.SportsCourse;
import com.fitnessstudio.planner.domain.repository.SportsCourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseApplicationServiceTest {

    @Mock
    private SportsCourseRepository courseRepository;

    private CourseApplicationService courseService;

    @BeforeEach
    void setUp() {
        courseService = new CourseApplicationService(courseRepository);
    }

    @Test
    @DisplayName("should create a course when name is unique")
    void shouldCreateCourseWhenNameIsUnique() {
        CreateCourseCommand command = new CreateCourseCommand(
                "Evening Yoga", CourseCategory.YOGA, "Relaxing yoga", 20);

        when(courseRepository.existsByName(CourseName.of("Evening Yoga"))).thenReturn(false);
        when(courseRepository.save(any(SportsCourse.class))).thenAnswer(inv -> inv.getArgument(0));

        CourseDto result = courseService.createCourse(command);

        assertThat(result.name()).isEqualTo("Evening Yoga");
        assertThat(result.category()).isEqualTo(CourseCategory.YOGA);
        assertThat(result.maxParticipants()).isEqualTo(20);
        verify(courseRepository).save(any(SportsCourse.class));
    }

    @Test
    @DisplayName("should throw DuplicateCourseNameException when name already exists")
    void shouldThrowWhenCourseNameAlreadyExists() {
        CreateCourseCommand command = new CreateCourseCommand(
                "Yoga", CourseCategory.YOGA, "Yoga class", 15);

        when(courseRepository.existsByName(CourseName.of("Yoga"))).thenReturn(true);

        assertThatThrownBy(() -> courseService.createCourse(command))
                .isInstanceOf(DuplicateCourseNameException.class)
                .hasMessageContaining("Yoga");
    }

    @Test
    @DisplayName("should return course by ID when it exists")
    void shouldReturnCourseByIdWhenExists() {
        SportsCourse course = SportsCourse.create(
                CourseName.of("Pilates"), CourseCategory.PILATES, "Core strength", MaxParticipants.of(12));

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        CourseDto result = courseService.getCourse(course.getId().toString());

        assertThat(result.name()).isEqualTo("Pilates");
    }

    @Test
    @DisplayName("should throw CourseNotFoundException when course does not exist")
    void shouldThrowCourseNotFoundForUnknownId() {
        CourseId unknownId = CourseId.generate();
        when(courseRepository.findById(unknownId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.getCourse(unknownId.toString()))
                .isInstanceOf(CourseNotFoundException.class);
    }

    @Test
    @DisplayName("should return all active courses")
    void shouldReturnAllActiveCourses() {
        SportsCourse active1 = SportsCourse.create(
                CourseName.of("Yoga"), CourseCategory.YOGA, "Yoga", MaxParticipants.of(10));
        SportsCourse active2 = SportsCourse.create(
                CourseName.of("HIIT"), CourseCategory.HIIT, "HIIT", MaxParticipants.of(15));

        when(courseRepository.findAllActive()).thenReturn(List.of(active1, active2));

        List<CourseDto> result = courseService.getActiveCourses();

        assertThat(result).hasSize(2);
    }
}
