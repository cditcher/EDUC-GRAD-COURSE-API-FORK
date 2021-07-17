package ca.bc.gov.educ.api.course.model.dto;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Component
public class GradRuleDetails {

	private String courseRequirementCode;
	private String requirementName;
	private String programCode;	
	private String specialProgramCode;
	
	@Override
	public String toString() {
		return "GradRuleDetails [courseRequirementCode=" + courseRequirementCode + ", requirementName=" + requirementName + ", programCode="
				+ programCode + "]";
	}
	
}
