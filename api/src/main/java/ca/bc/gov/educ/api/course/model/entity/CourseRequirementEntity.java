package ca.bc.gov.educ.api.course.model.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "COURSE_REQUIREMENT")
public class CourseRequirementEntity extends BaseEntity {
   
	@Id
	@Column(name = "COURSE_REQUIREMENT_ID", nullable = false)
    private UUID courseRequirementId;

    @Column(name = "COURSE_CODE", nullable = false)
    private String courseCode;   

    @Column(name = "COURSE_LEVEL", nullable = true)
    private String courseLevel;   
    
    @Column(name = "COURSE_REQUIREMENT_CODE", nullable = false)
    private String courseRequirementCode;
}
