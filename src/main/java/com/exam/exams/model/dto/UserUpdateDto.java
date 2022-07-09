package com.exam.exams.model.dto;

import com.exam.exams.model.Authority;
import lombok.Data;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Set;

@Data
public class UserUpdateDto {
    @Email
    private String email;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private Set<Authority> authorities;
}
