package com.exam.security.configuration.oidc;

import com.exam.exams.model.Authority;
import com.exam.exams.model.User;
import com.exam.exams.service.UserService;
import com.exam.exams.web.request.UserCreateRequest;
import com.exam.exams.web.request.UserUpdateRequest;
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
        Optional<User> existingUserOptional = userService.findByEmail(oidcUser.getAttribute("email"));
        if (existingUserOptional.isPresent()) {
            var existingUserDto = existingUserOptional.get();
            return userService.update(existingUserDto.getId(), toUserUpdateDto(oidcUser));
        }
        return userService.create(toUserCreateDto(oidcUser));
    }

    private UserUpdateRequest toUserUpdateDto(OidcUser oidcUser) {
        var userUpdateDto = new UserUpdateRequest();
        userUpdateDto.setFirstName(oidcUser.getAttribute("given_name"));
        userUpdateDto.setLastName(oidcUser.getAttribute("family_name"));
        Optional.ofNullable((String) oidcUser.getAttribute("birth_date"))
                .ifPresent(birthDateString -> userUpdateDto.setBirthDate(LocalDate.parse(birthDateString)));
        return userUpdateDto;
    }

    private UserCreateRequest toUserCreateDto(OidcUser oidcUser) {
        var userCreateDto = new UserCreateRequest();
        userCreateDto.setEmail(oidcUser.getAttribute("email"));
        userCreateDto.setFirstName(oidcUser.getAttribute("given_name"));
        userCreateDto.setLastName(oidcUser.getAttribute("family_name"));
        Optional.ofNullable((String) oidcUser.getAttribute("birth_date"))
                .ifPresent(birthDateString -> userCreateDto.setBirthDate(LocalDate.parse(birthDateString)));
        userCreateDto.setAuthorities(Set.of(Authority.USER));
        return userCreateDto;
    }
}
