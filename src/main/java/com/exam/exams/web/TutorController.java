package com.exam.exams.web;

import com.exam.exams.mapper.TutorMapper;
import com.exam.exams.model.Tutor;
import com.exam.exams.service.TutorService;
import com.exam.exams.web.request.TutorCreateRequest;
import com.exam.exams.web.response.TutorResponse;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Tutor controller")
@RestController
@RequestMapping("/tutors")
@RequiredArgsConstructor
public class TutorController {

    private final TutorService tutorService;

    private final TutorMapper tutorMapper;

    @Operation(description = "Find tutor by id")
    @GetMapping("/{id}")
    public ResponseEntity<TutorResponse> findById(@PathVariable Long id) {
        Tutor tutor = tutorService.findById(id);
        return ResponseEntity.ok(tutorMapper.map(tutor));
    }

    @Operation(description = "Find all tutors")
    @GetMapping
    public ResponseEntity<List<TutorResponse>> findAll() {
        List<Tutor> tutors = tutorService.findAll();
        return ResponseEntity.ok(tutorMapper.map(tutors));
    }

    @Operation(description = "Add new tutor")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TutorResponse> create(@Valid @RequestBody TutorCreateRequest tutorCreateRequest) {
        Tutor createdTutor = tutorService.create(tutorCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tutorMapper.map(createdTutor));
    }

    @Operation(description = "Update tutor by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tutorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
