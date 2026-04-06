package com.fitnessstudio.planner.domain.service;

import com.fitnessstudio.planner.domain.model.member.MemberId;
import com.fitnessstudio.planner.domain.model.session.TrainingSession;

import java.util.Objects;

public class EnrollmentDomainService {



    public boolean enrollMember(TrainingSession session, MemberId memberId) {
        Objects.requireNonNull(session, "TrainingSession must not be null");
        Objects.requireNonNull(memberId, "MemberId must not be null");
        return session.enroll(memberId);
    }



    public void cancelEnrollment(TrainingSession session, MemberId memberId) {
        Objects.requireNonNull(session, "TrainingSession must not be null");
        Objects.requireNonNull(memberId, "MemberId must not be null");
        session.cancelEnrollment(memberId);
    }



    public void cancelWaitlistEntry(TrainingSession session, MemberId memberId) {
        Objects.requireNonNull(session, "TrainingSession must not be null");
        Objects.requireNonNull(memberId, "MemberId must not be null");
        session.cancelWaitlistEntry(memberId);
    }
}
