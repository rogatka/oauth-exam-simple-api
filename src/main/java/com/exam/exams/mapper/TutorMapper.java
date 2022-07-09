package com.exam.exams.mapper;

import com.exam.common.configuration.MapperConfiguration;
import com.exam.exams.model.dto.TutorCreateDto;
import com.exam.exams.model.dto.TutorDto;
import com.exam.exams.model.Tutor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface TutorMapper {
    TutorDto map(Tutor tutor);

    @Mapping(source = "userId", target = "user.id")
    Tutor map(TutorCreateDto dto);

    List<TutorDto> map(List<Tutor> tutors);
}