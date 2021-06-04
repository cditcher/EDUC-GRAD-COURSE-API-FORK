package ca.bc.gov.educ.api.course.repository;

import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.course.model.entity.CourseRequirementEntity;
import ca.bc.gov.educ.api.course.util.criteria.CriteriaQueryRepository;

@Repository
public interface CourseRequirementCriteriaQueryRepository extends CriteriaQueryRepository<CourseRequirementEntity> {

}
