package com.fitnessstudio.planner.domain.model.course;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SportsCourseTest {

    @Test
    @DisplayName("should create a SportsCourse with valid data")
    void shouldCreateSportsCourseWithValidData() {
        CourseName name = CourseName.of("Monday Yoga");
        MaxParticipants max = MaxParticipants.of(20);

        SportsCourse course = SportsCourse.create(name, CourseCategory.YOGA, "Relaxing yoga class", max);

        assertThat(course.getId()).isNotNull();
        assertThat(course.getName()).isEqualTo(name);
        assertThat(course.getCategory()).isEqualTo(CourseCategory.YOGA);
        assertThat(course.getMaxParticipants()).isEqualTo(max);
        assertThat(course.isActive()).isTrue();
    }

    @Test
    @DisplayName("should reject blank CourseName")
    void shouldRejectBlankCourseName() {
        assertThatThrownBy(() -> CourseName.of("  "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("blank");
    }

    @Test
    @DisplayName("should reject CourseName exceeding maximum length")
    void shouldRejectCourseNameExceedingMaxLength() {
        String tooLong = "A".repeat(CourseName.MAX_LENGTH + 1);
        assertThatThrownBy(() -> CourseName.of(tooLong))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exceed");
    }

    @Test
    @DisplayName("should reject MaxParticipants below minimum")
    void shouldRejectMaxParticipantsBelowMinimum() {
        assertThatThrownBy(() -> MaxParticipants.of(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("at least");
    }

    @Test
    @DisplayName("should deactivate course correctly")
    void shouldDeactivateCourse() {
        SportsCourse course = SportsCourse.create(
                CourseName.of("HIIT"), CourseCategory.HIIT, "High intensity", MaxParticipants.of(15));

        course.deactivate();

        assertThat(course.isActive()).isFalse();
    }

    @Test
    @DisplayName("should reactivate a deactivated course")
    void shouldReactivateCourse() {
        SportsCourse course = SportsCourse.create(
                CourseName.of("HIIT"), CourseCategory.HIIT, "High intensity", MaxParticipants.of(15));
        course.deactivate();

        course.activate();

        assertThat(course.isActive()).isTrue();
    }

    @Test
    @DisplayName("CourseName value objects with same value should be equal")
    void courseNamesWithSameValueShouldBeEqual() {
        CourseName name1 = CourseName.of("Yoga");
        CourseName name2 = CourseName.of("Yoga");
        assertThat(name1).isEqualTo(name2);
    }

    @Test
    @DisplayName("CourseName should trim surrounding whitespace")
    void courseNameShouldTrimWhitespace() {
        CourseName name = CourseName.of("  Yoga  ");
        assertThat(name.value()).isEqualTo("Yoga");
    }
}
