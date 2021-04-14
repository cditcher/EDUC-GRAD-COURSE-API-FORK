package ca.bc.gov.educ.api.course.model.dto;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class AllCourseRequirements extends BaseModel {

	private UUID courseRequirementId;
	private String courseCode;
    private String courseLevel;
    private String ruleCode;
    private String requirementName;
    private String requirementProgram;

    public String getCourseCode() {
        if (courseCode != null)
            courseCode = courseCode.trim();
        return courseCode;
    }

    public String getCourseLevel() {
        if (courseLevel != null)
            courseLevel = courseLevel.trim();
        return courseLevel;
    }
}
