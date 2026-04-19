package com.fitnessstudio.planner.application.session;

import com.fitnessstudio.planner.application.exception.CourseNotFoundException;
import com.fitnessstudio.planner.application.exception.RoomNotFoundException;
import com.fitnessstudio.planner.application.exception.SchedulingConflictException;
import com.fitnessstudio.planner.application.exception.SessionNotFoundException;
import com.fitnessstudio.planner.application.exception.TrainerNotFoundException;
import com.fitnessstudio.planner.application.session.dto.CreateSessionCommand;
import com.fitnessstudio.planner.application.session.dto.SessionDto;
import com.fitnessstudio.planner.domain.model.course.CourseId;
import com.fitnessstudio.planner.domain.model.course.SportsCourse;
import com.fitnessstudio.planner.domain.model.room.RoomId;
import com.fitnessstudio.planner.domain.model.session.SessionId;
import com.fitnessstudio.planner.domain.model.session.TimeSlot;
import com.fitnessstudio.planner.domain.model.session.TrainingSession;
import com.fitnessstudio.planner.domain.model.trainer.TrainerId;
import com.fitnessstudio.planner.domain.repository.SportsCourseRepository;
import com.fitnessstudio.planner.domain.repository.RoomRepository;
import com.fitnessstudio.planner.domain.repository.TrainerRepository;
import com.fitnessstudio.planner.domain.repository.TrainingSessionRepository;
import com.fitnessstudio.planner.domain.service.SchedulingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TrainingSessionApplicationService {

    private final TrainingSessionRepository sessionRepository;
    private final SportsCourseRepository courseRepository;
    private final TrainerRepository trainerRepository;
    private final RoomRepository roomRepository;
    private final SchedulingService schedulingService;

    public TrainingSessionApplicationService(TrainingSessionRepository sessionRepository,
                                              SportsCourseRepository courseRepository,
                                              TrainerRepository trainerRepository,
                                              RoomRepository roomRepository,
                                              SchedulingService schedulingService) {
        this.sessionRepository = Objects.requireNonNull(sessionRepository);
        this.courseRepository = Objects.requireNonNull(courseRepository);
        this.trainerRepository = Objects.requireNonNull(trainerRepository);
        this.roomRepository = Objects.requireNonNull(roomRepository);
        this.schedulingService = Objects.requireNonNull(schedulingService);
    }

    public SessionDto createSession(CreateSessionCommand command) {
        Objects.requireNonNull(command, "Command must not be null");

        CourseId courseId = CourseId.of(command.courseId());
        TrainerId trainerId = TrainerId.of(command.trainerId());
        RoomId roomId = RoomId.of(command.roomId());

        SportsCourse course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(command.courseId()));

        trainerRepository.findById(trainerId)
                .orElseThrow(() -> new TrainerNotFoundException(command.trainerId()));

        roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(command.roomId()));

        TimeSlot timeSlot = TimeSlot.of(command.dayOfWeek(), command.startTime(), command.durationMinutes());

        try {
            schedulingService.assertNoConflict(trainerId, roomId, timeSlot);
        } catch (IllegalStateException e) {
            throw new SchedulingConflictException(e.getMessage());
        }

        TrainingSession session = TrainingSession.builder()
                .id(SessionId.generate())
                .courseId(courseId)
                .trainerId(trainerId)
                .roomId(roomId)
                .timeSlot(timeSlot)
                .maxParticipants(course.getMaxParticipants())
                .build();

        return SessionDto.from(sessionRepository.save(session));
    }

    @Transactional(readOnly = true)
    public SessionDto getSession(String id) {
        return sessionRepository.findById(SessionId.of(id))
                .map(SessionDto::from)
                .orElseThrow(() -> new SessionNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<SessionDto> getAllSessions() {
        return sessionRepository.findAll().stream()
                .map(SessionDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SessionDto> getSessionsByCourse(String courseId) {
        courseRepository.findById(CourseId.of(courseId))
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        return sessionRepository.findByCourseId(CourseId.of(courseId)).stream()
                .map(SessionDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SessionDto> getSessionsByTrainer(String trainerId) {
        trainerRepository.findById(TrainerId.of(trainerId))
                .orElseThrow(() -> new TrainerNotFoundException(trainerId));
        return sessionRepository.findByTrainerId(TrainerId.of(trainerId)).stream()
                .map(SessionDto::from)
                .toList();
    }

    public void deleteSession(String id) {
        sessionRepository.findById(SessionId.of(id))
                .orElseThrow(() -> new SessionNotFoundException(id));
        sessionRepository.deleteById(SessionId.of(id));
    }
}
