package com.exam.exams.mapper;

import com.exam.common.configuration.MapperConfiguration;
import com.exam.exams.model.ExamResult;
import com.exam.exams.web.request.ExamResultCreateRequest;
import com.exam.exams.web.request.ExamResultUpdateRequest;
import com.exam.exams.web.response.ExamResultResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface ExamResultMapper {

    ExamResultResponse map(ExamResult examResult);

    @Mapping(source = "studentId", target = "student.id")
    @Mapping(source = "subjectId", target = "subject.id")
    ExamResult map(ExamResultCreateRequest dto);

    ExamResult map(ExamResultUpdateRequest dto, @MappingTarget ExamResult examResult);

    List<ExamResultResponse> map(List<ExamResult> examResults);
}