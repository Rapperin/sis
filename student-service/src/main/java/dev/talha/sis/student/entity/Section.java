package dev.talha.sis.student.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Section {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @Column(nullable = false, length = 40)
    private String semester;

    @Column(nullable = false)
    private int capacity = 40;

    @Column(length = 120)
    private String schedule; // basit gösterim
}
