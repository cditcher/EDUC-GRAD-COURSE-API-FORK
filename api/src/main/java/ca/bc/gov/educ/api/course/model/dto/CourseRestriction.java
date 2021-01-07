package ca.bc.gov.educ.api.course.model.dto;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class CourseRestriction extends BaseModel {

	private UUID courseRestrictionId;
	private String mainCourse; 
	private String mainCourseLevel;
	private String restrictedCourse; 
	private String restrictedCourseLevel;   
	private String restrictionStartDate; 
	private String restrictionEndDate;	
}
