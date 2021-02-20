package ca.bc.gov.educ.api.course.util;

import java.util.Date;

public class EducCourseApiConstants {

    //API end-point Mapping constants
    public static final String API_ROOT_MAPPING = "";
    public static final String API_VERSION = "v1";
    public static final String GRAD_COURSE_API_ROOT_MAPPING = "/api/" + API_VERSION + "/course";
    
    public static final String GET_COURSE_BY_SEARCH_PARAMS_MAPPING = "/coursesearch";
    public static final String GET_STUDENT_COURSE_BY_ID_MAPPING = "/{courseCode}";
    public static final String GET_COURSE_BY_CODE_MAPPING="/{courseCode}/level/{courseLevel}";
    public static final String GET_COURSE_REQUIREMENT_MAPPING = "/requirement";
    public static final String GET_COURSE_REQUIREMENT_BY_RULE_MAPPING = "/requirement/rule";
    public static final String GET_COURSE_REQUIREMENT_BY_CODE_AND_LEVEL_MAPPING = "/course-requirement";

    public static final String GET_COURSE_RESTRICTION_MAPPING = "/restriction";
    public static final String GET_COURSE_RESTRICTION_BY_SEARCH_PARAMS_MAPPING = "/courserestrictionsearch";
    public static final String GET_COURSE_RESTRICTION_BY_CODE_AND_LEVEL_MAPPING = "/course-restriction";
    
    //Attribute Constants
    public static final String STUDENT_COURSE_ID_ATTRIBUTE = "courseID";

    //Default Attribute value constants
    public static final String DEFAULT_CREATED_BY = "CourseAPI";
    public static final Date DEFAULT_CREATED_TIMESTAMP = new Date();
    public static final String DEFAULT_UPDATED_BY = "CourseAPI";
    public static final Date DEFAULT_UPDATED_TIMESTAMP = new Date();

    //Default Date format constants
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    
    public static final String TRAX_DATE_FORMAT = "yyyyMM";
	
}
