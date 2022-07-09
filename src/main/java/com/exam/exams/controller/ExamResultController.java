package com.exam.exams.controller;

import com.exam.exams.model.dto.ExamResultCreateDto;
import com.exam.exams.model.dto.ExamResultDto;
import com.exam.exams.model.dto.ExamResultUpdateDto;
import com.exam.exams.service.ExamResultService;
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

@Tag(name = "Exam result controller")
@RestController
@RequestMapping("/exam-results")
@RequiredArgsConstructor
public class ExamResultController {

    private final ExamResultService examResultService;

    @Operation(description = "Find exam result by id")
    @GetMapping("/{id}")
    public ResponseEntity<ExamResultDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(examResultService.findById(id));
    }

    @Operation(description = "Find all exam results")
    @GetMapping
    public ResponseEntity<List<ExamResultDto>> findAll() {
        return ResponseEntity.ok(examResultService.findAll());
    }

    @Operation(description = "Add new exam result")
    @PostMapping
    @PreAuthorize("@subjectAccessService.isSubjectTutor(#examResultCreateDto.subjectId)")
    public ResponseEntity<ExamResultDto> create(@Valid @RequestBody ExamResultCreateDto examResultCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(examResultService.create(examResultCreateDto));
    }

    @Operation(description = "Update exam result by id")
    @PutMapping("/{id}")
    @PreAuthorize("@userPrincipalService.isTutor()")
    public ResponseEntity<ExamResultDto> update(@PathVariable Long id,
                                                @Valid @RequestBody ExamResultUpdateDto examResultUpdateDto) {
        return ResponseEntity.ok(examResultService.update(id, examResultUpdateDto));
    }

    @Operation(description = "Delete exam result by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("@userPrincipalService.isTutor()")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        examResultService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
