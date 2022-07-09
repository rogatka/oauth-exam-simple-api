package com.exam.exams.service.impl;

import com.exam.common.exception.TutorAlreadyExistsException;
import com.exam.common.exception.TutorNotFoundException;
import com.exam.exams.model.Tutor;
import com.exam.exams.model.dto.TutorCreateDto;
import com.exam.exams.model.dto.TutorDto;
import com.exam.exams.mapper.TutorMapper;
import com.exam.exams.mapper.UserMapper;
import com.exam.exams.repository.TutorRepository;
import com.exam.exams.service.TutorService;
import com.exam.exams.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorService {

    private final UserService userService;
    private final TutorRepository tutorRepository;
    private final TutorMapper tutorMapper;
    private final UserMapper userMapper;

    @Override
    public TutorDto findById(Long id) {
        return tutorMapper.map(findTutorById(id));
    }

    @Override
    public List<TutorDto> findAll() {
        return tutorMapper.map(tutorRepository.findAll());
    }

    @Override
    @Transactional
    public TutorDto create(TutorCreateDto tutorCreateDto) {
        var existingUserDto = userService.findById(tutorCreateDto.getUserId());
        if (tutorRepository.findByUserId(existingUserDto.getId()).isEmpty()) {
            var mappedTutor = tutorMapper.map(tutorCreateDto);
            mappedTutor.setUser(userMapper.map(existingUserDto));
            return tutorMapper.map(tutorRepository.save(mappedTutor));
        }
        throw new TutorAlreadyExistsException(tutorCreateDto.getUserId());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validateExists(id);
        tutorRepository.deleteById(id);
    }

    private void validateExists(Long id) {
        findTutorById(id);
    }

    private Tutor findTutorById(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new TutorNotFoundException(id));
    }
}
