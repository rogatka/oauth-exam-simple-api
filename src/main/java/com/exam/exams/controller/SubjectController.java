package com.exam.exams.controller;

import com.exam.exams.model.dto.SubjectCreateDto;
import com.exam.exams.model.dto.SubjectDto;
import com.exam.exams.model.dto.SubjectUpdateDto;
import com.exam.exams.service.SubjectService;
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

@Tag(name = "Subject controller")
@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;


    @Operation(description = "Find subject by id")
    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.findById(id));
    }

    @Operation(description = "Find all subjects")
    @GetMapping
    public ResponseEntity<List<SubjectDto>> findAll() {
        return ResponseEntity.ok(subjectService.findAll());
    }

    @Operation(description = "Add new subject")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SubjectDto> create(@Valid @RequestBody SubjectCreateDto subjectCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subjectService.create(subjectCreateDto));
    }

    @Operation(description = "Update subject by id")
    @PutMapping("/{id}")
    @PreAuthorize("@subjectAccessService.isSubjectTutor(#id)")
    public ResponseEntity<SubjectDto> update(@PathVariable Long id,
                                             @Valid @RequestBody SubjectUpdateDto subjectUpdateDto) {
        return ResponseEntity.ok(subjectService.update(id, subjectUpdateDto));
    }

    @Operation(description = "Assign tutor to subject")
    @PostMapping("/{subjectId}/tutors/{tutorId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SubjectDto> assignTutor(@PathVariable Long subjectId,
                                                  @PathVariable Long tutorId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subjectService.assignTutor(subjectId, tutorId));
    }

    @Operation(description = "Remove tutor from subject's  tutors")
    @DeleteMapping("/{subjectId}/tutors/{tutorId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SubjectDto> removeTutor(@PathVariable Long subjectId,
                                                  @PathVariable Long tutorId) {
        return ResponseEntity.ok(subjectService.removeTutor(subjectId, tutorId));
    }

    @Operation(description = "Delete subject")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
