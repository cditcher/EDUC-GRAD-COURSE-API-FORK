package ca.bc.gov.educ.api.course.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.course.model.entity.CourseRestrictionsEntity;

@Repository
public interface CourseRestrictionRepository extends JpaRepository<CourseRestrictionsEntity, UUID> {

    List<CourseRestrictionsEntity> findAll();

	List<CourseRestrictionsEntity> findByMainCourseAndMainCourseLevel(String courseCode, String courseLevel);

}
