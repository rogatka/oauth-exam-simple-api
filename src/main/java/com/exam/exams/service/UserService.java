package com.exam.exams.service;

import com.exam.exams.model.User;
import com.exam.exams.web.request.UserCreateRequest;
import com.exam.exams.web.request.UserUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(UserCreateRequest userCreateRequest);

    User findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    User update(Long id, UserUpdateRequest userUpdateRequest);

    void delete(Long id);
}
