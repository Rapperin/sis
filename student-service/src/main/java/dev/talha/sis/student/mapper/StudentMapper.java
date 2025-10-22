package dev.talha.sis.student.mapper;

import dev.talha.sis.student.dto.StudentDto;
import dev.talha.sis.student.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentDto toDto(Student s) {
        return StudentDto.builder()
                .id(s.getId())
                .firstName(s.getFirstName())
                .lastName(s.getLastName())
                .email(s.getEmail())
                .birthDate(s.getBirthDate())
                .build();
    }

    public Student toEntity(StudentDto d) {
        return Student.builder()
                .id(d.id())
                .firstName(d.firstName())
                .lastName(d.lastName())
                .email(d.email())
                .birthDate(d.birthDate())
                .build();
    }

    public void updateEntity(StudentDto d, Student target) {
        target.setFirstName(d.firstName());
        target.setLastName(d.lastName());
        target.setEmail(d.email());
        target.setBirthDate(d.birthDate());
    }
}
