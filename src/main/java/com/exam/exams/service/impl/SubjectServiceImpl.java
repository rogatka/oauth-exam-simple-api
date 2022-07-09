package com.exam.exams.service.impl;

import com.exam.common.exception.SubjectNotFoundException;
import com.exam.common.exception.TutorNotFoundException;
import com.exam.exams.model.Subject;
import com.exam.exams.model.Tutor;
import com.exam.exams.model.dto.SubjectCreateDto;
import com.exam.exams.model.dto.SubjectDto;
import com.exam.exams.model.dto.SubjectUpdateDto;
import com.exam.exams.mapper.SubjectMapper;
import com.exam.exams.repository.SubjectRepository;
import com.exam.exams.repository.TutorRepository;
import com.exam.exams.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final TutorRepository tutorRepository;

    @Override
    public List<SubjectDto> findAll() {
        return subjectMapper.map(subjectRepository.findAll());
    }

    @Override
    public SubjectDto findById(Long id) {
        return subjectMapper.map(findSubjectById(id));
    }

    @Override
    @Transactional
    public SubjectDto assignTutor(Long subjectId, Long tutorId) {
        var subject = findSubjectById(subjectId);
        var tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new TutorNotFoundException(tutorId));
        subject.getTutors().add(tutor);
        return subjectMapper.map(subjectRepository.save(subject));
    }

    @Override
    @Transactional
    public SubjectDto removeTutor(Long subjectId, Long tutorId) {
        var subject = findSubjectById(subjectId);
        var tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new TutorNotFoundException(tutorId));
        if (subject.getTutors().stream()
                .mapToLong(Tutor::getId)
                .noneMatch(tutorId::equals)) {
            throw new TutorNotFoundException(tutorId, subjectId);
        }
        subject.getTutors().remove(tutor);
        return subjectMapper.map(subjectRepository.save(subject));
    }

    @Override
    @Transactional
    public SubjectDto create(SubjectCreateDto subjectCreateDto) {
        return subjectMapper.map(subjectRepository.save(subjectMapper.map(subjectCreateDto)));
    }

    @Override
    @Transactional
    public SubjectDto update(Long id, SubjectUpdateDto subjectUpdateDto) {
        var existingSubject = findSubjectById(id);
        var mappedSubject = subjectMapper.map(subjectUpdateDto, existingSubject);
        return subjectMapper.map(subjectRepository.save(mappedSubject));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validateExists(id);
        subjectRepository.deleteById(id);
    }

    private void validateExists(Long id) {
        findSubjectById(id);
    }

    private Subject findSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(id));
    }
}
