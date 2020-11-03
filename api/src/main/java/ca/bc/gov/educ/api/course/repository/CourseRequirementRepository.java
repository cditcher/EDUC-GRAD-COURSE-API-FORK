package ca.bc.gov.educ.api.course.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.course.model.entity.CourseRequirementEntity;

@Repository
public interface CourseRequirementRepository extends JpaRepository<CourseRequirementEntity, UUID> {

    List<CourseRequirementEntity> findAll();

	Page<CourseRequirementEntity> findByRuleCode(String rule, Pageable paging);

}
