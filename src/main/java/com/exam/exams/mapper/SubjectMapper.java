package com.exam.exams.mapper;

import com.exam.common.configuration.MapperConfiguration;
import com.exam.exams.model.dto.SubjectCreateDto;
import com.exam.exams.model.dto.SubjectDto;
import com.exam.exams.model.dto.SubjectUpdateDto;
import com.exam.exams.model.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface SubjectMapper {
    List<SubjectDto> map(List<Subject> subjects);
    SubjectDto map(Subject subject);
    Subject map(SubjectDto dto);
    Subject map(SubjectCreateDto dto);
    Subject map(SubjectUpdateDto dto, @MappingTarget Subject subject);
}