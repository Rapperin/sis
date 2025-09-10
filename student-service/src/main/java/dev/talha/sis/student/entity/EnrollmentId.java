package dev.talha.sis.student.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EnrollmentId implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "semester", length = 10, nullable = false)
    private String semester;

    public EnrollmentId() {}

    public EnrollmentId(Long studentId, Long courseId, String semester) {
        this.studentId = studentId;
        this.courseId  = courseId;
        this.semester  = semester;
    }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnrollmentId that)) return false;
        return Objects.equals(studentId, that.studentId)
                && Objects.equals(courseId, that.courseId)
                && Objects.equals(semester, that.semester);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId, semester);
    }
}
