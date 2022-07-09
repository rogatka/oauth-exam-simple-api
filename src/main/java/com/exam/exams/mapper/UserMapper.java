package com.exam.exams.mapper;

import com.exam.common.configuration.MapperConfiguration;
import com.exam.exams.model.dto.UserCreateDto;
import com.exam.exams.model.dto.UserDto;
import com.exam.exams.model.dto.UserUpdateDto;
import com.exam.exams.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface UserMapper {
    List<UserDto> map(List<User> users);

    UserDto map(User user);

    User map(UserDto dto);

    User map(UserCreateDto dto);

    User map(UserUpdateDto dto, @MappingTarget User user);
}