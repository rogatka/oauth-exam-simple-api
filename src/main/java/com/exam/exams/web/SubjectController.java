package com.exam.exams.web;

import com.exam.exams.mapper.SubjectMapper;
import com.exam.exams.model.Subject;
import com.exam.exams.service.SubjectService;
import com.exam.exams.web.request.SubjectCreateRequest;
import com.exam.exams.web.request.SubjectUpdateRequest;
import com.exam.exams.web.response.SubjectResponse;
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

    private final SubjectMapper subjectMapper;


    @Operation(description = "Find subject by id")
    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponse> findById(@PathVariable Long id) {
        Subject subject = subjectService.findById(id);
        return ResponseEntity.ok(subjectMapper.map(subject));
    }

    @Operation(description = "Find all subjects")
    @GetMapping
    public ResponseEntity<List<SubjectResponse>> findAll() {
        List<Subject> subjects = subjectService.findAll();
        return ResponseEntity.ok(subjectMapper.map(subjects));
    }

    @Operation(description = "Add new subject")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SubjectResponse> create(@Valid @RequestBody SubjectCreateRequest subjectCreateRequest) {
        Subject createdSubject = subjectService.create(subjectCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subjectMapper.map(createdSubject));
    }

    @Operation(description = "Update subject by id")
    @PutMapping("/{id}")
    @PreAuthorize("@subjectAccessService.isSubjectTutor(#id)")
    public ResponseEntity<SubjectResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody SubjectUpdateRequest subjectUpdateRequest) {
        Subject updatedSubject = subjectService.update(id, subjectUpdateRequest);
        return ResponseEntity.ok(subjectMapper.map(updatedSubject));
    }

    @Operation(description = "Assign tutor to subject")
    @PostMapping("/{subjectId}/tutors/{tutorId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SubjectResponse> assignTutor(@PathVariable Long subjectId,
                                                       @PathVariable Long tutorId) {
        Subject subjectWithTutorAssigned = subjectService.assignTutor(subjectId, tutorId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subjectMapper.map(subjectWithTutorAssigned));
    }

    @Operation(description = "Remove tutor from subject's  tutors")
    @DeleteMapping("/{subjectId}/tutors/{tutorId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SubjectResponse> removeTutor(@PathVariable Long subjectId,
                                                       @PathVariable Long tutorId) {
        Subject subjectWithTutorRemoved = subjectService.removeTutor(subjectId, tutorId);
        return ResponseEntity.ok(subjectMapper.map(subjectWithTutorRemoved));
    }

    @Operation(description = "Delete subject")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
