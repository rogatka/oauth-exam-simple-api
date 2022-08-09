package com.exam.exams.web.request;

import com.exam.exams.model.PrimarySkill;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StudentUpdateRequest {
    @NotNull
    private PrimarySkill primarySkill;
}
