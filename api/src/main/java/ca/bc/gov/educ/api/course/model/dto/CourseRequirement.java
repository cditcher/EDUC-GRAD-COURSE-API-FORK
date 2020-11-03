package ca.bc.gov.educ.api.course.model.dto;

import java.sql.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class CourseRequirement {

	private UUID courseRequirementId;
	private String courseCode;
    private String courseLevel;
    private String ruleCode;
    private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;	
	private Date updatedTimestamp;
	
	
	@Override
	public String toString() {
		return "CourseRequirement [courseRequirementId=" + courseRequirementId + ", courseCode=" + courseCode
				+ ", courseLevel=" + courseLevel + ", ruleCode=" + ruleCode + ", createdBy=" + createdBy
				+ ", createdTimestamp=" + createdTimestamp + ", updatedBy=" + updatedBy + ", updatedTimestamp="
				+ updatedTimestamp + "]";
	}
	
	
}
