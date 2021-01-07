package ca.bc.gov.educ.api.course.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class CourseRestrictions {
    List<CourseRestriction> courseRestrictions;
}
