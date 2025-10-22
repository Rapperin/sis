package dev.talha.sis.student.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @EmbeddedId
    private EnrollmentId id;

    @MapsId("studentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Student student;

    @MapsId("courseId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Course course;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String status = "ENROLLED";

    public Enrollment() {}

    public Enrollment(Student student, Course course, String semester) {
        this.student = student;
        this.course  = course;
        this.id      = new EnrollmentId(student.getId(), course.getId(), semester);
    }

    public EnrollmentId getId() { return id; }
    public void setId(EnrollmentId id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private EnrollmentId id;
        private Student student;
        private Course course;
        private Long studentId;
        private Long courseId;
        private String semester;
        private String status = "ENROLLED";

        private Builder() {
        }

        public Builder id(EnrollmentId id) {
            this.id = id;
            return this;
        }

        public Builder student(Student student) {
            this.student = student;
            return this;
        }

        public Builder course(Course course) {
            this.course = course;
            return this;
        }

        public Builder studentId(Long studentId) {
            this.studentId = studentId;
            return this;
        }

        public Builder courseId(Long courseId) {
            this.courseId = courseId;
            return this;
        }

        public Builder semester(String semester) {
            this.semester = semester;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Enrollment build() {
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setStatus(status != null ? status : "ENROLLED");

            EnrollmentId targetId = this.id;
            if (targetId == null) {
                Long resolvedStudentId = student != null ? student.getId() : studentId;
                Long resolvedCourseId = course != null ? course.getId() : courseId;
                if (resolvedStudentId != null && resolvedCourseId != null && semester != null) {
                    targetId = new EnrollmentId(resolvedStudentId, resolvedCourseId, semester);
                }
            } else if (semester != null && !semester.equals(targetId.getSemester())) {
                targetId = new EnrollmentId(targetId.getStudentId(), targetId.getCourseId(), semester);
            }

            enrollment.setId(targetId);
            return enrollment;
        }
    }
}
