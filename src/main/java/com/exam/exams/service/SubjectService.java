package com.exam.exams.service;

import com.exam.exams.model.Subject;
import com.exam.exams.web.request.SubjectCreateRequest;
import com.exam.exams.web.request.SubjectUpdateRequest;

import java.util.List;

public interface SubjectService {
    List<Subject> findAll();

    Subject findById(Long id);

    Subject assignTutor(Long subjectId, Long tutorId);

    Subject removeTutor(Long subjectId, Long tutorId);

    Subject create(SubjectCreateRequest subjectCreateRequest);

    Subject update(Long id, SubjectUpdateRequest subjectUpdateRequest);

    void delete(Long id);
}
