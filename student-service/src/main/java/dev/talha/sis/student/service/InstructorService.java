package dev.talha.sis.student.service;

import dev.talha.sis.student.dto.InstructorDto;
import dev.talha.sis.student.entity.Instructor;
import dev.talha.sis.student.exception.ConflictException;
import dev.talha.sis.student.exception.NotFoundException;
import dev.talha.sis.student.mapper.InstructorMapper;
import dev.talha.sis.student.repo.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository repo;
    private final InstructorMapper mapper;

    public InstructorDto create(InstructorDto dto){
        if (repo.existsByEmail(dto.email())) throw new ConflictException("email_exists");
        Instructor instructor = mapper.toEntity(dto);
        instructor = repo.save(instructor);
        return mapper.toDto(instructor);
    }

    public Page<InstructorDto> list(Pageable p){
        return repo.findAll(p).map(mapper::toDto);
    }

    public Instructor getOrThrow(Long id){
        return repo.findById(id).orElseThrow(() -> new NotFoundException("instructor_not_found"));
    }
}
