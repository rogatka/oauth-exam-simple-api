package com.exam.common.exception;

public class ExamResultNotFoundException extends NotFoundException {
    private static final String MESSAGE_TEMPLATE = "Exam result with id=%d not found";

    public ExamResultNotFoundException(Long id) {
        super(String.format(MESSAGE_TEMPLATE, id));
    }
}
