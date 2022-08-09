package com.exam.exams.service.impl;

import com.exam.common.exception.ExamResultNotFoundException;
import com.exam.exams.mapper.ExamResultMapper;
import com.exam.exams.model.ExamResult;
import com.exam.exams.repository.ExamResultRepository;
import com.exam.exams.service.ExamResultService;
import com.exam.exams.service.StudentService;
import com.exam.exams.service.SubjectService;
import com.exam.exams.web.request.ExamResultCreateRequest;
import com.exam.exams.web.request.ExamResultUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamResultServiceImpl implements ExamResultService {

    private final StudentService studentService;

    private final SubjectService subjectService;

    private final ExamResultRepository examResultRepository;

    private final ExamResultMapper examResultMapper;

    @Override
    @Transactional
    public ExamResult create(ExamResultCreateRequest examResultCreateRequest) {
        var subject = subjectService.findById(examResultCreateRequest.getSubjectId());
        var student = studentService.findById(examResultCreateRequest.getStudentId());
        var mappedExamResult = examResultMapper.map(examResultCreateRequest);
        mappedExamResult.setSubject(subject);
        mappedExamResult.setStudent(student);
        return examResultRepository.save(mappedExamResult);
    }

    @Override
    @Transactional
    public ExamResult update(Long id, ExamResultUpdateRequest examResultUpdateRequest) {
        var existingExamResult = findById(id);
        var mappedExamResult = examResultMapper.map(examResultUpdateRequest, existingExamResult);
        return examResultRepository.save(mappedExamResult);
    }

    @Override
    public ExamResult findById(Long id) {
        return examResultRepository.findById(id)
                .orElseThrow(() -> {
                    log.debug("Exam result with id = {} not found", id);
                    throw new ExamResultNotFoundException(id);
                });
    }

    @Override
    public List<ExamResult> findAll() {
        return examResultRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        findById(id);
        examResultRepository.deleteById(id);
    }
}
