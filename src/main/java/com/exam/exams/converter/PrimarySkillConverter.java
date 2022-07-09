package com.exam.exams.converter;

import com.exam.exams.model.PrimarySkill;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class PrimarySkillConverter implements AttributeConverter<PrimarySkill, String> {

    @Override
    public String convertToDatabaseColumn(PrimarySkill skill) {
        if (skill == null) {
            return null;
        }
        return skill.getName();
    }

    @Override
    public PrimarySkill convertToEntityAttribute(String name) {
        if (name == null) {
            return null;
        }

        return Stream.of(PrimarySkill.values())
                .filter(sill -> sill.getName().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
