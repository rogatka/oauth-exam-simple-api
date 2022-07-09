package com.exam.exams.service;

import com.exam.exams.model.dto.StudentCreateDto;
import com.exam.exams.model.dto.StudentDto;
import com.exam.exams.model.dto.StudentUpdateDto;

import java.util.List;

public interface StudentService {
    StudentDto create(StudentCreateDto studentCreateDto);

    StudentDto findById(Long id);

    List<StudentDto> findAll();

    StudentDto update(Long id, StudentUpdateDto userUpdateDto);

    void delete(Long id);
}
