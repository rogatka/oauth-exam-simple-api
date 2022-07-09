package com.exam.exams.service.impl;

import com.exam.common.exception.UserNotFoundException;
import com.exam.exams.model.User;
import com.exam.exams.model.dto.UserCreateDto;
import com.exam.exams.model.dto.UserDto;
import com.exam.exams.model.dto.UserUpdateDto;
import com.exam.exams.mapper.UserMapper;
import com.exam.exams.repository.UserRepository;
import com.exam.exams.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto findById(Long id) {
        return userMapper.map(findUserById(id));
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::map);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return userMapper.map(users);
    }

    @Transactional
    @Override
    public UserDto create(UserCreateDto userCreateDto) {
        var mappedUser = userMapper.map(userCreateDto);
        mappedUser.setCreatedAt(LocalDateTime.now());
        var createdUser = userRepository.save(mappedUser);
        return userMapper.map(createdUser);
    }

    @Transactional
    @Override
    public UserDto update(Long id, UserUpdateDto userUpdateDto) {
        var existingUser = findUserById(id);
        var mappedUser = userMapper.map(userUpdateDto, existingUser);
        var updatedUser = userRepository.save(mappedUser);
        return userMapper.map(updatedUser);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        validateExists(id);
        userRepository.deleteById(id);
    }

    private void validateExists(Long id) {
        findUserById(id);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
