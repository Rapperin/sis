package dev.talha.sis.student.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class StudentFilter {

    private String firstName;
    private String lastName;
    private String email;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDateTo;

    public StudentFilter() {
    }

    private StudentFilter(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.birthDateFrom = builder.birthDateFrom;
        this.birthDateTo = builder.birthDateTo;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDateFrom() {
        return birthDateFrom;
    }

    public void setBirthDateFrom(LocalDate birthDateFrom) {
        this.birthDateFrom = birthDateFrom;
    }

    public LocalDate getBirthDateTo() {
        return birthDateTo;
    }

    public void setBirthDateTo(LocalDate birthDateTo) {
        this.birthDateTo = birthDateTo;
    }

    public static final class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private LocalDate birthDateFrom;
        private LocalDate birthDateTo;

        private Builder() {
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

        public Builder birthDateFrom(LocalDate birthDateFrom) {
            this.birthDateFrom = birthDateFrom;
            return this;
        }

        public Builder birthDateTo(LocalDate birthDateTo) {
            this.birthDateTo = birthDateTo;
            return this;
        }

        public StudentFilter build() {
            return new StudentFilter(this);
        }
    }
}
