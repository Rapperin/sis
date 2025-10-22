package dev.talha.sis.student.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name", nullable=false)
    private String firstName;

    @Column(name="last_name", nullable=false)
    private String lastName;

    @Column(name="email", nullable=false, unique=true)
    private String email;

    @Column(name="birth_date")
    private LocalDate birthDate;

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public String getFirstName(){ return firstName; }
    public void setFirstName(String v){ this.firstName = v; }

    public String getLastName(){ return lastName; }
    public void setLastName(String v){ this.lastName = v; }

    public String getEmail(){ return email; }
    public void setEmail(String v){ this.email = v; }

    public LocalDate getBirthDate(){ return birthDate; }
    public void setBirthDate(LocalDate v){ this.birthDate = v; }

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

        public Student build() {
            Student student = new Student();
            student.setId(id);
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setEmail(email);
            student.setBirthDate(birthDate);
            return student;
        }
    }
}
