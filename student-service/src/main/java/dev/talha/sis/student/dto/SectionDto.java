package dev.talha.sis.student.dto;

import jakarta.validation.constraints.*;

public record SectionDto(
        Long id,
        @NotNull Long courseId,
        Long instructorId,
        @NotBlank @Size(max=40) String semester,
        @Min(1) @Max(500) int capacity,
        @Size(max=120) String schedule
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Long courseId;
        private Long instructorId;
        private String semester;
        private Integer capacity;
        private String schedule;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder courseId(Long courseId) {
            this.courseId = courseId;
            return this;
        }

        public Builder instructorId(Long instructorId) {
            this.instructorId = instructorId;
            return this;
        }

        public Builder semester(String semester) {
            this.semester = semester;
            return this;
        }

        public Builder capacity(Integer capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder schedule(String schedule) {
            this.schedule = schedule;
            return this;
        }

        public SectionDto build() {
            return new SectionDto(
                    id,
                    courseId,
                    instructorId,
                    semester,
                    capacity != null ? capacity : 0,
                    schedule
            );
        }
    }
}
