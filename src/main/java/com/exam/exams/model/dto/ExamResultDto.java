package com.exam.exams.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExamResultDto {
    private Long id;
    private StudentDto student;
    private SubjectDto subject;
    private Integer mark;
    private LocalDate examDate;
}
