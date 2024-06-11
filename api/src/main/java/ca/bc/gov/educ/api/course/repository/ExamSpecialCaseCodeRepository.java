package ca.bc.gov.educ.api.course.repository;

import ca.bc.gov.educ.api.course.model.entity.ExamSpecialCaseCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamSpecialCaseCodeRepository extends JpaRepository<ExamSpecialCaseCodeEntity, String> {

}
