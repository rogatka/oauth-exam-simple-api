package com.exam.exams.service;

import com.exam.exams.model.dto.TutorCreateDto;
import com.exam.exams.model.dto.TutorDto;

import java.util.List;

public interface TutorService {

    TutorDto findById(Long id);

    List<TutorDto> findAll();

    TutorDto create(TutorCreateDto tutorCreateDto);

    void delete(Long id);
}
