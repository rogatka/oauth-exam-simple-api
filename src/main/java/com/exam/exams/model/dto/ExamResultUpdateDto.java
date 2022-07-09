package com.exam.exams.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
public class ExamResultUpdateDto {

    @Min(1)
    @Max(10)
    private Integer mark;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate examDate;
}
