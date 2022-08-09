package com.exam.exams.web.request;

import com.exam.exams.model.PrimarySkill;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StudentCreateRequest {
    @NotNull
    private Long userId;
    @NotNull
    private PrimarySkill primarySkill;
}
