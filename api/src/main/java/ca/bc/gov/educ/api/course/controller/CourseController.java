package ca.bc.gov.educ.api.course.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.educ.api.course.config.GradDateEditor;
import ca.bc.gov.educ.api.course.model.dto.AllCourseRequirements;
import ca.bc.gov.educ.api.course.model.dto.Course;
import ca.bc.gov.educ.api.course.model.dto.CourseList;
import ca.bc.gov.educ.api.course.model.dto.CourseRequirement;
import ca.bc.gov.educ.api.course.model.dto.CourseRequirements;
import ca.bc.gov.educ.api.course.model.dto.CourseRestriction;
import ca.bc.gov.educ.api.course.model.dto.CourseRestrictions;
import ca.bc.gov.educ.api.course.service.CourseRequirementService;
import ca.bc.gov.educ.api.course.service.CourseRestrictionService;
import ca.bc.gov.educ.api.course.service.CourseService;
import ca.bc.gov.educ.api.course.util.EducCourseApiConstants;
import ca.bc.gov.educ.api.course.util.GradValidation;
import ca.bc.gov.educ.api.course.util.PermissionsContants;
import ca.bc.gov.educ.api.course.util.ResponseHelper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin
@RestController
@RequestMapping(EducCourseApiConstants.GRAD_COURSE_API_ROOT_MAPPING)
@EnableResourceServer
@OpenAPIDefinition(info = @Info(title = "API for Course Management.",
        description = "This API is for Managing Course data.", version = "1"),
        security = {@SecurityRequirement(name = "OAUTH2", scopes = {"READ_GRAD_COURSE_DATA","READ_GRAD_COURSE_REQUIREMENT_DATA"})})
public class CourseController {

    private static Logger logger = LoggerFactory.getLogger(CourseController.class);

    @InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
	    binder.registerCustomEditor(Date.class, null,  new GradDateEditor());
	}
    
    @Autowired
    CourseService courseService;
    
    @Autowired
    CourseRequirementService courseRequirementService;
    
    @Autowired
    CourseRestrictionService courseRestrictionService;
    
    @Autowired
	GradValidation validation;
    
    @Autowired
	ResponseHelper response;

    @GetMapping
    @PreAuthorize(PermissionsContants.READ_GRAD_COURSE)
    @Operation(summary = "Find All Courses", description = "Get All Courses", tags = { "Courses" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<Course>> getAllCourses(
    		@RequestParam(value = "pageNo", required = false,defaultValue = "0") Integer pageNo, 
            @RequestParam(value = "pageSize", required = false,defaultValue = "150") Integer pageSize) { 
    	logger.debug("getAllCourses : ");
        return response.GET(courseService.getCourseList(pageNo,pageSize));
    }
    
    @GetMapping(EducCourseApiConstants.GET_COURSE_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_COURSE)
    @Operation(summary = "Find a Course by Course Code and Course Level",
            description = "Get a Course by Course Code and Course Level", tags = { "Courses" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Course> getCourseDetails(@PathVariable String courseCode, @PathVariable String courseLevel) {
    	logger.debug("getCourseDetails : ");
        return response.GET(courseService.getCourseDetails(courseCode,courseLevel));
    }
    
    @GetMapping(EducCourseApiConstants.GET_COURSE_BY_SEARCH_PARAMS_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_COURSE)
    @Operation(summary = "Search for a Course", description = "Search for a Course", tags = { "Courses" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST.")})
    public ResponseEntity<List<Course>> getCoursesSearch(
            @RequestParam(value = "courseCode", required = false) String courseCode,
            @RequestParam(value = "courseLevel", required = false) String courseLevel,
            @RequestParam(value = "courseName", required = false) String courseName,
            @RequestParam(value = "language", required = false) String language,
            @RequestParam(value = "startDate", required = false) Date startDate,
			@RequestParam(value = "endDate", required = false) Date endDate) { 
    	logger.debug("getCoursesSearch : ");
        return response.GET(courseService.getCourseSearchList(courseCode,courseLevel,courseName,language,startDate,endDate));
    }
    
    @GetMapping(EducCourseApiConstants.GET_STUDENT_COURSE_BY_ID_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_COURSE)
    @Operation(summary = "Find a Course by Course Code", description = "Get a Course by Course Code", tags = { "Courses" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Course> getCourseDetailsJustCode(@PathVariable String courseCode) { 
    	logger.debug("getCourseDetails with Code : ");
        return response.GET(courseService.getCourseDetails(courseCode," "));
    }
    
    @GetMapping(EducCourseApiConstants.GET_COURSE_REQUIREMENT_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_COURSE_REQUIREMENT)
    @Operation(summary = "Find All Course Requirements",
            description = "Get All Course Requirements", tags = { "Course Requirements" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<AllCourseRequirements>> getAllCoursesRequirement(
    		@RequestParam(value = "pageNo", required = false,defaultValue = "0") Integer pageNo, 
            @RequestParam(value = "pageSize", required = false,defaultValue = "50") Integer pageSize) { 
    	logger.debug("getAllCoursesRequirement : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder
                .getContext().getAuthentication().getDetails();
    	String accessToken = auth.getTokenValue();
        return response.GET(courseRequirementService.getAllCourseRequirementList(pageNo,pageSize,accessToken));
    }
    
    @GetMapping(EducCourseApiConstants.GET_COURSE_REQUIREMENT_BY_RULE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_COURSE_REQUIREMENT)
    @Operation(summary = "Find all Course Requirements by Rule",
            description = "Get all Course Requirements by Rule", tags = { "Course Requirements" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<CourseRequirement>> getAllCoursesRequirementByRule(
    		@RequestParam(value = "rule", required = true) String rule,
    		@RequestParam(value = "pageNo", required = false,defaultValue = "0") Integer pageNo, 
            @RequestParam(value = "pageSize", required = false,defaultValue = "150") Integer pageSize) { 
    	logger.debug("getAllCoursesRequirementByRule : ");
        return response.GET(courseRequirementService.getAllCourseRequirementListByRule(rule, pageNo, pageSize));
    }
    
    @GetMapping(EducCourseApiConstants.GET_COURSE_REQUIREMENT_BY_CODE_AND_LEVEL_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_COURSE_REQUIREMENT)
    @Operation(summary = "Find all Course Requirements by Course Code and Level",
            description = "Get all Course Requirements by Course Code and Level", tags = { "Course Requirements" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<CourseRequirements> getCourseRequirements(
            @RequestParam(value = "courseCode", required = false) String courseCode,
            @RequestParam(value = "courseLevel", required = false) String courseLevel) {

        CourseRequirements courseRequirements = new CourseRequirements();

        if ((courseCode == null || courseCode.isEmpty()) && (courseLevel == null || courseLevel.isEmpty())) {
            logger.debug("**** CourseCode and CourseLevel Not Specified. Retreiving all CourseRequirements.");
            courseRequirements = courseRequirementService.getCourseRequirements();
        } else {
            logger.debug("**** Retreiving CourseRequirements for CourseCode= " + courseCode
                    + " and CourseLevel= " + courseLevel + ".");
            courseRequirements = courseRequirementService.getCourseRequirements(courseCode, courseLevel);
        }

        return response.GET(courseRequirements);
    }
    
    @PostMapping(EducCourseApiConstants.GET_COURSE_REQUIREMENT_BY_COURESE_LIST_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_COURSE_REQUIREMENT)
    @Operation(summary = "Find all Course Requirements by Course Code list",
            description = "Get all Course Requirements by Course Code list", tags = { "Course Requirements" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<CourseRequirements> getCoursesRequirementByCourse(@RequestBody CourseList courseList) { 
    	logger.debug("getAllCoursesRequirement : ");
        return response.GET(courseRequirementService.getCourseRequirementListByCourses(courseList));
    }
    
    @GetMapping(EducCourseApiConstants.GET_COURSE_RESTRICTION_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_COURSE_RESTRICTION)
    @Operation(summary = "Find All Course Restrictions",
            description = "Get All Course Restrictions", tags = { "Course Restrictions" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<CourseRestriction>> getAllCoursesRestriction(
    		@RequestParam(value = "pageNo", required = false,defaultValue = "0") Integer pageNo, 
            @RequestParam(value = "pageSize", required = false,defaultValue = "150") Integer pageSize) { 
    	logger.debug("getAllCoursesRestriction : ");
        return response.GET(courseRestrictionService.getAllCourseRestrictionList(pageNo,pageSize));
    }
    
    @GetMapping(EducCourseApiConstants.GET_COURSE_RESTRICTION_BY_SEARCH_PARAMS_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_COURSE_RESTRICTION)
    @Operation(summary = "Find All Course Restrictions",
            description = "Get All Course Restrictions", tags = { "Course Restrictions" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST.")})
    public ResponseEntity<List<CourseRestriction>> getCourseRestrictionsSearch(
            @RequestParam(value = "mainCourseCode", required = false) String mainCourseCode,
            @RequestParam(value = "mainCourseLevel", required = false) String mainCourseLevel) { 
    	logger.debug("getCourseRestrictionsSearch : ");
    	if((StringUtils.isNotBlank(mainCourseCode) && mainCourseCode.length() < 2)) {
    		validation.addError("Course Code should be minimum 2 digits for Course Search");
    	}
    	if((StringUtils.isNotBlank(mainCourseLevel) && mainCourseLevel.length() < 2)) {
    		validation.addError("Course Level should be minimum 2 digits for Course Search");
    	}
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.GET(courseRestrictionService.getCourseRestrictionsSearchList(mainCourseCode,mainCourseLevel));
    }
    
    @GetMapping(EducCourseApiConstants.GET_COURSE_RESTRICTION_BY_CODE_AND_LEVEL_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_COURSE_RESTRICTION)
    @Operation(summary = "Find All Course Restrictions by Course Code and Level",
            description = "Get All Course Restrictions by Course Code and Level", tags = { "Course Restrictions" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<CourseRestrictions> getCourseRestrictions(
            @RequestParam(value = "courseCode", required = false) String courseCode,
            @RequestParam(value = "courseLevel", required = false) String courseLevel) {

        CourseRestrictions courseRestrictions = new CourseRestrictions();

        if ((courseCode == null || courseCode.isEmpty()) && (courseLevel == null || courseLevel.isEmpty())) {
            logger.debug("**** CourseCode and CourseLevel Not Specified. Retreiving all CourseRestrictions.");
            courseRestrictions = courseRestrictionService.getCourseRestrictions();
        } else {
            logger.debug("**** Retreiving CourseRestrictions for CourseCode= " + courseCode
                    + " and CourseLevel= " + courseLevel + ".");
            courseRestrictions = courseRestrictionService.getCourseRestrictions(courseCode, courseLevel);
        }

        return response.GET(courseRestrictions);
    }
}
