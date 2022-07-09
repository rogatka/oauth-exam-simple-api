package com.exam.security.service;

import com.exam.exams.repository.TutorRepository;
import com.exam.security.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class UserPrincipalService {

    private final TutorRepository tutorRepository;

    public UserPrincipal getPrincipal() {
        var context = SecurityContextHolder.getContext();
        var authentication = context.getAuthentication();
        Assert.notNull(authentication, "Authentication object cannot be null");
        Object principal = authentication.getPrincipal();
        return (UserPrincipal) principal;
    }

    public boolean isTutor() {
        return tutorRepository.findByUserId(getId()).isPresent();
    }

    public Long getId() {
        return getPrincipal().getUser().getId();
    }
}
