package ca.bc.gov.educ.api.course.repository;

import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.course.model.entity.CourseEntity;
import ca.bc.gov.educ.api.course.repository.criteria.CriteriaQueryRepositoryImpl;

@Repository
public class CourseCriteriaQueryRepositoryImpl extends CriteriaQueryRepositoryImpl<CourseEntity> implements CourseCriteriaQueryRepository {

}
