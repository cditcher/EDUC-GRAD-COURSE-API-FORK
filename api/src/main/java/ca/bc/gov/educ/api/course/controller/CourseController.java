package ca.bc.gov.educ.api.course.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.educ.api.course.model.dto.Course;
import ca.bc.gov.educ.api.course.model.dto.CourseRequirement;
import ca.bc.gov.educ.api.course.model.dto.CourseRequirements;
import ca.bc.gov.educ.api.course.service.CourseRequirementService;
import ca.bc.gov.educ.api.course.service.CourseService;
import ca.bc.gov.educ.api.course.util.EducCourseApiConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin
@RestController
@RequestMapping(EducCourseApiConstants.GRAD_COURSE_API_ROOT_MAPPING)
@EnableResourceServer
@OpenAPIDefinition(info = @Info(title = "API for Course Data.", description = "This Read API is for Reading Course data.", version = "1"), security = {@SecurityRequirement(name = "OAUTH2", scopes = {"READ_GRAD_COURSE_DATA","READ_GRAD_COURSE_REQUIREMENT_DATA"})})
public class CourseController {

    private static Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    CourseService courseService;
    
    @Autowired
    CourseRequirementService courseRequirementService;

    @GetMapping
    @PreAuthorize("#oauth2.hasScope('READ_GRAD_COURSE_DATA')")
    public List<Course> getAllCourses(
    		@RequestParam(value = "pageNo", required = false,defaultValue = "0") Integer pageNo, 
            @RequestParam(value = "pageSize", required = false,defaultValue = "150") Integer pageSize) { 
    	logger.debug("getAllCourses : ");
        return courseService.getCourseList(pageNo,pageSize);
    }
    
    @GetMapping(EducCourseApiConstants.GET_COURSE_BY_CODE_MAPPING)
    @PreAuthorize("#oauth2.hasScope('READ_GRAD_COURSE_DATA')")
    public Course getCourseDetails(@PathVariable String courseCode,@PathVariable String courseLevel) { 
    	logger.debug("getCourseDetails : ");
        return courseService.getCourseDetails(courseCode,courseLevel);
    }
    
    @GetMapping(EducCourseApiConstants.GET_COURSE_REQUIREMENT_MAPPING)
    @PreAuthorize("#oauth2.hasScope('READ_GRAD_COURSE_REQUIREMENT_DATA')")
    public List<CourseRequirement> getAllCoursesRequirement(
    		@RequestParam(value = "pageNo", required = false,defaultValue = "0") Integer pageNo, 
            @RequestParam(value = "pageSize", required = false,defaultValue = "150") Integer pageSize) { 
    	logger.debug("getAllCoursesRequirement : ");
        return courseRequirementService.getAllCourseRequirementList(pageNo,pageSize);
    }
    
    @GetMapping(EducCourseApiConstants.GET_COURSE_REQUIREMENT_BY_RULE_MAPPING)
    @PreAuthorize("#oauth2.hasScope('READ_GRAD_COURSE_REQUIREMENT_DATA')")
    public List<CourseRequirement> getAllCoursesRequirementByRule(
    		@RequestParam(value = "rule", required = true) String rule,
    		@RequestParam(value = "pageNo", required = false,defaultValue = "0") Integer pageNo, 
            @RequestParam(value = "pageSize", required = false,defaultValue = "150") Integer pageSize) { 
    	logger.debug("getAllCoursesRequirementByRule : ");
        return courseRequirementService.getAllCourseRequirementListByRule(rule, pageNo, pageSize);
    }
    
    @GetMapping(EducCourseApiConstants.GET_COURSE_REQUIREMENT_BY_CODE_AND_LEVEL_MAPPING)
    @PreAuthorize("#oauth2.hasScope('READ_GRAD_COURSE_REQUIREMENT_DATA')")
    public CourseRequirements getCourseRequirements(
            @RequestParam(value = "courseCode", required = false) String courseCode,
            @RequestParam(value = "courseLevel", required = false) String courseLevel) {

        CourseRequirements courseRequirements = new CourseRequirements();

        if ((courseCode == null || courseCode.isEmpty()) && (courseLevel == null || courseLevel.isEmpty())) {
            logger.debug("**** CourseCode and CourseLevel Not Specified. Retreiving all CourseRequirements.");
            courseRequirements = courseRequirementService.getCourseRequirements();
        } else {
            logger.debug("**** Retreiving CourseRequirements for CourseCode= " + courseCode + " and CourseLevel= " + courseLevel + ".");
            courseRequirements = courseRequirementService.getCourseRequirements(courseCode, courseLevel);
        }

        return courseRequirements;
    }
}
