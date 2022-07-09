package com.exam.exams.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class SubjectDto {
    private Long id;
    private String name;
    private String description;
    private Set<TutorDto> tutors;
}
