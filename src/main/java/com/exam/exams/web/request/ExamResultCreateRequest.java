package com.exam.exams.web.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ExamResultCreateRequest {
    @NotNull
    private Long studentId;
    @NotNull
    private Long subjectId;
    @NotNull
    @Min(1)
    @Max(10)
    private Integer mark;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate examDate;
}
