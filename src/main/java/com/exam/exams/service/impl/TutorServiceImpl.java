package com.exam.exams.service.impl;

import com.exam.common.exception.TutorAlreadyExistsException;
import com.exam.common.exception.TutorNotFoundException;
import com.exam.exams.mapper.TutorMapper;
import com.exam.exams.model.Tutor;
import com.exam.exams.repository.TutorRepository;
import com.exam.exams.service.TutorService;
import com.exam.exams.service.UserService;
import com.exam.exams.web.request.TutorCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorService {

    private final UserService userService;

    private final TutorRepository tutorRepository;

    private final TutorMapper tutorMapper;

    @Override
    public Tutor findById(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> {
                    log.debug("Tutor with id = {} not found", id);
                    throw new TutorNotFoundException(id);
                });
    }

    @Override
    public List<Tutor> findAll() {
        return tutorRepository.findAll();
    }

    @Override
    @Transactional
    public Tutor create(TutorCreateRequest tutorCreateRequest) {
        Long userId = tutorCreateRequest.getUserId();
        var existingUser = userService.findById(userId);
        if (tutorRepository.findByUserId(existingUser.getId()).isEmpty()) {
            var mappedTutor = tutorMapper.map(tutorCreateRequest);
            mappedTutor.setUser(existingUser);
            return tutorRepository.save(mappedTutor);
        }
        log.debug("User with id = {} is already a tutor", userId);
        throw new TutorAlreadyExistsException(userId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        findById(id);
        tutorRepository.deleteById(id);
    }

    @Override
    public Optional<Tutor> findByUserId(Long userId) {
        return tutorRepository.findByUserId(userId);
    }
}
