package com.exam.exams.web.response;

import lombok.Data;

import java.util.Set;

@Data
public class SubjectResponse {
    private Long id;
    private String name;
    private String description;
    private Set<TutorResponse> tutors;
}
