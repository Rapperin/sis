package dev.talha.sis.student.service;

import dev.talha.sis.student.dto.CourseDto;
import dev.talha.sis.student.entity.Course;
import dev.talha.sis.student.exception.ConflictException;
import dev.talha.sis.student.mapper.CourseMapper;
import dev.talha.sis.student.repo.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {
    private final CourseRepository repo;
    private final CourseMapper mapper;

    public CourseService(CourseRepository repo, CourseMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Transactional
    public CourseDto create(CourseDto dto) {
        if (repo.existsByCode(dto.code())) {
            throw new ConflictException("course_code_exists");
        }
        Course course = mapper.toEntity(dto);
        course = repo.save(course);
        return mapper.toDto(course);
    }

    public Page<CourseDto> list(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }
}
