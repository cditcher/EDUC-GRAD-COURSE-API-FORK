package ca.bc.gov.educ.api.course.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class CourseRequirementCode extends BaseModel {
    private String courseRequirementCode;
    private String label;
    private String description;
    private Date effectiveDate;
    private Date expiryDate;
}
