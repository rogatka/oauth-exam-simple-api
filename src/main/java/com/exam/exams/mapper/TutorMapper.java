package com.exam.exams.mapper;

import com.exam.common.configuration.MapperConfiguration;
import com.exam.exams.web.request.TutorCreateRequest;
import com.exam.exams.web.response.TutorResponse;
import com.exam.exams.model.Tutor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface TutorMapper {
    TutorResponse map(Tutor tutor);

    @Mapping(source = "userId", target = "user.id")
    Tutor map(TutorCreateRequest dto);

    List<TutorResponse> map(List<Tutor> tutors);
}