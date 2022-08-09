package com.exam.exams.mapper;

import com.exam.common.configuration.MapperConfiguration;
import com.exam.exams.web.request.StudentCreateRequest;
import com.exam.exams.web.response.StudentResponse;
import com.exam.exams.web.request.StudentUpdateRequest;
import com.exam.exams.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface StudentMapper {
    List<StudentResponse> map(List<Student> students);

    StudentResponse map(Student student);

    @Mapping(source = "userId", target = "user.id")
    Student map(StudentCreateRequest dto);

    Student map(StudentResponse dto);

    Student map(StudentUpdateRequest dto, @MappingTarget Student student);
}