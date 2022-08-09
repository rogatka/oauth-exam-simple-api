package com.exam.exams.web.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SubjectCreateRequest {
    @NotNull
    private String name;
    private String description;
}
