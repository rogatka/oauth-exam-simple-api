package com.exam.common.exception;

public class StudentNotFoundException extends NotFoundException {
    private static final String MESSAGE_TEMPLATE = "Student with id=%d not found";

    public StudentNotFoundException(Long id) {
        super(String.format(MESSAGE_TEMPLATE, id));
    }
}
