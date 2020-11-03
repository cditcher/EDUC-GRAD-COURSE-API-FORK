package ca.bc.gov.educ.api.course.util;

import java.util.Date;

public class EducCourseApiConstants {

    //API end-point Mapping constants
    public static final String API_ROOT_MAPPING = "";
    public static final String API_VERSION = "v1";
    public static final String GRAD_COURSE_API_ROOT_MAPPING = "/api/" + API_VERSION + "/course";
    
    public static final String GET_STUDENT_COURSE_BY_ID_MAPPING = "/{courseCode}";
    public static final String GET_COURSE_REQUIREMENT_MAPPING = "/requirement";
    public static final String GET_COURSE_REQUIREMENT_BY_RULE_MAPPING = "/requirement/rule";

    //Attribute Constants
    public static final String STUDENT_COURSE_ID_ATTRIBUTE = "courseID";

    //Default Attribute value constants
    public static final String DEFAULT_CREATED_BY = "CourseAPI";
    public static final Date DEFAULT_CREATED_TIMESTAMP = new Date();
    public static final String DEFAULT_UPDATED_BY = "CourseAPI";
    public static final Date DEFAULT_UPDATED_TIMESTAMP = new Date();

    //Default Date format constants
    public static final String DEFAULT_DATE_FORMAT = "dd-MMM-yyyy";
    
    public static final String TRAX_DATE_FORMAT = "yyyyMM";
}
