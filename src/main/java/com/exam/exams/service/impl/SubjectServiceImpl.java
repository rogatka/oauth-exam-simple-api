package com.exam.exams.service.impl;

import com.exam.common.exception.SubjectNotFoundException;
import com.exam.common.exception.TutorNotFoundException;
import com.exam.exams.mapper.SubjectMapper;
import com.exam.exams.model.Subject;
import com.exam.exams.model.Tutor;
import com.exam.exams.web.request.SubjectCreateRequest;
import com.exam.exams.web.request.SubjectUpdateRequest;
import com.exam.exams.repository.SubjectRepository;
import com.exam.exams.service.SubjectService;
import com.exam.exams.service.TutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    private final SubjectMapper subjectMapper;

    private final TutorService tutorService;

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject findById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> {
                    log.debug("Subject with id = {} not found", id);
                    throw new SubjectNotFoundException(id);
                });
    }

    @Override
    @Transactional
    public Subject assignTutor(Long subjectId, Long tutorId) {
        var subject = findById(subjectId);
        var tutor = tutorService.findById(tutorId);
        subject.getTutors().add(tutor);
        return subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public Subject removeTutor(Long subjectId, Long tutorId) {
        var subject = findById(subjectId);
        var tutor = tutorService.findById(tutorId);
        if (subject.getTutors().stream()
                .mapToLong(Tutor::getId)
                .noneMatch(tutorId::equals)) {
            log.debug("Tutor with id = {} not found for subject with id = {}", tutorId, subjectId);
            throw new TutorNotFoundException(tutorId, subjectId);
        }
        subject.getTutors().remove(tutor);
        return subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public Subject create(SubjectCreateRequest subjectCreateRequest) {
        return subjectRepository.save(subjectMapper.map(subjectCreateRequest));
    }

    @Override
    @Transactional
    public Subject update(Long id, SubjectUpdateRequest subjectUpdateRequest) {
        var existingSubject = findById(id);
        var mappedSubject = subjectMapper.map(subjectUpdateRequest, existingSubject);
        return subjectRepository.save(mappedSubject);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        findById(id);
        subjectRepository.deleteById(id);
    }
}
