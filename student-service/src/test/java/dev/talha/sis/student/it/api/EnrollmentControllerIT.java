package dev.talha.sis.student.it.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.talha.sis.student.dto.EnrollmentDto;
import dev.talha.sis.student.entity.Course;
import dev.talha.sis.student.entity.Student;
import dev.talha.sis.student.it.AbstractIntegrationTest;
import dev.talha.sis.student.repo.CourseRepository;
import dev.talha.sis.student.repo.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EnrollmentControllerIT extends AbstractIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired StudentRepository studentRepository;
    @Autowired CourseRepository courseRepository;

    @Test
    void enroll_and_list_endpoints_should_work() throws Exception {
        // given
        Student ada = new Student();
        ada.setFirstName("Ada");
        ada.setLastName("Lovelace");
        ada.setEmail("ada@test.local");
        ada.setBirthDate(LocalDate.of(1815, 12, 10));
        ada = studentRepository.save(ada);

        Course cs101 = new Course("CS101", "Intro to CS", 6);
        cs101 = courseRepository.save(cs101);

        var dto = new EnrollmentDto(ada.getId(), cs101.getId(), "2025F");

        // enroll
        mockMvc.perform(post("/api/v1/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(status().isCreated());

        // list by student
        mockMvc.perform(get("/api/v1/enrollments/by-student/{id}", ada.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].student.id").value(ada.getId()));

        // list by course
        mockMvc.perform(get("/api/v1/enrollments/by-course/{id}", cs101.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].course.id").value(cs101.getId()));
    }
}
