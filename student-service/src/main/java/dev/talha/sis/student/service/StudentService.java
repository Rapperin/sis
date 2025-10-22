package dev.talha.sis.student.service;

import dev.talha.sis.student.dto.StudentDto;
import dev.talha.sis.student.entity.Student;
import dev.talha.sis.student.exception.ConflictException;
import dev.talha.sis.student.exception.NotFoundException;
import dev.talha.sis.student.mapper.StudentMapper;
import dev.talha.sis.student.repo.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository repo;
    private final StudentMapper mapper;

    public StudentService(StudentRepository repo, StudentMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public StudentDto create(StudentDto dto) {
        repo.findByEmail(dto.email()).ifPresent(s -> {
            throw new ConflictException("email_exists");
        });
        Student s = mapper.toEntity(dto);
        s = repo.save(s);
        return mapper.toDto(s);
    }

    public Page<StudentDto> list(Pageable p) {
        return repo.findAll(p).map(mapper::toDto);
    }

    public StudentDto get(Long id) {
        return mapper.toDto(
                repo.findById(id)
                        .orElseThrow(() -> new NotFoundException("student_not_found"))
        );
    }

    public StudentDto update(Long id, StudentDto dto) {
        Student s = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("student_not_found"));
        mapper.updateEntity(dto, s);
        return mapper.toDto(repo.save(s));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
