package com.fitnessstudio.planner.presentation.session;

import com.fitnessstudio.planner.application.session.TrainingSessionApplicationService;
import com.fitnessstudio.planner.application.session.dto.CreateSessionCommand;
import com.fitnessstudio.planner.application.session.dto.SessionDto;
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

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class TrainingSessionController {

    private final TrainingSessionApplicationService sessionService;

    public TrainingSessionController(TrainingSessionApplicationService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<SessionDto> createSession(@Valid @RequestBody CreateSessionRequest request) {
        CreateSessionCommand command = new CreateSessionCommand(
                request.courseId(), request.trainerId(), request.roomId(),
                request.dayOfWeek(), request.startTime(), request.durationMinutes());
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.createSession(command));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDto> getSession(@PathVariable String id) {
        return ResponseEntity.ok(sessionService.getSession(id));
    }

    @GetMapping
    public ResponseEntity<List<SessionDto>> getSessions(
            @RequestParam(required = false) String courseId,
            @RequestParam(required = false) String trainerId) {
        if (courseId != null) {
            return ResponseEntity.ok(sessionService.getSessionsByCourse(courseId));
        }
        if (trainerId != null) {
            return ResponseEntity.ok(sessionService.getSessionsByTrainer(trainerId));
        }
        return ResponseEntity.ok(sessionService.getAllSessions());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable String id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }

    public record CreateSessionRequest(
            @NotBlank String courseId,
            @NotBlank String trainerId,
            @NotBlank String roomId,
            @NotNull DayOfWeek dayOfWeek,
            @NotNull LocalTime startTime,
            @Min(15) int durationMinutes
    ) {}
}
