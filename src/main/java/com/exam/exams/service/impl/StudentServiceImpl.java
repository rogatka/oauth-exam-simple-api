package com.exam.exams.service.impl;

import com.exam.common.exception.StudentAlreadyExistsException;
import com.exam.common.exception.StudentNotFoundException;
import com.exam.exams.model.Student;
import com.exam.exams.model.dto.StudentCreateDto;
import com.exam.exams.model.dto.StudentDto;
import com.exam.exams.model.dto.StudentUpdateDto;
import com.exam.exams.mapper.StudentMapper;
import com.exam.exams.mapper.UserMapper;
import com.exam.exams.repository.StudentRepository;
import com.exam.exams.service.StudentService;
import com.exam.exams.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final UserService userService;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final UserMapper userMapper;

    @Override
    public StudentDto create(StudentCreateDto studentCreateDto) {
        var existingUserDto = userService.findById(studentCreateDto.getUserId());
        if (studentRepository.findByUserId(existingUserDto.getId()).isEmpty()) {
            var mappedStudent = studentMapper.map(studentCreateDto);
            mappedStudent.setUser(userMapper.map(existingUserDto));
            return studentMapper.map(studentRepository.save(mappedStudent));
        }
        throw new StudentAlreadyExistsException(studentCreateDto.getUserId());
    }

    @Override
    public StudentDto findById(Long id) {
        return studentMapper.map(findStudentById(id));
    }

    @Override
    public List<StudentDto> findAll() {
        return studentMapper.map(studentRepository.findAll());
    }

    @Override
    public StudentDto update(Long id, StudentUpdateDto studentUpdateDto) {
        var existingStudent = findStudentById(id);
        var mappedStudent = studentMapper.map(studentUpdateDto, existingStudent);
        var updatedStudent = studentRepository.save(mappedStudent);
        return studentMapper.map(updatedStudent);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        validateExists(id);
        studentRepository.deleteById(id);
    }

    private void validateExists(Long id) {
        findStudentById(id);
    }

    private Student findStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }
}
