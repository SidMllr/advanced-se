package com.fitnessstudio.planner.application.trainer.dto;

import com.fitnessstudio.planner.domain.model.trainer.Trainer;

import java.util.List;

public record TrainerDto(
        String id,
        String name,
        List<String> qualifications,
        String contactEmail
) {
    public static TrainerDto from(Trainer trainer) {
        return new TrainerDto(
                trainer.getId().toString(),
                trainer.getName(),
                trainer.getQualifications().stream()
                        .map(q -> q.title())
                        .toList(),
                trainer.getContactEmail()
        );
    }
}
