package com.exam.exams.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TutorCreateDto {
    @NotNull
    private Long userId;
}
