package ca.bc.gov.educ.api.course.repository;

import ca.bc.gov.educ.api.course.model.entity.StudentCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourseEntity, UUID> {

     @Query("select c from StudentCourseEntity c where c.courseKey.pen=:pen")
    Iterable<StudentCourseEntity> findByPen(String pen);

}
