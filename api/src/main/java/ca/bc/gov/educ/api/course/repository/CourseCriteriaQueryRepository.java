package ca.bc.gov.educ.api.course.repository;

import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.course.model.entity.CourseEntity;
import ca.bc.gov.educ.api.course.repository.criteria.CriteriaQueryRepository;

@Repository
public interface CourseCriteriaQueryRepository extends CriteriaQueryRepository<CourseEntity> {

}
