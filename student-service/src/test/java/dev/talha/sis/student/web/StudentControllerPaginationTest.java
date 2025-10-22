package dev.talha.sis.student.web;

import dev.talha.sis.student.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
class StudentControllerPaginationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentService service;

    @Test
    void usesDefaultPageSizeWhenNotProvided() throws Exception {
        when(service.list(any(Pageable.class))).thenReturn(Page.empty(PageRequest.of(0, 20)));

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(service).list(captor.capture());

        Pageable pageable = captor.getValue();
        assertThat(pageable.getPageNumber()).isEqualTo(0);
        assertThat(pageable.getPageSize()).isEqualTo(20);
    }

    @Test
    void forwardsExplicitPaginationParameters() throws Exception {
        when(service.list(any(Pageable.class))).thenReturn(Page.empty(PageRequest.of(1, 5)));

        mockMvc.perform(get("/api/v1/students").param("page", "1").param("size", "5"))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(service).list(captor.capture());

        Pageable pageable = captor.getValue();
        assertThat(pageable.getPageNumber()).isEqualTo(1);
        assertThat(pageable.getPageSize()).isEqualTo(5);
    }
}
