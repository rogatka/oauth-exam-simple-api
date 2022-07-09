package com.exam.security.service;

import com.exam.exams.mapper.UserMapper;
import com.exam.exams.model.Authority;
import com.exam.exams.model.User;
import com.exam.exams.model.dto.UserCreateDto;
import com.exam.exams.model.dto.UserDto;
import com.exam.exams.model.dto.UserUpdateDto;
import com.exam.exams.service.UserService;
import com.exam.security.model.UserPrincipal;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final Counter oidcCounter;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        var oidcUser = super.loadUser(userRequest);
        User user = processOidcUser(oidcUser);
        UserPrincipal principal = UserPrincipal.from(user);
        principal.setOidcUser(oidcUser);
        oidcCounter.increment();
        return principal;
    }

    private User processOidcUser(OidcUser oidcUser) {
        Optional<UserDto> existingUserDtoOptional = userService.findByEmail(oidcUser.getAttribute("email"));
        if (existingUserDtoOptional.isPresent()) {
            var existingUserDto = existingUserDtoOptional.get();
            return userMapper.map(userService.update(existingUserDto.getId(), toUserUpdateDto(oidcUser)));
        }
        return userMapper.map(userService.create(toUserCreateDto(oidcUser)));
    }

    private UserUpdateDto toUserUpdateDto(OidcUser oidcUser) {
        var userUpdateDto = new UserUpdateDto();
        userUpdateDto.setFirstName(oidcUser.getAttribute("given_name"));
        userUpdateDto.setLastName(oidcUser.getAttribute("family_name"));
        Optional.ofNullable((String) oidcUser.getAttribute("birth_date"))
                .ifPresent(birthDateString -> userUpdateDto.setBirthDate(LocalDate.parse(birthDateString)));
        return userUpdateDto;
    }

    private UserCreateDto toUserCreateDto(OidcUser oidcUser) {
        var userCreateDto = new UserCreateDto();
        userCreateDto.setEmail(oidcUser.getAttribute("email"));
        userCreateDto.setFirstName(oidcUser.getAttribute("given_name"));
        userCreateDto.setLastName(oidcUser.getAttribute("family_name"));
        Optional.ofNullable((String) oidcUser.getAttribute("birth_date"))
                .ifPresent(birthDateString -> userCreateDto.setBirthDate(LocalDate.parse(birthDateString)));
        userCreateDto.setAuthorities(Set.of(Authority.USER));
        return userCreateDto;
    }
}
