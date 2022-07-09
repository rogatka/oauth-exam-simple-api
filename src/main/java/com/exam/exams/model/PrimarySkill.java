package com.exam.exams.model;

import lombok.Getter;

@Getter
public enum PrimarySkill {
    JAVA("java"),
    C_PLUS_PLUS("c++"),
    PYTHON("python"),
    DOTNET("dotnet"),
    JAVASCRIPT("javascript");

    private final String name;

    PrimarySkill(String name) {
        this.name = name;
    }
}
