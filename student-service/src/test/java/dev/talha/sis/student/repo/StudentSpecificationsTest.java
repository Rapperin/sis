package dev.talha.sis.student.repo;

import dev.talha.sis.student.dto.StudentFilter;
import dev.talha.sis.student.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudentSpecificationsTest {

    @Autowired
    StudentRepository repository;

    @Test
    void filtersByFirstNameAndDateRange() {
        Student alice = Student.builder()
                .firstName("Alice")
                .lastName("Johnson")
                .email("alice@example.com")
                .birthDate(LocalDate.of(2000, 1, 10))
                .build();
        Student bob = Student.builder()
                .firstName("Bob")
                .lastName("Smith")
                .email("bob@example.com")
                .birthDate(LocalDate.of(1995, 5, 20))
                .build();
        repository.save(alice);
        repository.save(bob);

        StudentFilter filter = StudentFilter.builder()
                .firstName("ali")
                .birthDateFrom(LocalDate.of(1999, 1, 1))
                .birthDateTo(LocalDate.of(2001, 12, 31))
                .build();

        Specification<Student> spec = StudentSpecifications.fromFilter(filter);

        Page<Student> result = repository.findAll(spec, PageRequest.of(0, 10));

        assertThat(result.getContent())
                .extracting(Student::getEmail)
                .containsExactly("alice@example.com");
    }

    @Test
    void returnsAllWhenFilterEmpty() {
        Student alice = Student.builder()
                .firstName("Alice")
                .lastName("Johnson")
                .email("alice2@example.com")
                .birthDate(LocalDate.of(2001, 2, 15))
                .build();
        Student bob = Student.builder()
                .firstName("Bob")
                .lastName("Brown")
                .email("bob2@example.com")
                .birthDate(LocalDate.of(1999, 7, 30))
                .build();
        repository.save(alice);
        repository.save(bob);

        Specification<Student> spec = StudentSpecifications.fromFilter(new StudentFilter());

        Page<Student> result = repository.findAll(spec, PageRequest.of(0, 10));

        assertThat(result.getTotalElements()).isEqualTo(2);
    }
}
