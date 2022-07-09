package com.exam.exams.model.dto;

import com.exam.exams.model.PrimarySkill;
import lombok.Data;

@Data
public class StudentDto {
    private Long id;
    private UserDto user;
    private PrimarySkill primarySkill;
}
