package dev.talha.sis.student.mapper;

import dev.talha.sis.student.dto.EnrollmentDto;
import dev.talha.sis.student.entity.Course;
import dev.talha.sis.student.entity.Enrollment;
import dev.talha.sis.student.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {

    public EnrollmentDto toDto(Enrollment enrollment) {
        return EnrollmentDto.builder()
                .studentId(enrollment.getId().getStudentId())
                .courseId(enrollment.getId().getCourseId())
                .semester(enrollment.getId().getSemester())
                .build();
    }

    public Enrollment toEntity(Student student, Course course, String semester) {
        return Enrollment.builder()
                .student(student)
                .course(course)
                .semester(semester)
                .build();
    }
}
