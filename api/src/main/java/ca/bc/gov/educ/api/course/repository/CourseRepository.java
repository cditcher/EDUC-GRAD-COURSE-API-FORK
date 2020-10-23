package ca.bc.gov.educ.api.course.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.course.model.entity.CourseEntity;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {

    List<CourseEntity> findAll();

}
