package dev.talha.sis.student.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record EnrollmentDto(
        @NotNull Long studentId,
        @NotNull Long courseId,
        @NotBlank String semester
) {}
