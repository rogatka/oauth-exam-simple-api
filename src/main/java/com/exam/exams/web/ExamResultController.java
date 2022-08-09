package com.exam.exams.web;

import com.exam.exams.mapper.ExamResultMapper;
import com.exam.exams.model.ExamResult;
import com.exam.exams.web.request.ExamResultCreateRequest;
import com.exam.exams.web.response.ExamResultResponse;
import com.exam.exams.web.request.ExamResultUpdateRequest;
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

    private final ExamResultMapper examResultMapper;

    @Operation(description = "Find exam result by id")
    @GetMapping("/{id}")
    public ResponseEntity<ExamResultResponse> findById(@PathVariable Long id) {
        ExamResult examResult = examResultService.findById(id);
        return ResponseEntity.ok(examResultMapper.map(examResult));
    }

    @Operation(description = "Find all exam results")
    @GetMapping
    public ResponseEntity<List<ExamResultResponse>> findAll() {
        List<ExamResult> examResults = examResultService.findAll();
        return ResponseEntity.ok(examResultMapper.map(examResults));
    }

    @Operation(description = "Add new exam result")
    @PostMapping
    @PreAuthorize("@subjectAccessService.isSubjectTutor(#examResultCreateDto.subjectId)")
    public ResponseEntity<ExamResultResponse> create(@Valid @RequestBody ExamResultCreateRequest examResultCreateRequest) {
        ExamResult createdExamResult = examResultService.create(examResultCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(examResultMapper.map(createdExamResult));
    }

    @Operation(description = "Update exam result by id")
    @PutMapping("/{id}")
    @PreAuthorize("@userPrincipalService.isTutor()")
    public ResponseEntity<ExamResultResponse> update(@PathVariable Long id,
                                                     @Valid @RequestBody ExamResultUpdateRequest examResultUpdateRequest) {
        ExamResult updatedExamResult = examResultService.update(id, examResultUpdateRequest);
        return ResponseEntity.ok(examResultMapper.map(updatedExamResult));
    }

    @Operation(description = "Delete exam result by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("@userPrincipalService.isTutor()")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        examResultService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
