package dev.talha.sis.student.it;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

// Testlerde ortak Spring context + MockMvc + Testcontainers tabanı
@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false) // Security filtrelerini kapat
@TestInstance(Lifecycle.PER_CLASS)
public abstract class AbstractIntegrationTest {

    // Postgres test veritabanı (otomatik indirip ayağa kaldırır)
    @Container
    public static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("sis_student_test")
                    .withUsername("test")
                    .withPassword("test");

    // ÖNEMLİ: DynamicPropertySource kullanmadan önce container’ı başlat
    static {
        if (!POSTGRES.isRunning()) {
            POSTGRES.start();
        }
        // Spring Boot 3.x DataSource otomatiği: URL/USER/PASS ortam değişkenlerinden de okunur.
        System.setProperty("spring.datasource.url", POSTGRES.getJdbcUrl());
        System.setProperty("spring.datasource.username", POSTGRES.getUsername());
        System.setProperty("spring.datasource.password", POSTGRES.getPassword());
    }
}
