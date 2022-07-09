package com.exam.common.exception;

public class TutorNotFoundException extends NotFoundException {
    private static final String TUTOR_ID_NOT_FOUND_MESSAGE_TEMPLATE = "Tutor with id=%d not found";
    private static final String SUBJECT_TUTOR_NOT_FOUND_MESSAGE_TEMPLATE = "Tutor with id=%d not found among subject #%d tutors";

    public TutorNotFoundException(Long id) {
        super(String.format(TUTOR_ID_NOT_FOUND_MESSAGE_TEMPLATE, id));
    }

    public TutorNotFoundException(Long tutorId, Long subjectId) {
        super(String.format(SUBJECT_TUTOR_NOT_FOUND_MESSAGE_TEMPLATE, tutorId, subjectId));
    }
}
