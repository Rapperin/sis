package dev.talha.sis.student.it.api;

import dev.talha.sis.student.it.AbstractIntegrationTest;
import dev.talha.sis.student.entity.Course;
import dev.talha.sis.student.entity.Student;
import dev.talha.sis.student.repo.CourseRepository;
import dev.talha.sis.student.repo.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EnrollmentControllerIT extends AbstractIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired StudentRepository studentRepo;
    @Autowired CourseRepository courseRepo;

    Long studentId;
    Long courseId;

    @BeforeEach
    void setUp() {
        courseRepo.deleteAll();
        studentRepo.deleteAll();

        Student s = new Student();
        s.setFirstName("Ali");
        s.setLastName("Veli");
        s.setEmail("ali.veli@example.com");
        studentId = studentRepo.save(s).getId();

        Course c = new Course("CS101", "Intro to CS", 5);
        courseId = courseRepo.save(c).getId();
    }

    @Test
    void enroll_and_list_ok() throws Exception {
        // Enroll
        String body = """
            {"studentId": %d, "courseId": %d, "semester": "2025-FALL", "status": "ENROLLED"}
            """.formatted(studentId, courseId);

        mockMvc.perform(post("/api/v1/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        // By-student
        mockMvc.perform(get("/api/v1/enrollments/by-student/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].student.id").value(studentId.intValue()))
                .andExpect(jsonPath("$.content[0].course.id").value(courseId.intValue()));
    }
}
