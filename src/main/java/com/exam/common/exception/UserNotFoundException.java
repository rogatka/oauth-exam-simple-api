package com.exam.common.exception;

public class UserNotFoundException extends NotFoundException {
    private static final String MESSAGE_TEMPLATE = "User with id=%d not found";

    public UserNotFoundException(Long id) {
        super(String.format(MESSAGE_TEMPLATE, id));
    }
}
