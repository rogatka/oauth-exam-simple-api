package com.exam.exams.web;

import com.exam.exams.mapper.StudentMapper;
import com.exam.exams.model.Student;
import com.exam.exams.web.request.StudentCreateRequest;
import com.exam.exams.web.response.StudentResponse;
import com.exam.exams.web.request.StudentUpdateRequest;
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

    private final StudentMapper studentMapper;

    @Operation(description = "Find student by id")
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> findById(@PathVariable Long id) {
        Student student = studentService.findById(id);
        return ResponseEntity.ok(studentMapper.map(student));
    }

    @Operation(description = "Find all students")
    @GetMapping
    public ResponseEntity<List<StudentResponse>> findAll() {
        List<Student> students = studentService.findAll();
        return ResponseEntity.ok(studentMapper.map(students));
    }

    @Operation(description = "Add new student")
    @PostMapping
    @PreAuthorize("@userPrincipalService.isTutor()")
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentCreateRequest studentCreateRequest) {
        Student createdStudent = studentService.create(studentCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentMapper.map(createdStudent));
    }

    @Operation(description = "Update student by id")
    @PutMapping("/{id}")
    @PreAuthorize("@userPrincipalService.isTutor()")
    public ResponseEntity<StudentResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody StudentUpdateRequest studentUpdateRequest) {
        Student updatedStudent = studentService.update(id, studentUpdateRequest);
        return ResponseEntity.ok(studentMapper.map(updatedStudent));
    }

    @Operation(description = "Delete student by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("@userPrincipalService.isTutor()")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
