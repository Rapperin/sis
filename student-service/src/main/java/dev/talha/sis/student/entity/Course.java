package dev.talha.sis.student.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String name;

    @Min(1)
    @Max(15)
    @Column(nullable = false)
    private Integer credit;

    public Course() {}

    public Course(String code, String name, int credit) {
        this.code = code;
        this.name = name;
        this.credit = credit;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getCredit() { return credit; }
    public void setCredit(Integer credit) { this.credit = credit; }

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

        public Course build() {
            Course course = new Course();
            course.setId(id);
            course.setCode(code);
            course.setName(name);
            course.setCredit(credit);
            return course;
        }
    }
}
