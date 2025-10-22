package dev.talha.sis.student.mapper;

import dev.talha.sis.student.dto.CourseDto;
import dev.talha.sis.student.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDto toDto(Course c) {
        return CourseDto.builder()
                .id(c.getId())
                .code(c.getCode())
                .name(c.getName())
                .credit(c.getCredit())
                .build();
    }

    public Course toEntity(CourseDto dto) {
        return Course.builder()
                .id(dto.id())
                .code(dto.code())
                .name(dto.name())
                .credit(dto.credit())
                .build();
    }

    public void updateEntity(CourseDto dto, Course target) {
        target.setCode(dto.code());
        target.setName(dto.name());
        target.setCredit(dto.credit());
    }
}
