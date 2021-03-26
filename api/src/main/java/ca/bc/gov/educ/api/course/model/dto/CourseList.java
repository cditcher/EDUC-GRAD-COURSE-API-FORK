package ca.bc.gov.educ.api.course.model.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class CourseList {

	List<String> courseCodes;		
}
