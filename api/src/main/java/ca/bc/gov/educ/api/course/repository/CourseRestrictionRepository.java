package ca.bc.gov.educ.api.course.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.course.model.entity.CourseRestrictionsEntity;

@Repository
public interface CourseRestrictionRepository extends JpaRepository<CourseRestrictionsEntity, UUID> {

    List<CourseRestrictionsEntity> findAll();

	List<CourseRestrictionsEntity> findByMainCourseAndMainCourseLevel(String courseCode, String courseLevel);

	@Query(value="SELECT si.* FROM GRAD_COURSE_RESTRICTIONS si where "
			+ "(:mainCourseLevel is null or si.CRSE_MAIN_LVL like :mainCourseLevel%)  and "
			+ "(:mainCourseCode is null or si.CRSE_MAIN like :mainCourseCode%) and ROWNUM <= 50",nativeQuery = true)
	List<CourseRestrictionsEntity> searchForCourseRestriction(String mainCourseCode, String mainCourseLevel);

	List<CourseRestrictionsEntity> findByMainCourseIn(List<String> courseCodes);

}
