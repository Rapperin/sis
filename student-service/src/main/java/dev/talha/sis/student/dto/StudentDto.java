package dev.talha.sis.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record StudentDto(
        Long id,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email String email,
        LocalDate birthDate
) {}
