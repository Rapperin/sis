package dev.talha.sis.student.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record EnrollmentDto(
        @NotNull Long studentId,
        @NotNull Long courseId,
        @NotBlank String semester
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long studentId;
        private Long courseId;
        private String semester;

        private Builder() {
        }

        public Builder studentId(Long studentId) {
            this.studentId = studentId;
            return this;
        }

        public Builder courseId(Long courseId) {
            this.courseId = courseId;
            return this;
        }

        public Builder semester(String semester) {
            this.semester = semester;
            return this;
        }

        public EnrollmentDto build() {
            return new EnrollmentDto(studentId, courseId, semester);
        }
    }
}
