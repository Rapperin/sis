package dev.talha.sis.student.service;

import dev.talha.sis.student.dto.StudentDto;
import dev.talha.sis.student.entity.Student;
import dev.talha.sis.student.exception.ConflictException;
import dev.talha.sis.student.exception.NotFoundException;
import dev.talha.sis.student.repo.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public StudentDto create(StudentDto dto) {
        repo.findByEmail(dto.email()).ifPresent(s -> {
            throw new ConflictException("email_exists");
        });
        Student s = toEntity(dto);
        s = repo.save(s);
        return toDto(s);
    }

    public Page<StudentDto> list(Pageable p) {
        return repo.findAll(p).map(this::toDto);
    }

    public StudentDto get(Long id) {
        return toDto(
                repo.findById(id)
                        .orElseThrow(() -> new NotFoundException("student_not_found"))
        );
    }

    public StudentDto update(Long id, StudentDto dto) {
        Student s = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("student_not_found"));
        s.setFirstName(dto.firstName());
        s.setLastName(dto.lastName());
        s.setEmail(dto.email());
        s.setBirthDate(dto.birthDate());
        return toDto(repo.save(s));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    private StudentDto toDto(Student s) {
        return new StudentDto(
                s.getId(),
                s.getFirstName(),
                s.getLastName(),
                s.getEmail(),
                s.getBirthDate()
        );
    }

    private Student toEntity(StudentDto d) {
        Student s = new Student();
        s.setId(d.id());
        s.setFirstName(d.firstName());
        s.setLastName(d.lastName());
        s.setEmail(d.email());
        s.setBirthDate(d.birthDate());
        return s;
    }
}
