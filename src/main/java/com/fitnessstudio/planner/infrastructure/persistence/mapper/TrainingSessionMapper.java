package com.fitnessstudio.planner.infrastructure.persistence.mapper;

import com.fitnessstudio.planner.domain.model.course.CourseId;
import com.fitnessstudio.planner.domain.model.course.MaxParticipants;
import com.fitnessstudio.planner.domain.model.member.MemberId;
import com.fitnessstudio.planner.domain.model.room.RoomId;
import com.fitnessstudio.planner.domain.model.session.Enrollment;
import com.fitnessstudio.planner.domain.model.session.EnrollmentId;
import com.fitnessstudio.planner.domain.model.session.SessionId;
import com.fitnessstudio.planner.domain.model.session.TimeSlot;
import com.fitnessstudio.planner.domain.model.session.TrainingSession;
import com.fitnessstudio.planner.domain.model.session.WaitlistEntry;
import com.fitnessstudio.planner.domain.model.session.WaitlistEntryId;
import com.fitnessstudio.planner.domain.model.trainer.TrainerId;
import com.fitnessstudio.planner.infrastructure.persistence.entity.EnrollmentJpaEntity;
import com.fitnessstudio.planner.infrastructure.persistence.entity.TrainingSessionJpaEntity;
import com.fitnessstudio.planner.infrastructure.persistence.entity.WaitlistEntryJpaEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainingSessionMapper {

    public TrainingSessionJpaEntity toJpaEntity(TrainingSession session) {
        TrainingSessionJpaEntity entity = new TrainingSessionJpaEntity(
                session.getId().value(),
                session.getCourseId().value(),
                session.getTrainerId().value(),
                session.getRoomId().value(),
                session.getTimeSlot().dayOfWeek(),
                session.getTimeSlot().startTime(),
                session.getTimeSlot().durationMinutes(),
                session.getMaxParticipants().value()
        );

        List<EnrollmentJpaEntity> enrollmentEntities = session.getEnrollments().stream()
                .map(e -> new EnrollmentJpaEntity(
                        e.getId().value(), entity, e.getMemberId().value(), e.getEnrolledAt()))
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));

        List<WaitlistEntryJpaEntity> waitlistEntities = session.getWaitlist().stream()
                .map(w -> new WaitlistEntryJpaEntity(
                        w.getId().value(), entity, w.getMemberId().value(), w.getAddedAt()))
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));

        entity.setEnrollments(enrollmentEntities);
        entity.setWaitlistEntries(waitlistEntities);

        return entity;
    }

    public TrainingSession toDomain(TrainingSessionJpaEntity entity) {
        List<Enrollment> enrollments = entity.getEnrollments().stream()
                .map(e -> new Enrollment(
                        EnrollmentId.of(e.getId()),
                        MemberId.of(e.getMemberId()),
                        e.getEnrolledAt()))
                .toList();

        List<WaitlistEntry> waitlist = entity.getWaitlistEntries().stream()
                .map(w -> new WaitlistEntry(
                        WaitlistEntryId.of(w.getId()),
                        MemberId.of(w.getMemberId()),
                        w.getAddedAt()))
                .toList();

        return TrainingSession.builder()
                .id(SessionId.of(entity.getId()))
                .courseId(CourseId.of(entity.getCourseId()))
                .trainerId(TrainerId.of(entity.getTrainerId()))
                .roomId(RoomId.of(entity.getRoomId()))
                .timeSlot(TimeSlot.of(entity.getDayOfWeek(), entity.getStartTime(), entity.getDurationMinutes()))
                .maxParticipants(MaxParticipants.of(entity.getMaxParticipants()))
                .enrollments(enrollments)
                .waitlist(waitlist)
                .build();
    }
}
