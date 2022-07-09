package com.exam.exams.service;

import com.exam.exams.model.dto.UserCreateDto;
import com.exam.exams.model.dto.UserDto;
import com.exam.exams.model.dto.UserUpdateDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto create(UserCreateDto userCreateDto);

    UserDto findById(Long id);

    Optional<UserDto> findByEmail(String email);

    List<UserDto> findAll();

    UserDto update(Long id, UserUpdateDto userUpdateDto);

    void delete(Long id);
}
