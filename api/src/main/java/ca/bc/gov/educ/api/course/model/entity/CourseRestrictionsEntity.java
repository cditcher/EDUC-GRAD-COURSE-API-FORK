package ca.bc.gov.educ.api.course.model.entity;

import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "COURSE_RESTRICTION")
public class CourseRestrictionsEntity extends BaseEntity  {
   
	@Id
	@Column(name = "COURSE_RESTRICTION_ID", nullable = false)
    private UUID courseRestrictionId;

	@Column(name = "MAIN_COURSE", nullable = false)
    private String mainCourse;  
	
	@Column(name = "MAIN_COURSE_LEVEL", nullable = true)
    private String mainCourseLevel;
	
	@Column(name = "RESTRICTED_COURSE", nullable = false)
    private String restrictedCourse; 
	
	@Column(name = "RESTRICTED_COURSE_LVL", nullable = true)
    private String restrictedCourseLevel;   
	
	@Column(name = "RESTRICTION_EFFECTIVE_DATE", nullable = false)
    private Date restrictionStartDate; 
	
	@Column(name = "RESTRICTION_EXPIRY_DATE", nullable = true)
    private Date restrictionEndDate;
	
}
