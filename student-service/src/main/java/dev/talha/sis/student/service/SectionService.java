package dev.talha.sis.student.service;

import dev.talha.sis.student.dto.SectionDto;
import dev.talha.sis.student.entity.Section;
import dev.talha.sis.student.exception.ConflictException;
import dev.talha.sis.student.exception.NotFoundException;
import dev.talha.sis.student.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

@Service @RequiredArgsConstructor
public class SectionService {
    private final SectionRepository sectionRepo;
    private final CourseRepository courseRepo;
    private final InstructorService instructorService; // getOrThrow için

    public SectionDto create(SectionDto dto){
        var course = courseRepo.findById(dto.courseId())
                .orElseThrow(() -> new NotFoundException("course_not_found"));
        var instructor = dto.instructorId() == null ? null : instructorService.getOrThrow(dto.instructorId());

        if (instructor != null &&
                sectionRepo.existsByCourse_IdAndSemesterAndInstructor_Id(course.getId(), dto.semester(), instructor.getId())) {
            throw new ConflictException("section_already_exists_for_instructor");
        }

        var s = Section.builder()
                .course(course)
                .instructor(instructor)
                .semester(dto.semester())
                .capacity(dto.capacity())
                .schedule(dto.schedule())
                .build();

        s = sectionRepo.save(s);
        return new SectionDto(s.getId(), course.getId(), instructor!=null? instructor.getId():null,
                s.getSemester(), s.getCapacity(), s.getSchedule());
    }

    public Page<SectionDto> list(Pageable p){
        return sectionRepo.findAll(p).map(s ->
                new SectionDto(s.getId(),
                        s.getCourse().getId(),
                        s.getInstructor()!=null ? s.getInstructor().getId() : null,
                        s.getSemester(), s.getCapacity(), s.getSchedule()));
    }
}
