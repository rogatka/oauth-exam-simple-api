package com.exam.exams.service.impl;

import com.exam.common.exception.StudentAlreadyExistsException;
import com.exam.common.exception.StudentNotFoundException;
import com.exam.exams.mapper.StudentMapper;
import com.exam.exams.model.Student;
import com.exam.exams.repository.StudentRepository;
import com.exam.exams.service.StudentService;
import com.exam.exams.service.UserService;
import com.exam.exams.web.request.StudentCreateRequest;
import com.exam.exams.web.request.StudentUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final UserService userService;

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    @Override
    public Student create(StudentCreateRequest studentCreateRequest) {
        Long userId = studentCreateRequest.getUserId();
        var existingUser = userService.findById(userId);
        if (studentRepository.findByUserId(existingUser.getId()).isEmpty()) {
            var mappedStudent = studentMapper.map(studentCreateRequest);
            mappedStudent.setUser(existingUser);
            return studentRepository.save(mappedStudent);
        }
        log.debug("User with id = {} is already a student", userId);
        throw new StudentAlreadyExistsException(userId);
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.debug("Student with id = {} not found", id);
                    throw new StudentNotFoundException(id);
                });
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student update(Long id, StudentUpdateRequest studentUpdateRequest) {
        var existingStudent = findById(id);
        var mappedStudent = studentMapper.map(studentUpdateRequest, existingStudent);
        return studentRepository.save(mappedStudent);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        findById(id);
        studentRepository.deleteById(id);
    }
}
