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
}
