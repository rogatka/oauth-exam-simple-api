package com.exam.exams.service;

import com.exam.exams.model.ExamResult;
import com.exam.exams.web.request.ExamResultCreateRequest;
import com.exam.exams.web.request.ExamResultUpdateRequest;

import java.util.List;

public interface ExamResultService {
    ExamResult create(ExamResultCreateRequest examResultCreateRequest);

    ExamResult update(Long id, ExamResultUpdateRequest examResultUpdateRequest);

    ExamResult findById(Long id);

    List<ExamResult> findAll();

    void delete(Long id);
}
