package com.exam.exams.web.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TutorCreateRequest {
    @NotNull
    private Long userId;
}
