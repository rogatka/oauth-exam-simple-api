package com.exam.exams.web;

import com.exam.exams.mapper.UserMapper;
import com.exam.exams.model.User;
import com.exam.exams.service.UserService;
import com.exam.exams.web.request.UserCreateRequest;
import com.exam.exams.web.request.UserUpdateRequest;
import com.exam.exams.web.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "User controller")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @Operation(description = "Get oauth user info")
    @GetMapping("/me")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> userAttributes = new HashMap<>();
        userAttributes.put("name", principal.getAttribute("name"));
        userAttributes.put("givenName", principal.getAttribute("given_name"));
        userAttributes.put("familyName", principal.getAttribute("family_name"));
        userAttributes.put("email", principal.getAttribute("email"));
        return userAttributes;
    }

    @Operation(description = "Find user by id")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(userMapper.map(user));
    }

    @Operation(description = "Find all users")
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserResponse>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(userMapper.map(users));
    }

    @Operation(description = "Create new user")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        User createdUser = userService.create(userCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.map(createdUser));
    }

    @Operation(description = "Update user by id")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        User updatedUser = userService.update(id, userUpdateRequest);
        return ResponseEntity.ok(userMapper.map(updatedUser));
    }

    @Operation(description = "Delete user by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
