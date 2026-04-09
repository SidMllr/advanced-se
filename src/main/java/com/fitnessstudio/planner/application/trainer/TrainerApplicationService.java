package com.fitnessstudio.planner.application.trainer;

import com.fitnessstudio.planner.application.exception.TrainerNotFoundException;
import com.fitnessstudio.planner.application.trainer.dto.CreateTrainerCommand;
import com.fitnessstudio.planner.application.trainer.dto.TrainerDto;
import com.fitnessstudio.planner.domain.model.trainer.Trainer;
import com.fitnessstudio.planner.domain.model.trainer.TrainerId;
import com.fitnessstudio.planner.domain.model.trainer.TrainerQualification;
import com.fitnessstudio.planner.domain.repository.TrainerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TrainerApplicationService {

    private final TrainerRepository trainerRepository;

    public TrainerApplicationService(TrainerRepository trainerRepository) {
        this.trainerRepository = Objects.requireNonNull(trainerRepository);
    }



    public TrainerDto createTrainer(CreateTrainerCommand command) {
        Objects.requireNonNull(command, "Command must not be null");

        List<TrainerQualification> qualifications = command.qualifications() == null
                ? List.of()
                : command.qualifications().stream()
                        .map(TrainerQualification::of)
                        .toList();

        Trainer trainer = Trainer.create(command.name(), qualifications, command.contactEmail());
        return TrainerDto.from(trainerRepository.save(trainer));
    }

    @Transactional(readOnly = true)
    public TrainerDto getTrainer(String id) {
        return trainerRepository.findById(TrainerId.of(id))
                .map(TrainerDto::from)
                .orElseThrow(() -> new TrainerNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<TrainerDto> getAllTrainers() {
        return trainerRepository.findAll().stream()
                .map(TrainerDto::from)
                .toList();
    }

    public void deleteTrainer(String id) {
        trainerRepository.findById(TrainerId.of(id))
                .orElseThrow(() -> new TrainerNotFoundException(id));
        trainerRepository.deleteById(TrainerId.of(id));
    }
}
