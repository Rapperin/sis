package dev.talha.sis.student.web;

import dev.talha.sis.student.dto.StudentDto;
import dev.talha.sis.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService service;
    public StudentController(StudentService service){ this.service = service; }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public StudentDto create(@Valid @RequestBody StudentDto dto){ return service.create(dto); }

    @GetMapping
    public Page<StudentDto> list(@PageableDefault(size=20) Pageable p){ return service.list(p); }

    @GetMapping("/{id}")
    public StudentDto get(@PathVariable Long id){ return service.get(id); }

    @PutMapping("/{id}")
    public StudentDto update(@PathVariable Long id, @Valid @RequestBody StudentDto dto){ return service.update(id, dto); }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){ service.delete(id); }
}
