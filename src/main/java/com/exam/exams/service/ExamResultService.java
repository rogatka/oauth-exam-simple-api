package com.exam.exams.service;

import com.exam.exams.model.dto.ExamResultCreateDto;
import com.exam.exams.model.dto.ExamResultDto;
import com.exam.exams.model.dto.ExamResultUpdateDto;

import java.util.List;

public interface ExamResultService {
    ExamResultDto create(ExamResultCreateDto examResultCreateDto);

    ExamResultDto update(Long id, ExamResultUpdateDto examResultUpdateDto);

    ExamResultDto findById(Long id);

    List<ExamResultDto> findAll();

    void delete(Long id);
}
