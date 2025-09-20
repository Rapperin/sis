package dev.talha.sis.student.dto;

import jakarta.validation.constraints.*;

public record InstructorDto(
        Long id,
        @NotBlank @Size(max=100) String firstName,
        @NotBlank @Size(max=100) String lastName,
        @Email String email
) {}
