package com.exam.exams.service.access;

import com.exam.security.service.UserPrincipalService;
import com.exam.exams.model.Tutor;
import com.exam.exams.repository.SubjectRepository;
import com.exam.exams.repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectAccessService {

    private final UserPrincipalService userPrincipalService;
    private final SubjectRepository subjectRepository;
    private final TutorRepository tutorRepository;

    public boolean isSubjectTutor(Long subjectId) {
        var tutor = tutorRepository.findByUserId(userPrincipalService.getId());
        if (tutor.isEmpty()) return false;
        var subject = subjectRepository.findById(subjectId);
        if (subject.isEmpty()) return false;
        return subject.get().getTutors().stream()
                .map(Tutor::getId)
                .anyMatch(tutorId -> tutorId.equals(tutor.get().getId()));
    }
}
