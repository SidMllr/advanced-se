package com.fitnessstudio.planner.infrastructure.config;

import com.fitnessstudio.planner.domain.repository.TrainingSessionRepository;
import com.fitnessstudio.planner.domain.service.EnrollmentDomainService;
import com.fitnessstudio.planner.domain.service.SchedulingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainServiceConfig {

    @Bean
    public SchedulingService schedulingService(TrainingSessionRepository sessionRepository) {
        return new SchedulingService(sessionRepository);
    }

    @Bean
    public EnrollmentDomainService enrollmentDomainService() {
        return new EnrollmentDomainService();
    }
}
