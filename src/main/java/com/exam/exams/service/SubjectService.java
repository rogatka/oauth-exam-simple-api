package com.exam.exams.service;

import com.exam.exams.model.dto.SubjectCreateDto;
import com.exam.exams.model.dto.SubjectDto;
import com.exam.exams.model.dto.SubjectUpdateDto;

import java.util.List;

public interface SubjectService {
    List<SubjectDto> findAll();

    SubjectDto findById(Long id);

    SubjectDto assignTutor(Long subjectId, Long tutorId);

    SubjectDto removeTutor(Long subjectId, Long tutorId);

    SubjectDto create(SubjectCreateDto subjectCreateDto);

    SubjectDto update(Long id, SubjectUpdateDto subjectUpdateDto);

    void delete(Long id);
}
