package com.fitnessstudio.planner.presentation.enrollment;

import com.fitnessstudio.planner.application.enrollment.EnrollmentApplicationService;
import com.fitnessstudio.planner.application.session.dto.SessionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sessions/{sessionId}/enrollments")
public class EnrollmentController {

    private final EnrollmentApplicationService enrollmentService;

    public EnrollmentController(EnrollmentApplicationService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<SessionDto> enroll(
            @PathVariable String sessionId,
            @RequestParam String memberId) {
        return ResponseEntity.ok(enrollmentService.enroll(sessionId, memberId));
    }

    @DeleteMapping
    public ResponseEntity<SessionDto> cancelEnrollment(
            @PathVariable String sessionId,
            @RequestParam String memberId) {
        return ResponseEntity.ok(enrollmentService.cancelEnrollment(sessionId, memberId));
    }

    @DeleteMapping("/waitlist")
    public ResponseEntity<SessionDto> cancelWaitlistEntry(
            @PathVariable String sessionId,
            @RequestParam String memberId) {
        return ResponseEntity.ok(enrollmentService.cancelWaitlistEntry(sessionId, memberId));
    }
}
