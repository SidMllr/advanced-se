package com.fitnessstudio.planner.presentation.trainer;

import com.fitnessstudio.planner.application.trainer.TrainerApplicationService;
import com.fitnessstudio.planner.application.trainer.dto.CreateTrainerCommand;
import com.fitnessstudio.planner.application.trainer.dto.TrainerDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerApplicationService trainerService;

    public TrainerController(TrainerApplicationService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    public ResponseEntity<TrainerDto> createTrainer(@Valid @RequestBody CreateTrainerRequest request) {
        CreateTrainerCommand command = new CreateTrainerCommand(
                request.name(), request.qualifications(), request.contactEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(trainerService.createTrainer(command));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerDto> getTrainer(@PathVariable String id) {
        return ResponseEntity.ok(trainerService.getTrainer(id));
    }

    @GetMapping
    public ResponseEntity<List<TrainerDto>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable String id) {
        trainerService.deleteTrainer(id);
        return ResponseEntity.noContent().build();
    }

    public record CreateTrainerRequest(
            @NotBlank String name,
            List<String> qualifications,
            String contactEmail
    ) {}
}
