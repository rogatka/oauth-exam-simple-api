package com.exam.common.exception;

public class StudentAlreadyExistsException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Student with user id=%d already exists";

    public StudentAlreadyExistsException(Long id) {
        super(String.format(MESSAGE_TEMPLATE, id));
    }
}
