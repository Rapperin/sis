package dev.talha.sis.student.it.repo;

import dev.talha.sis.student.entity.Student;
import dev.talha.sis.student.entity.Course;
import dev.talha.sis.student.entity.Enrollment;
import dev.talha.sis.student.repo.StudentRepository;
import dev.talha.sis.student.repo.CourseRepository;
import dev.talha.sis.student.repo.EnrollmentRepository;
import dev.talha.sis.student.it.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class EnrollmentRepositoryIT extends AbstractIntegrationTest {

    @Autowired StudentRepository studentRepo;
    @Autowired CourseRepository courseRepo;
    @Autowired EnrollmentRepository enrollmentRepo;

    @Test
    void saveAndLoadEnrollment() {
        Student ada = new Student();
        ada.setFirstName("Ada");
        ada.setLastName("Lovelace");
        ada.setEmail("ada@test.local");
        ada.setBirthDate(LocalDate.of(1815, 12, 10));
        ada = studentRepo.save(ada);

        Course cs101 = new Course("CS101", "Intro to CS", 6);
        cs101 = courseRepo.save(cs101);

        Enrollment e = new Enrollment(ada, cs101, "2025F");
        enrollmentRepo.save(e);

        assertThat(enrollmentRepo.findByStudent_Id(ada.getId(), null))
                .isNotEmpty();
    }
}
