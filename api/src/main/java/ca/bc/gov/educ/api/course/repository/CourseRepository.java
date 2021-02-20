package ca.bc.gov.educ.api.course.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.course.model.entity.CourseEntity;
import ca.bc.gov.educ.api.course.model.entity.CourseId;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {

    List<CourseEntity> findAll();

	CourseEntity findByCourseKey(CourseId key);

	@Query(value="SELECT si.* FROM tab_crse si where "
			+ "(:courseName is null or si.CRSE_NAME like %:courseName%)  and "
			+ "(:courseLevel is null or si.CRSE_LEVEL like :courseLevel%)  and "
			+ "(:courseCode is null or si.CRSE_CODE like :courseCode%) and ROWNUM <= 50",nativeQuery = true)	
	List<CourseEntity> searchForCourse(String courseCode, String courseLevel, String courseName);

}
