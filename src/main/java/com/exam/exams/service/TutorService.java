package com.exam.exams.service;

import com.exam.exams.model.Tutor;
import com.exam.exams.web.request.TutorCreateRequest;

import java.util.List;
import java.util.Optional;

public interface TutorService {

    Tutor findById(Long id);

    List<Tutor> findAll();

    Tutor create(TutorCreateRequest tutorCreateRequest);

    void delete(Long id);

    Optional<Tutor> findByUserId(Long userId);
}
