package com.exam.common.exception;

public class SubjectNotFoundException extends NotFoundException {
    private static final String MESSAGE_TEMPLATE = "Subject with id=%d not found";

    public SubjectNotFoundException(Long id) {
        super(String.format(MESSAGE_TEMPLATE, id));
    }
}
