package com.exam.exams.service.impl;

import com.exam.common.exception.ExamResultNotFoundException;
import com.exam.exams.model.ExamResult;
import com.exam.exams.model.dto.ExamResultCreateDto;
import com.exam.exams.model.dto.ExamResultDto;
import com.exam.exams.model.dto.ExamResultUpdateDto;
import com.exam.exams.mapper.ExamResultMapper;
import com.exam.exams.mapper.StudentMapper;
import com.exam.exams.mapper.SubjectMapper;
import com.exam.exams.repository.ExamResultRepository;
import com.exam.exams.service.ExamResultService;
import com.exam.exams.service.StudentService;
import com.exam.exams.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamResultServiceImpl implements ExamResultService {

    private final StudentService studentService;
    private final SubjectService subjectService;
    private final ExamResultRepository examResultRepository;
    private final ExamResultMapper examResultMapper;
    private final StudentMapper studentMapper;
    private final SubjectMapper subjectMapper;

    @Override
    @Transactional
    public ExamResultDto create(ExamResultCreateDto examResultCreateDto) {
        var subjectDto = subjectService.findById(examResultCreateDto.getSubjectId());
        var studentDto = studentService.findById(examResultCreateDto.getStudentId());
        var mappedExamResult = examResultMapper.map(examResultCreateDto);
        mappedExamResult.setSubject(subjectMapper.map(subjectDto));
        mappedExamResult.setStudent(studentMapper.map(studentDto));
        return examResultMapper.map(examResultRepository.save(mappedExamResult));
    }

    @Override
    @Transactional
    public ExamResultDto update(Long id, ExamResultUpdateDto examResultUpdateDto) {
        var existingExamResult = findExamResultById(id);
        var mappedExamResult = examResultMapper.map(examResultUpdateDto, existingExamResult);
        var updatedExamResult = examResultRepository.save(mappedExamResult);
        return examResultMapper.map(updatedExamResult);
    }

    @Override
    public ExamResultDto findById(Long id) {
        return examResultMapper.map(findExamResultById(id));
    }

    @Override
    public List<ExamResultDto> findAll() {
        return examResultMapper.map(examResultRepository.findAll());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validateExists(id);
        examResultRepository.deleteById(id);
    }

    private void validateExists(Long id) {
        findExamResultById(id);
    }

    private ExamResult findExamResultById(Long id) {
        return examResultRepository.findById(id)
                .orElseThrow(() -> new ExamResultNotFoundException(id));
    }
}
