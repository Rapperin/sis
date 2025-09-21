package dev.talha.sis.student.web;

import dev.talha.sis.student.dto.EnrollmentDto;
import dev.talha.sis.student.entity.Enrollment;
import dev.talha.sis.student.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Enrollment enroll(@RequestBody @Valid EnrollmentDto dto) {
        return service.enroll(dto);
    }

    @GetMapping("/by-student/{studentId}")
    public Page<Enrollment> listByStudent(@PathVariable("studentId") Long studentId,
                                          Pageable pageable) {
        return service.listByStudent(studentId, pageable);
    }

    @GetMapping("/by-course/{courseId}")
    public Page<Enrollment> listByCourse(@PathVariable("courseId") Long courseId,
                                         Pageable pageable) {
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
