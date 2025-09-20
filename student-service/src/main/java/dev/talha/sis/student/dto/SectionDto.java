package dev.talha.sis.student.dto;

import jakarta.validation.constraints.*;

public record SectionDto(
        Long id,
        @NotNull Long courseId,
        Long instructorId,
        @NotBlank @Size(max=40) String semester,
        @Min(1) @Max(500) int capacity,
        @Size(max=120) String schedule
) {}
