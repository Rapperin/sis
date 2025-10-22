package dev.talha.sis.student.mapper;

import dev.talha.sis.student.dto.InstructorDto;
import dev.talha.sis.student.entity.Instructor;
import org.springframework.stereotype.Component;

@Component
public class InstructorMapper {

    public InstructorDto toDto(Instructor instructor) {
        return InstructorDto.builder()
                .id(instructor.getId())
                .firstName(instructor.getFirstName())
                .lastName(instructor.getLastName())
                .email(instructor.getEmail())
                .build();
    }

    public Instructor toEntity(InstructorDto dto) {
        return Instructor.builder()
                .id(dto.id())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .build();
    }

    public void updateEntity(InstructorDto dto, Instructor target) {
        target.setFirstName(dto.firstName());
        target.setLastName(dto.lastName());
        target.setEmail(dto.email());
    }
}
