package com.exam.exams.mapper;

import com.exam.common.configuration.MapperConfiguration;
import com.exam.exams.web.request.UserCreateRequest;
import com.exam.exams.web.response.UserResponse;
import com.exam.exams.web.request.UserUpdateRequest;
import com.exam.exams.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface UserMapper {
    List<UserResponse> map(List<User> users);

    UserResponse map(User user);

    User map(UserResponse dto);

    User map(UserCreateRequest dto);

    User map(UserUpdateRequest dto, @MappingTarget User user);
}