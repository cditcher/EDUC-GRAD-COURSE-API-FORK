package ca.bc.gov.educ.api.course.repository;

import java.util.List;
import java.util.UUID;

import ca.bc.gov.educ.api.course.model.entity.CourseRequirementCodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.course.model.entity.CourseRequirementEntity;

@Repository
public interface CourseRequirementRepository extends JpaRepository<CourseRequirementEntity, UUID> {

    List<CourseRequirementEntity> findAll();

	Page<CourseRequirementEntity> findByRuleCode(CourseRequirementCodeEntity ruleCode, Pageable paging);

	List<CourseRequirementEntity> findByCourseCodeAndCourseLevel(String courseCode, String courseLevel);

	List<CourseRequirementEntity> findByCourseCodeIn(List<String> courseCodes);

	@Query(value="select count(*) from STUD_XCRSE sx, COURSE_REQUIREMENT cr\n" +
			"where sx.stud_no = :pen \n" +
			"and trim(sx.crse_code) = cr.course_code\n" +
			"and trim(sx.crse_level) = cr.course_level\n" +
			"and cr.course_requirement_code = 202\n", nativeQuery=true)
	long countFrenchImmersionCourses(@Param("pen") String pen);

}
