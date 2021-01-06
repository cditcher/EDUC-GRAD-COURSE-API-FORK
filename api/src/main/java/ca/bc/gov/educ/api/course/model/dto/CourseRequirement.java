package ca.bc.gov.educ.api.course.model.dto;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class CourseRequirement extends BaseModel {

	private UUID courseRequirementId;
	private String courseCode;
    private String courseLevel;
    private String ruleCode;	
}
