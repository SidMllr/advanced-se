package com.fitnessstudio.planner.application.enrollment;

import com.fitnessstudio.planner.application.exception.EnrollmentException;
import com.fitnessstudio.planner.application.exception.MemberNotFoundException;
import com.fitnessstudio.planner.application.exception.SessionNotFoundException;
import com.fitnessstudio.planner.application.session.dto.SessionDto;
import com.fitnessstudio.planner.domain.model.member.MemberId;
import com.fitnessstudio.planner.domain.model.session.SessionId;
import com.fitnessstudio.planner.domain.model.session.TrainingSession;
import com.fitnessstudio.planner.domain.repository.MemberRepository;
import com.fitnessstudio.planner.domain.repository.TrainingSessionRepository;
import com.fitnessstudio.planner.domain.service.EnrollmentDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class EnrollmentApplicationService {

    private final TrainingSessionRepository sessionRepository;
    private final MemberRepository memberRepository;
    private final EnrollmentDomainService enrollmentDomainService;

    public EnrollmentApplicationService(TrainingSessionRepository sessionRepository,
                                         MemberRepository memberRepository,
                                         EnrollmentDomainService enrollmentDomainService) {
        this.sessionRepository = Objects.requireNonNull(sessionRepository);
        this.memberRepository = Objects.requireNonNull(memberRepository);
        this.enrollmentDomainService = Objects.requireNonNull(enrollmentDomainService);
    }

    public SessionDto enroll(String sessionId, String memberId) {
        Objects.requireNonNull(sessionId);
        Objects.requireNonNull(memberId);

        TrainingSession session = findSessionOrThrow(sessionId);
        MemberId memberIdObj = MemberId.of(memberId);

        memberRepository.findById(memberIdObj)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        try {
            enrollmentDomainService.enrollMember(session, memberIdObj);
        } catch (IllegalStateException e) {
            throw new EnrollmentException(e.getMessage());
        }

        return SessionDto.from(sessionRepository.save(session));
    }

    public SessionDto cancelEnrollment(String sessionId, String memberId) {
        Objects.requireNonNull(sessionId);
        Objects.requireNonNull(memberId);

        TrainingSession session = findSessionOrThrow(sessionId);
        MemberId memberIdObj = MemberId.of(memberId);

        try {
            enrollmentDomainService.cancelEnrollment(session, memberIdObj);
        } catch (IllegalStateException e) {
            throw new EnrollmentException(e.getMessage());
        }

        return SessionDto.from(sessionRepository.save(session));
    }

    public SessionDto cancelWaitlistEntry(String sessionId, String memberId) {
        Objects.requireNonNull(sessionId);
        Objects.requireNonNull(memberId);

        TrainingSession session = findSessionOrThrow(sessionId);
        MemberId memberIdObj = MemberId.of(memberId);

        try {
            enrollmentDomainService.cancelWaitlistEntry(session, memberIdObj);
        } catch (IllegalStateException e) {
            throw new EnrollmentException(e.getMessage());
        }

        return SessionDto.from(sessionRepository.save(session));
    }

    private TrainingSession findSessionOrThrow(String id) {
        return sessionRepository.findById(SessionId.of(id))
                .orElseThrow(() -> new SessionNotFoundException(id));
    }
}
