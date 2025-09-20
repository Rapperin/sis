package dev.talha.sis.student.repo;

import dev.talha.sis.student.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByCourse_Id(Long courseId);
    boolean existsByCourse_IdAndSemesterAndInstructor_Id(Long courseId, String semester, Long instructorId);
}
