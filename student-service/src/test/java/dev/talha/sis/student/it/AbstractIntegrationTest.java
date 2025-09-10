package dev.talha.sis.student.it;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@Testcontainers
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(classes = {
        dev.talha.sis.student.StudentServiceApplication.class,
        dev.talha.sis.student.config.TestSecurityConfig.class
})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = NONE)
public abstract class AbstractIntegrationTest {

    @Container
    protected static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("sis_student_test")
                    .withUsername("test")
                    .withPassword("test");

    @Container
    protected static final RabbitMQContainer RABBIT =
            new RabbitMQContainer("rabbitmq:3.13");

    @DynamicPropertySource
    static void override(DynamicPropertyRegistry r) {
        // Bu çağrılar container’lar JUnit tarafından başlatıldıktan sonra evaluate edilir.
        r.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        r.add("spring.datasource.username", POSTGRES::getUsername);
        r.add("spring.datasource.password", POSTGRES::getPassword);

        r.add("spring.rabbitmq.host", RABBIT::getHost);
        r.add("spring.rabbitmq.port", () -> RABBIT.getAmqpPort());
        r.add("spring.rabbitmq.username", () -> "guest");
        r.add("spring.rabbitmq.password", () -> "guest");

        // Testte Flyway çalışsın ki tablolar oluşsun
        r.add("spring.flyway.enabled", () -> true);
        r.add("spring.flyway.locations", () -> "classpath:db/migration");
        // Hibernate tablo oluşturmasın, sadece şemayı doğrulasın (Flyway ile uyumlu)
        r.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    }
}
