package com.exam.exams.model.dto;

import lombok.Data;

@Data(staticConstructor = "of")
public class ErrorInfo {
    private final String message;
}
