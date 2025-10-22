package dev.talha.sis.student.service;

import dev.talha.sis.student.dto.SectionDto;
import dev.talha.sis.student.entity.Section;
import dev.talha.sis.student.exception.ConflictException;
import dev.talha.sis.student.exception.NotFoundException;
import dev.talha.sis.student.mapper.SectionMapper;
import dev.talha.sis.student.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionService {
    private final SectionRepository sectionRepo;
    private final CourseRepository courseRepo;
    private final InstructorService instructorService; // getOrThrow için
    private final SectionMapper mapper;

    public SectionDto create(SectionDto dto){
        var course = courseRepo.findById(dto.courseId())
                .orElseThrow(() -> new NotFoundException("course_not_found"));
        var instructor = dto.instructorId() == null ? null : instructorService.getOrThrow(dto.instructorId());

        if (instructor != null &&
                sectionRepo.existsByCourse_IdAndSemesterAndInstructor_Id(course.getId(), dto.semester(), instructor.getId())) {
            throw new ConflictException("section_already_exists_for_instructor");
        }

        Section section = mapper.toEntity(dto, course, instructor);
        section = sectionRepo.save(section);
        return mapper.toDto(section);
    }

    public Page<SectionDto> list(Pageable p){
        return sectionRepo.findAll(p).map(mapper::toDto);
    }
}
