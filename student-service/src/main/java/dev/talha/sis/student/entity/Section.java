package dev.talha.sis.student.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "section",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_section_course_semester_instructor",
                        columnNames = {"course_id", "semester", "instructor_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @Column(nullable = false, length = 20)
    private String semester;

    @Column(nullable = false)
    private int capacity;

    @Column(length = 50)
    private String schedule;
}
