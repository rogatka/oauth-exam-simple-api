package com.exam.exams.mapper;

import com.exam.common.configuration.MapperConfiguration;
import com.exam.exams.web.request.SubjectCreateRequest;
import com.exam.exams.web.response.SubjectResponse;
import com.exam.exams.web.request.SubjectUpdateRequest;
import com.exam.exams.model.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface SubjectMapper {
    List<SubjectResponse> map(List<Subject> subjects);
    SubjectResponse map(Subject subject);
    Subject map(SubjectResponse dto);
    Subject map(SubjectCreateRequest dto);
    Subject map(SubjectUpdateRequest dto, @MappingTarget Subject subject);
}