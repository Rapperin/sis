// CourseService.java
package dev.talha.sis.student.service;

import dev.talha.sis.student.dto.CourseDto;
import dev.talha.sis.student.entity.Course;
import dev.talha.sis.student.exception.ConflictException;
import dev.talha.sis.student.repo.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {
    private final CourseRepository repo;

    public CourseService(CourseRepository repo){ this.repo=repo; }

    @Transactional
    public Course create(CourseDto dto){
        if (repo.existsByCode(dto.code())) throw new ConflictException("code already exists");
        Course c = new Course();
        c.setCode(dto.code()); c.setName(dto.name()); c.setCredit(dto.credit());
        return repo.save(c);
    }

    public Page<Course> list(Pageable pageable){ return repo.findAll(pageable); }
}
