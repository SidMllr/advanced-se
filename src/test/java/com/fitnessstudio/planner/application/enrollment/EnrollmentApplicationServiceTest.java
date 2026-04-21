package com.fitnessstudio.planner.application.enrollment;

import com.fitnessstudio.planner.application.exception.EnrollmentException;
import com.fitnessstudio.planner.application.exception.MemberNotFoundException;
import com.fitnessstudio.planner.application.session.dto.SessionDto;
import com.fitnessstudio.planner.domain.model.course.CourseId;
import com.fitnessstudio.planner.domain.model.course.MaxParticipants;
import com.fitnessstudio.planner.domain.model.member.MemberId;
import com.fitnessstudio.planner.domain.model.room.RoomId;
import com.fitnessstudio.planner.domain.model.session.SessionId;
import com.fitnessstudio.planner.domain.model.session.TimeSlot;
import com.fitnessstudio.planner.domain.model.session.TrainingSession;
import com.fitnessstudio.planner.domain.model.trainer.TrainerId;
import com.fitnessstudio.planner.domain.repository.MemberRepository;
import com.fitnessstudio.planner.domain.repository.TrainingSessionRepository;
import com.fitnessstudio.planner.domain.service.EnrollmentDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnrollmentApplicationServiceTest {

    @Mock
    private TrainingSessionRepository sessionRepository;

    @Mock
    private MemberRepository memberRepository;

    private EnrollmentApplicationService enrollmentService;

    private TrainingSession session;
    private MemberId memberId;
    private SessionId sessionId;

    @BeforeEach
    void setUp() {
        EnrollmentDomainService domainService = new EnrollmentDomainService();
        enrollmentService = new EnrollmentApplicationService(sessionRepository, memberRepository, domainService);

        sessionId = SessionId.generate();
        memberId = MemberId.generate();

        session = TrainingSession.builder()
                .id(sessionId)
                .courseId(CourseId.generate())
                .trainerId(TrainerId.generate())
                .roomId(RoomId.generate())
                .timeSlot(TimeSlot.of(DayOfWeek.MONDAY, LocalTime.of(9, 0), 60))
                .maxParticipants(MaxParticipants.of(5))
                .build();
    }

    @Test
    @DisplayName("should enroll member when member and session exist and capacity is available")
    void shouldEnrollMemberSuccessfully() {
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(
                com.fitnessstudio.planner.domain.model.member.Member.create("Test",
                        com.fitnessstudio.planner.domain.model.member.ContactInfo.of("test@example.com"))));
        when(sessionRepository.save(any(TrainingSession.class))).thenAnswer(inv -> inv.getArgument(0));

        SessionDto result = enrollmentService.enroll(sessionId.toString(), memberId.toString());

        assertThat(result.enrollmentCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("should throw MemberNotFoundException when member does not exist")
    void shouldThrowWhenMemberNotFound() {
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> enrollmentService.enroll(sessionId.toString(), memberId.toString()))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("should throw EnrollmentException when member tries to enroll twice")
    void shouldThrowWhenMemberAlreadyEnrolled() {
        session.enroll(memberId);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(
                com.fitnessstudio.planner.domain.model.member.Member.create("Test",
                        com.fitnessstudio.planner.domain.model.member.ContactInfo.of("test@example.com"))));

        assertThatThrownBy(() -> enrollmentService.enroll(sessionId.toString(), memberId.toString()))
                .isInstanceOf(EnrollmentException.class)
                .hasMessageContaining("already enrolled");
    }
}
