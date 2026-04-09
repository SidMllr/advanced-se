package com.fitnessstudio.planner.application.trainer.dto;

import java.util.List;

public record CreateTrainerCommand(
        String name,
        List<String> qualifications,
        String contactEmail
) {}
