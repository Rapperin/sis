package dev.talha.sis.student.web;

import dev.talha.sis.student.dto.InstructorDto;
import dev.talha.sis.student.service.InstructorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/instructors")
public class InstructorController {
    private final InstructorService service;
    public InstructorController(InstructorService s){ this.service = s; }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public InstructorDto create(@Valid @RequestBody InstructorDto dto){ return service.create(dto); }

    @GetMapping
    public Page<InstructorDto> list(@PageableDefault(size=20) Pageable p){ return service.list(p); }
}
