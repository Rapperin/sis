package dev.talha.sis.student.repo;

import dev.talha.sis.student.entity.Enrollment;
import dev.talha.sis.student.entity.EnrollmentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {

    // Association üstünden (önerilen)
    @EntityGraph(attributePaths = {"student", "course"})
    Page<Enrollment> findByStudent_Id(Long studentId, Pageable pageable);

    @EntityGraph(attributePaths = {"student", "course"})
    Page<Enrollment> findByCourse_Id(Long courseId, Pageable pageable);

    // İstersen: EmbeddedId üstünden alternatif imzalar
    @EntityGraph(attributePaths = {"student", "course"})
    Page<Enrollment> findByIdStudentId(Long studentId, Pageable pageable);

    @EntityGraph(attributePaths = {"student", "course"})
    Page<Enrollment> findByIdCourseId(Long courseId, Pageable pageable);

    @Transactional
    @Modifying
    void deleteByIdStudentIdAndIdCourseIdAndIdSemester(Long studentId, Long courseId, String semester);

    boolean existsByIdStudentIdAndIdCourseIdAndIdSemester(Long studentId, Long courseId, String semester);

}
