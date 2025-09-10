package dev.talha.sis.student.dto;

import jakarta.validation.constraints.*;

public record CourseDto(
        @NotBlank @Size(max=20) String code,
        @NotBlank @Size(max=150) String name,
        @Min(1) @Max(15) int credit
) {}
