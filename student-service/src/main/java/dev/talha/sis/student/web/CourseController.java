package dev.talha.sis.student.web;

import dev.talha.sis.student.dto.CourseDto;
import dev.talha.sis.student.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDto create(@RequestBody @Valid CourseDto dto) {
        return service.create(dto);
    }

    @GetMapping
    public Page<CourseDto> list(@PageableDefault(size = 20) Pageable pageable) {
        return service.list(pageable);
    }
}
