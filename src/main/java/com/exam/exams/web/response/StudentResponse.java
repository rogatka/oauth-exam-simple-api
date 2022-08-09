package com.exam.exams.web.response;

import com.exam.exams.model.PrimarySkill;
import lombok.Data;

@Data
public class StudentResponse {
    private Long id;
    private UserResponse user;
    private PrimarySkill primarySkill;
}
