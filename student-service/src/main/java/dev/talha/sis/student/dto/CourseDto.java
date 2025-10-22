package dev.talha.sis.student.dto;

import jakarta.validation.constraints.*;

public record CourseDto(
        Long id,
        @NotBlank @Size(max = 20) String code,
        @NotBlank @Size(max = 150) String name,
        @Min(1) @Max(15) int credit
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String code;
        private String name;
        private Integer credit;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder credit(Integer credit) {
            this.credit = credit;
            return this;
        }

        public CourseDto build() {
            return new CourseDto(id, code, name, credit != null ? credit : 0);
        }
    }
}
