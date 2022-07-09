package com.exam.common.exception;

public class TutorAlreadyExistsException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Tutor with user id=%d already exists";

    public TutorAlreadyExistsException(Long id) {
        super(String.format(MESSAGE_TEMPLATE, id));
    }
}
