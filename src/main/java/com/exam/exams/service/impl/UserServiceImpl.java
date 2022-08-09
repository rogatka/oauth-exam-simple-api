package com.exam.exams.service.impl;

import com.exam.common.exception.UserNotFoundException;
import com.exam.exams.mapper.UserMapper;
import com.exam.exams.model.User;
import com.exam.exams.web.request.UserCreateRequest;
import com.exam.exams.web.request.UserUpdateRequest;
import com.exam.exams.repository.UserRepository;
import com.exam.exams.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.debug("User with id = {} not found", id);
                    throw new UserNotFoundException(id);
                });
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User create(UserCreateRequest userCreateRequest) {
        var mappedUser = userMapper.map(userCreateRequest);
        mappedUser.setCreatedAt(LocalDateTime.now());
        return userRepository.save(mappedUser);
    }

    @Transactional
    @Override
    public User update(Long id, UserUpdateRequest userUpdateRequest) {
        var existingUser = findById(id);
        var mappedUser = userMapper.map(userUpdateRequest, existingUser);
        return userRepository.save(mappedUser);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }
}
