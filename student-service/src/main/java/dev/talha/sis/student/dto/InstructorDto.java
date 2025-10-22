package dev.talha.sis.student.dto;

import jakarta.validation.constraints.*;

public record InstructorDto(
        Long id,
        @NotBlank @Size(max=100) String firstName,
        @NotBlank @Size(max=100) String lastName,
        @Email String email
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;

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

        public InstructorDto build() {
            return new InstructorDto(id, firstName, lastName, email);
        }
    }
}
