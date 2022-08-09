package com.exam.exams.web.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
public class ExamResultUpdateRequest {

    @Min(1)
    @Max(10)
    private Integer mark;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate examDate;
}
