package dev.talha.sis.student.service;

import dev.talha.sis.student.dto.EnrollmentDto;
import dev.talha.sis.student.entity.Course;
import dev.talha.sis.student.entity.Enrollment;
import dev.talha.sis.student.entity.Student;
import dev.talha.sis.student.exception.NotFoundException;
import dev.talha.sis.student.repo.CourseRepository;
import dev.talha.sis.student.repo.EnrollmentRepository;
import dev.talha.sis.student.repo.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.talha.sis.student.exception.NotFoundException;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollRepo;
    private final StudentRepository  studentRepo;
    private final CourseRepository   courseRepo;

    public EnrollmentService(EnrollmentRepository enrollRepo,
                             StudentRepository studentRepo,
                             CourseRepository courseRepo) {
        this.enrollRepo  = enrollRepo;
        this.studentRepo = studentRepo;
        this.courseRepo  = courseRepo;
    }

    @Transactional
    public Enrollment enroll(EnrollmentDto dto) {
        Student s = studentRepo.findById(dto.studentId())
                .orElseThrow(() -> new NotFoundException("student_not_found"));

        Course c = courseRepo.findById(dto.courseId())
                .orElseThrow(() -> new NotFoundException("course_not_found"));

        Enrollment e = new Enrollment(s, c, dto.semester());
        return enrollRepo.save(e);
    }

    public Page<Enrollment> listByStudent(Long studentId, Pageable pageable) {
        return enrollRepo.findByStudent_Id(studentId, pageable);
    }

    public Page<Enrollment> listByCourse(Long courseId, Pageable pageable) {
        return enrollRepo.findByCourse_Id(courseId, pageable);
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
