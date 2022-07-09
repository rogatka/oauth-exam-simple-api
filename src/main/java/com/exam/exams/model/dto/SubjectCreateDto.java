package com.exam.exams.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SubjectCreateDto {
    @NotNull
    private String name;
    private String description;
}
