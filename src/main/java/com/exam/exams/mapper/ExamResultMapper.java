package com.exam.exams.mapper;

import com.exam.common.configuration.MapperConfiguration;
import com.exam.exams.model.ExamResult;
import com.exam.exams.model.dto.ExamResultCreateDto;
import com.exam.exams.model.dto.ExamResultDto;
import com.exam.exams.model.dto.ExamResultUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface ExamResultMapper {

    ExamResultDto map(ExamResult examResult);

    @Mapping(source = "studentId", target = "student.id")
    @Mapping(source = "subjectId", target = "subject.id")
    ExamResult map(ExamResultCreateDto dto);

    ExamResult map(ExamResultUpdateDto dto, @MappingTarget ExamResult examResult);

    List<ExamResultDto> map(List<ExamResult> examResults);
}