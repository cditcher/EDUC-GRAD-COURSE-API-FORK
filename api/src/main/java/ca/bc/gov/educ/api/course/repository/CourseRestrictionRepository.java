package ca.bc.gov.educ.api.course.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.course.model.entity.CourseRestrictionsEntity;

@Repository
public interface CourseRestrictionRepository extends JpaRepository<CourseRestrictionsEntity, UUID>, JpaSpecificationExecutor<CourseRestrictionsEntity> {

    List<CourseRestrictionsEntity> findAll();

	List<CourseRestrictionsEntity> findByMainCourseAndMainCourseLevel(String courseCode, String courseLevel);

	List<CourseRestrictionsEntity> findByMainCourseAndRestrictedCourse(String courseCode, String restrictedCourseCode);

	Optional<CourseRestrictionsEntity> findByMainCourseAndMainCourseLevelAndRestrictedCourseAndRestrictedCourseLevel(String courseCode, String courseLevel, String restrictedCourseCode, String restrictedCourseLevel);

	@Query(value="SELECT si.* FROM COURSE_RESTRICTION si where "
			+ "(:mainCourseLevel is null or si.MAIN_COURSE_LEVEL like :mainCourseLevel%)  and "
			+ "(:mainCourseCode is null or si.MAIN_COURSE like :mainCourseCode%) and ROWNUM <= 50",nativeQuery = true)
	List<CourseRestrictionsEntity> searchForCourseRestriction(String mainCourseCode, String mainCourseLevel);

	List<CourseRestrictionsEntity> findByMainCourseIn(List<String> courseCodes);

}
