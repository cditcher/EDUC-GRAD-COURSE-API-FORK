package ca.bc.gov.educ.api.course.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
@Getter
@Setter
public class EducCourseApiConstants {

    private EducCourseApiConstants() {}

    public static final String CORRELATION_ID = "correlationID";

    //API end-point Mapping constants
    public static final String API_ROOT_MAPPING = "";
    public static final String API_VERSION = "v1";

    // API Root Mapping
    public static final String GRAD_COURSE_API_ROOT_MAPPING = "/api/" + API_VERSION+"/course";

    // Controller Mappings
    public static final String GRAD_COURSE_URL_MAPPING = GRAD_COURSE_API_ROOT_MAPPING;
    public static final String STUDENT_COURSE_URL_MAPPING = GRAD_COURSE_API_ROOT_MAPPING + "/studentcourse";
    public static final String STUDENT_EXAM_URL_MAPPING = GRAD_COURSE_API_ROOT_MAPPING + "/studentexam";
    public static final String COURSE_ALGORITHM_URL_MAPPING = GRAD_COURSE_API_ROOT_MAPPING + "/course-algorithm";

    // Service Method Mappings
    public static final String GET_STUDENT_COURSE_BY_PEN_MAPPING = "/pen/{pen}";
    public static final String GET_STUDENT_EXAM_BY_PEN_MAPPING = "/pen/{pen}";
    
    public static final String GET_COURSE_BY_SEARCH_PARAMS_MAPPING = "/coursesearch";
    public static final String GET_COURSE_DETAILS_BY_CODE_MAPPING = "/{courseCode}";
    public static final String GET_COURSE_BY_CODE_MAPPING="/{courseCode}/level/{courseLevel}";
    public static final String GET_COURSE_REQUIREMENT_MAPPING = "/requirement";
    public static final String GET_COURSE_REQUIREMENT_BY_COURSE_LIST_MAPPING = "/course-requirement/course-list";
    public static final String GET_COURSE_REQUIREMENT_BY_RULE_MAPPING = "/requirement/rule";
    public static final String GET_COURSE_REQUIREMENT_BY_CODE_AND_LEVEL_MAPPING = "/course-requirement";
    public static final String GET_COURSE_REQUIREMENTS_BY_SEARCH_PARAMS_MAPPING = "/courserequirementsearch";
    public static final String SAVE_COURSE_REQUIREMENT = "/save-course-requirement";
    public static final String GET_COURSE_RESTRICTION_MAPPING = "/restriction";
    public static final String GET_COURSE_RESTRICTION_BY_SEARCH_PARAMS_MAPPING = "/courserestrictionsearch";
    public static final String GET_COURSE_RESTRICTIONS_BY_CODE_AND_LEVEL_MAPPING = "/course-restriction";
    public static final String GET_COURSE_RESTRICTIONS_BY_COURSE_LIST_MAPPING = "/course-restriction/course-list";
    public static final String GET_COURSE_RESTRICTION_BY_CODE_AND_LEVEL_AND_RESTRICTED_CODE_AND_LEVEL_MAPPING = "/get-course-restriction";
    public static final String SAVE_COURSE_RESTRICTION = "/save-course-restriction";
    public static final String CHECK_COURSE_REQUIREMENT_EXISTENCE = "/check-course-requirement";
    public static final String CHECK_FRENCH_IMMERSION_COURSE = "/check-french-immersion-course/pen/{pen}";
    public static final String CHECK_FRENCH_IMMERSION_COURSE_BY_PEN_AND_LEVEL_MAPPING = "/check-french-immersion-course";
    public static final String CHECK_FRENCH_IMMERSION_COURSE_FOR_EN_BY_PEN_AND_LEVEL_MAPPING = "/check-french-immersion-course-for-en";
    public static final String CHECK_BLANK_LANGUAGE_COURSE_BY_CODE_AND_LEVEL_MAPPING = "/check-blank-language-course";
    public static final String CHECK_FRENCH_LANGUAGE_COURSE_BY_CODE_AND_LEVEL_MAPPING = "/check-french-language-course";

    public static final String GET_COURSE_ALGORITHM_DATA_BY_PEN_MAPPING = "/pen/{pen}";

    //Attribute Constants
    public static final String COURSE_ID_ATTRIBUTE = "courseID";
    public static final String STUDENT_COURSE_ID_ATTRIBUTE = "studentCourseID";

    //Default Attribute value constants
    public static final String DEFAULT_CREATED_BY = "API_COURSE";
    public static final String DEFAULT_UPDATED_BY = "API_COURSE";

    //Default Date format constants
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String TRAX_DATE_FORMAT = "yyyyMM";
    public static final DateFormat DEFAULT_DATE_FORMAT_INSTANCE = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

    //Endpoints
    @Value("${endpoint.grad-program-api.rule-detail.url}")
    private String ruleDetailProgramManagementApiUrl;

    // Splunk LogHelper Enabled
    @Value("${splunk.log-helper.enabled}")
    private boolean splunkLogHelperEnabled;

}
