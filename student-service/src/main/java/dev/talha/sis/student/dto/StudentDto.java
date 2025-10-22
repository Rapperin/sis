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
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private LocalDate birthDate;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public StudentDto build() {
            return new StudentDto(id, firstName, lastName, email, birthDate);
        }
    }
}
