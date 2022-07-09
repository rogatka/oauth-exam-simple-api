package com.exam.exams.model.dto;

import com.exam.exams.model.Authority;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
public class UserCreateDto {
    @Email
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private LocalDate birthDate;

    private Set<Authority> authorities;
}
