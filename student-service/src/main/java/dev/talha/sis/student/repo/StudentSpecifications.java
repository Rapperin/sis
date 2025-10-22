package dev.talha.sis.student.repo;

import dev.talha.sis.student.dto.StudentFilter;
import dev.talha.sis.student.entity.Student;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public final class StudentSpecifications {

    private StudentSpecifications() {
    }

    public static Specification<Student> fromFilter(StudentFilter filter) {
        Specification<Student> spec = Specification.where(null);

        if (filter == null) {
            return spec;
        }

        if (StringUtils.hasText(filter.getFirstName())) {
            spec = spec.and(likeIgnoreCase("firstName", filter.getFirstName()));
        }

        if (StringUtils.hasText(filter.getLastName())) {
            spec = spec.and(likeIgnoreCase("lastName", filter.getLastName()));
        }

        if (StringUtils.hasText(filter.getEmail())) {
            spec = spec.and(equalsIgnoreCase("email", filter.getEmail()));
        }

        if (filter.getBirthDateFrom() != null) {
            spec = spec.and(greaterThanOrEqualTo("birthDate", filter.getBirthDateFrom()));
        }

        if (filter.getBirthDateTo() != null) {
            spec = spec.and(lessThanOrEqualTo("birthDate", filter.getBirthDateTo()));
        }

        return spec;
    }

    private static Specification<Student> likeIgnoreCase(String attribute, String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(attribute)), "%" + value.toLowerCase() + "%");
    }

    private static Specification<Student> equalsIgnoreCase(String attribute, String value) {
        return (root, query, cb) -> cb.equal(cb.lower(root.get(attribute)), value.toLowerCase());
    }

    private static Specification<Student> greaterThanOrEqualTo(String attribute, LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(attribute), value);
    }

    private static Specification<Student> lessThanOrEqualTo(String attribute, LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(attribute), value);
    }
}
