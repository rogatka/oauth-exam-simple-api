package com.exam.exams.mapper;

import com.exam.common.configuration.MapperConfiguration;
import com.exam.exams.model.dto.StudentDto;
import com.exam.exams.model.dto.StudentUpdateDto;
import com.exam.exams.model.Student;
import com.exam.exams.model.dto.StudentCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface StudentMapper {
    List<StudentDto> map(List<Student> students);

    StudentDto map(Student student);

    @Mapping(source = "userId", target = "user.id")
    Student map(StudentCreateDto dto);

    Student map(StudentDto dto);

    Student map(StudentUpdateDto dto, @MappingTarget Student student);
}