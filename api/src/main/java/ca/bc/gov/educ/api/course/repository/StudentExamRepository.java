package ca.bc.gov.educ.api.course.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.course.model.entity.StudentExamEntity;

@Repository
public interface StudentExamRepository extends JpaRepository<StudentExamEntity, UUID> {

    @Query("select c from StudentExamEntity c where c.courseKey.pen=:pen")
    Iterable<StudentExamEntity> findByPen(String pen);

}
