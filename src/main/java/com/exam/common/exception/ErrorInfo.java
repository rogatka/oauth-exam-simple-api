package com.exam.common.exception;

import lombok.Data;

@Data(staticConstructor = "of")
public class ErrorInfo {
    private final String message;
}
