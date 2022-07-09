package com.exam.exams.model.dto;

import com.exam.exams.model.PrimarySkill;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StudentUpdateDto {
    @NotNull
    private PrimarySkill primarySkill;
}
