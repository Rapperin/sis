package dev.talha.sis.student.web;

import dev.talha.sis.student.dto.SectionDto;
import dev.talha.sis.student.service.SectionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sections")
public class SectionController {
    private final SectionService service;
    public SectionController(SectionService s){ this.service = s; }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public SectionDto create(@Valid @RequestBody SectionDto dto){ return service.create(dto); }

    @GetMapping
    public Page<SectionDto> list(@PageableDefault(size=20) Pageable p){ return service.list(p); }
}
