package dev.talha.sis.student.mapper;

import dev.talha.sis.student.dto.SectionDto;
import dev.talha.sis.student.entity.Course;
import dev.talha.sis.student.entity.Instructor;
import dev.talha.sis.student.entity.Section;
import org.springframework.stereotype.Component;

@Component
public class SectionMapper {

    public SectionDto toDto(Section section) {
        return SectionDto.builder()
                .id(section.getId())
                .courseId(section.getCourse().getId())
                .instructorId(section.getInstructor() != null ? section.getInstructor().getId() : null)
                .semester(section.getSemester())
                .capacity(section.getCapacity())
                .schedule(section.getSchedule())
                .build();
    }

    public Section toEntity(SectionDto dto, Course course, Instructor instructor) {
        return Section.builder()
                .id(dto.id())
                .course(course)
                .instructor(instructor)
                .semester(dto.semester())
                .capacity(dto.capacity())
                .schedule(dto.schedule())
                .build();
    }

    public void updateEntity(SectionDto dto, Section target, Course course, Instructor instructor) {
        target.setCourse(course);
        target.setInstructor(instructor);
        target.setSemester(dto.semester());
        target.setCapacity(dto.capacity());
        target.setSchedule(dto.schedule());
    }
}
