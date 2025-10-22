package dev.talha.sis.student.service;

import dev.talha.sis.student.dto.EnrollmentDto;
import dev.talha.sis.student.entity.Course;
import dev.talha.sis.student.entity.Enrollment;
import dev.talha.sis.student.entity.Student;
import dev.talha.sis.student.exception.NotFoundException;
import dev.talha.sis.student.mapper.EnrollmentMapper;
import dev.talha.sis.student.repo.CourseRepository;
import dev.talha.sis.student.repo.EnrollmentRepository;
import dev.talha.sis.student.repo.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollRepo;
    private final StudentRepository  studentRepo;
    private final CourseRepository   courseRepo;
    private final EnrollmentMapper mapper;

    public EnrollmentService(EnrollmentRepository enrollRepo,
                             StudentRepository studentRepo,
                             CourseRepository courseRepo,
                             EnrollmentMapper mapper) {
        this.enrollRepo  = enrollRepo;
        this.studentRepo = studentRepo;
        this.courseRepo  = courseRepo;
        this.mapper      = mapper;
    }

    @Transactional
    public EnrollmentDto enroll(EnrollmentDto dto) {
        Student s = studentRepo.findById(dto.studentId())
                .orElseThrow(() -> new NotFoundException("student_not_found"));

        Course c = courseRepo.findById(dto.courseId())
                .orElseThrow(() -> new NotFoundException("course_not_found"));

        Enrollment e = mapper.toEntity(s, c, dto.semester());
        e = enrollRepo.save(e);
        return mapper.toDto(e);
    }

    public Page<EnrollmentDto> listByStudent(Long studentId, Pageable pageable) {
        return enrollRepo.findByStudent_Id(studentId, pageable).map(mapper::toDto);
    }

    public Page<EnrollmentDto> listByCourse(Long courseId, Pageable pageable) {
        return enrollRepo.findByCourse_Id(courseId, pageable).map(mapper::toDto);
    }

    @Transactional
    public void delete(Long studentId, Long courseId, String semester) {
        boolean exists = enrollRepo.existsByIdStudentIdAndIdCourseIdAndIdSemester(studentId, courseId, semester);
        if (!exists) {
            throw new NotFoundException("enrollment_not_found");
        }
        enrollRepo.deleteByIdStudentIdAndIdCourseIdAndIdSemester(studentId, courseId, semester);
    }



}
