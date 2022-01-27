package ca.bc.gov.educ.api.course.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import io.github.resilience4j.retry.annotation.Retry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.bc.gov.educ.api.course.model.dto.Course;
import ca.bc.gov.educ.api.course.model.entity.CourseEntity;
import ca.bc.gov.educ.api.course.model.entity.CourseId;
import ca.bc.gov.educ.api.course.model.transformer.CourseTransformer;
import ca.bc.gov.educ.api.course.repository.CourseCriteriaQueryRepository;
import ca.bc.gov.educ.api.course.repository.CourseRepository;
import ca.bc.gov.educ.api.course.util.criteria.CriteriaHelper;
import ca.bc.gov.educ.api.course.util.criteria.GradCriteria.OperationEnum;

@Service
public class CourseService {

	private static final String START_DATE = "startDate";
	private static final String END_DATE = "endDate";
	private static final String LANGUAGE= "language";
	private static final String COURSE_NAME= "courseName";
	
    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private CourseCriteriaQueryRepository courseCriteriaQueryRepository;

    Iterable<CourseEntity> courseEntities;

    @Autowired
    private CourseTransformer courseTransformer;

    @SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(CourseService.class);

    /**
     * Get all courses in Course DTO
     *
     * @return Course
     * @throws java.lang.Exception
     */
    @Retry(name = "generalgetcall")
    public List<Course> getCourseList() {
        return courseTransformer.transformToDTO(courseRepo.findAll());
    }

    public Course getCourseDetails(String crseCode, String crseLvl) {
        CourseId key = new CourseId();
        key.setCourseCode(crseCode);
        key.setCourseLevel(crseLvl);
        return courseTransformer.transformToDTO(courseRepo.findByCourseKey(key));
    }

    @Retry(name = "generalgetcall")
    public List<Course> getCourseSearchList(String courseCode, String courseLevel, String courseName, String language, Date startDate, Date endDate) {
        CriteriaHelper criteria = new CriteriaHelper();
        getSearchCriteria("courseKey.courseCode", courseCode, "courseCode", criteria);
        getSearchCriteria("courseKey.courseLevel", courseLevel, "courseLevel", criteria);
        getSearchCriteria(COURSE_NAME, courseName, COURSE_NAME, criteria);
        getSearchCriteria(LANGUAGE, language, LANGUAGE, criteria);

        if (startDate != null) {
            getSearchCriteriaDate(START_DATE, startDate, START_DATE, criteria);
        }
        if (endDate != null) {
            getSearchCriteriaDate(END_DATE, endDate, END_DATE, criteria);
        }

        List<Course> courseList = courseTransformer.transformToDTO(courseCriteriaQueryRepository.findByCriteria(criteria, CourseEntity.class));
        if (!courseList.isEmpty()) {
            Collections.sort(courseList, Comparator.comparing(Course::getCourseCode)
                    .thenComparing(Course::getCourseLevel));
        }
        return courseList;
    }

    private void getSearchCriteriaDate(String rootElement, Date value, String paramterType,
                                                 CriteriaHelper criteria) {
        if (paramterType.equalsIgnoreCase(START_DATE)) {
            criteria.add(rootElement, OperationEnum.GREATER_THAN_EQUAL_TO, value);
        } else if (paramterType.equalsIgnoreCase(END_DATE)) {
            criteria.add(rootElement, OperationEnum.LESS_THAN_EQUAL_TO, value);
        }
    }

    private void getSearchCriteria(String rootElement, String value, String paramterType, CriteriaHelper criteria) {
        if (StringUtils.isNotBlank(value)) {
            switch (paramterType) {
                case LANGUAGE:
                    if (StringUtils.equalsIgnoreCase("F", value)) {
                        criteria.add(rootElement, OperationEnum.EQUALS, value.toUpperCase());
                    } else {
                        criteria.add(rootElement, OperationEnum.NOT_EQUALS, "F");
                    }
                    break;
                case COURSE_NAME:
                    if (StringUtils.contains(value, "*")) {
                        criteria.add(rootElement, OperationEnum.LIKE, StringUtils.strip(value.toUpperCase(), "*"));
                    } else {
                        criteria.add(rootElement, OperationEnum.EQUALS, value.toUpperCase());
                    }
                    break;
                default:
                    if (StringUtils.contains(value, "*")) {
                        criteria.add(rootElement, OperationEnum.STARTS_WITH_IGNORE_CASE, StringUtils.strip(value.toUpperCase(), "*"));
                    } else {
                        criteria.add(rootElement, OperationEnum.EQUALS, value.toUpperCase());
                    }
                    break;
            }
        }
    }
}
