package com.fitnessstudio.planner.presentation.course;

import com.fitnessstudio.planner.application.course.CourseApplicationService;
import com.fitnessstudio.planner.application.course.dto.CourseDto;
import com.fitnessstudio.planner.application.course.dto.CreateCourseCommand;
import com.fitnessstudio.planner.domain.model.course.CourseCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseApplicationService courseService;

    public CourseController(CourseApplicationService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CreateCourseRequest request) {
        CreateCourseCommand command = new CreateCourseCommand(
                request.name(), request.category(), request.description(), request.maxParticipants());
        CourseDto created = courseService.createCourse(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable String id) {
        return ResponseEntity.ok(courseService.getCourse(id));
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses(
            @RequestParam(defaultValue = "false") boolean activeOnly) {
        List<CourseDto> courses = activeOnly
                ? courseService.getActiveCourses()
                : courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @DeleteMapping("/{id}/deactivate")
    public ResponseEntity<CourseDto> deactivateCourse(@PathVariable String id) {
        return ResponseEntity.ok(courseService.deactivateCourse(id));
    }

    public record CreateCourseRequest(
            @NotBlank String name,
            @NotNull CourseCategory category,
            @NotBlank String description,
            @Min(1) int maxParticipants
    ) {}
}
