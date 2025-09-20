package dev.talha.sis.student.service;

import dev.talha.sis.student.dto.InstructorDto;
import dev.talha.sis.student.entity.Instructor;
import dev.talha.sis.student.exception.ConflictException;
import dev.talha.sis.student.exception.NotFoundException;
import dev.talha.sis.student.repo.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

@Service @RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository repo;

    public InstructorDto create(InstructorDto dto){
        if (repo.existsByEmail(dto.email())) throw new ConflictException("email_exists");
        var e = repo.save(Instructor.builder()
                .firstName(dto.firstName()).lastName(dto.lastName()).email(dto.email()).build());
        return new InstructorDto(e.getId(), e.getFirstName(), e.getLastName(), e.getEmail());
    }

    public Page<InstructorDto> list(Pageable p){
        return repo.findAll(p).map(e -> new InstructorDto(e.getId(), e.getFirstName(), e.getLastName(), e.getEmail()));
    }

    public Instructor getOrThrow(Long id){
        return repo.findById(id).orElseThrow(() -> new NotFoundException("instructor_not_found"));
    }
}
