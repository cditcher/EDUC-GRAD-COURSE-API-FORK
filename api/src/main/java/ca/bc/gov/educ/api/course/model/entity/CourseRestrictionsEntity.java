package ca.bc.gov.educ.api.course.model.entity;

import java.sql.Date;
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
@Table(name = "GRAD_COURSE_RESTRICTIONS")
public class CourseRestrictionsEntity extends BaseEntity  {
   
	@Id
	@Column(name = "ID", nullable = false)
    private UUID courseRestrictionId;

	@Column(name = "CRSE_MAIN", nullable = false)
    private String mainCourse;  
	
	@Column(name = "CRSE_MAIN_LVL", nullable = false)
    private String mainCourseLevel;
	
	@Column(name = "CRSE_RESTRICTED", nullable = false)
    private String restrictedCourse; 
	
	@Column(name = "CRSE_RESTRICTED_LVL", nullable = false)
    private String restrictedCourseLevel;   
	
	@Column(name = "RESTRICTION_START_DT", nullable = true)
    private Date restrictionStartDate; 
	
	@Column(name = "RESTRICTION_END_DT", nullable = true)
    private Date restrictionEndDate; 
	
}
