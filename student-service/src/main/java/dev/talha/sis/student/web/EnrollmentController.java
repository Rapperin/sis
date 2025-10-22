package dev.talha.sis.student.web;

import dev.talha.sis.student.dto.EnrollmentDto;
import dev.talha.sis.student.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentDto enroll(@RequestBody @Valid EnrollmentDto dto) {
        return service.enroll(dto);
    }

    @GetMapping("/by-student/{studentId}")
    public Page<EnrollmentDto> listByStudent(@PathVariable("studentId") Long studentId,
                                          @PageableDefault(size = 20) Pageable pageable) {
        return service.listByStudent(studentId, pageable);
    }

    @GetMapping("/by-course/{courseId}")
    public Page<EnrollmentDto> listByCourse(@PathVariable("courseId") Long courseId,
                                         @PageableDefault(size = 20) Pageable pageable) {
        return service.listByCourse(courseId, pageable);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @RequestParam("studentId") Long studentId,
            @RequestParam("courseId")  Long courseId,
            @RequestParam("semester") String semester
    ) {
        service.delete(studentId, courseId, semester);
    }


}
