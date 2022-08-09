package com.exam.exams.web.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExamResultResponse {
    private Long id;
    private StudentResponse student;
    private SubjectResponse subject;
    private Integer mark;
    private LocalDate examDate;
}
