package com.exam.exams.service;

import com.exam.exams.model.Student;
import com.exam.exams.web.request.StudentCreateRequest;
import com.exam.exams.web.request.StudentUpdateRequest;

import java.util.List;

public interface StudentService {
    Student create(StudentCreateRequest studentCreateRequest);

    Student findById(Long id);

    List<Student> findAll();

    Student update(Long id, StudentUpdateRequest userUpdateDto);

    void delete(Long id);
}
