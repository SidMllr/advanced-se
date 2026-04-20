package com.fitnessstudio.planner.presentation;

import com.fitnessstudio.planner.application.exception.CourseNotFoundException;
import com.fitnessstudio.planner.application.exception.DuplicateCourseNameException;
import com.fitnessstudio.planner.application.exception.EnrollmentException;
import com.fitnessstudio.planner.application.exception.MemberNotFoundException;
import com.fitnessstudio.planner.application.exception.RoomNotFoundException;
import com.fitnessstudio.planner.application.exception.SchedulingConflictException;
import com.fitnessstudio.planner.application.exception.SessionNotFoundException;
import com.fitnessstudio.planner.application.exception.TrainerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            CourseNotFoundException.class,
            TrainerNotFoundException.class,
            RoomNotFoundException.class,
            MemberNotFoundException.class,
            SessionNotFoundException.class
    })
    public ProblemDetail handleNotFound(RuntimeException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicateCourseNameException.class)
    public ProblemDetail handleDuplicateName(DuplicateCourseNameException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(SchedulingConflictException.class)
    public ProblemDetail handleSchedulingConflict(SchedulingConflictException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(EnrollmentException.class)
    public ProblemDetail handleEnrollmentError(EnrollmentException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleValidation(IllegalArgumentException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
