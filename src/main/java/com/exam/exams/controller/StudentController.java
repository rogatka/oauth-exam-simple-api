package com.exam.exams.controller;

import com.exam.exams.model.dto.StudentCreateDto;
import com.exam.exams.model.dto.StudentDto;
import com.exam.exams.model.dto.StudentUpdateDto;
import com.exam.exams.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Student Controller")
@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @Operation(description = "Find student by id")
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    @Operation(description = "Find all students")
    @GetMapping
    public ResponseEntity<List<StudentDto>> findAll() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @Operation(description = "Add new student")
    @PostMapping
    @PreAuthorize("@userPrincipalService.isTutor()")
    public ResponseEntity<StudentDto> create(@Valid @RequestBody StudentCreateDto studentCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.create(studentCreateDto));
    }

    @Operation(description = "Update student by id")
    @PutMapping("/{id}")
    @PreAuthorize("@userPrincipalService.isTutor()")
    public ResponseEntity<StudentDto> update(@PathVariable Long id,
                                             @Valid @RequestBody StudentUpdateDto studentUpdateDto) {
        return ResponseEntity.ok(studentService.update(id, studentUpdateDto));
    }

    @Operation(description = "Delete student by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("@userPrincipalService.isTutor()")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
