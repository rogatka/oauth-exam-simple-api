package com.exam.exams.controller;

import com.exam.exams.model.dto.TutorCreateDto;
import com.exam.exams.model.dto.TutorDto;
import com.exam.exams.service.TutorService;
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

    @Operation(description = "Find tutor by id")
    @GetMapping("/{id}")
    public ResponseEntity<TutorDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tutorService.findById(id));
    }

    @Operation(description = "Find all tutors")
    @GetMapping
    public ResponseEntity<List<TutorDto>> findAll() {
        return ResponseEntity.ok(tutorService.findAll());
    }

    @Operation(description = "Add new tutor")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TutorDto> create(@Valid @RequestBody TutorCreateDto tutorCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tutorService.create(tutorCreateDto));
    }

    @Operation(description = "Update tutor by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tutorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
