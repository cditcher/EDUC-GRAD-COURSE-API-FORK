package ca.bc.gov.educ.api.course.service;


import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
import ca.bc.gov.educ.api.course.repository.criteria.CriteriaHelper;
import ca.bc.gov.educ.api.course.repository.criteria.GradCriteria.OperationEnum;

@Service
public class CourseService {

	private static final String START_DATE = "startDate";
	private static final String END_DATE = "endDate";
	private static final String LANGUAGE= "language";
	
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
     * @param pageSize
     * @param pageNo
     * @return Course
     * @throws java.lang.Exception
     */
    public List<Course> getCourseList() {
        return courseTransformer.transformToDTO(courseRepo.findAll());
    }

    public Course getCourseDetails(String crseCode, String crseLvl) {
        CourseId key = new CourseId();
        key.setCourseCode(crseCode);
        key.setCourseLevel(crseLvl);
        return courseTransformer.transformToDTO(courseRepo.findByCourseKey(key));
    }

    public List<Course> getCourseSearchList(String courseCode, String courseLevel, String courseName, String language, Date startDate, Date endDate) {
        CriteriaHelper criteria = new CriteriaHelper();
        criteria = getSearchCriteria("courseKey.courseCode", courseCode, "courseCode", criteria);
        criteria = getSearchCriteria("courseKey.courseLevel", courseLevel, "courseLevel", criteria);
        criteria = getSearchCriteria("courseName", courseName, "courseName", criteria);
        criteria = getSearchCriteria(LANGUAGE, language, LANGUAGE, criteria);

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

    private CriteriaHelper getSearchCriteriaDate(String roolElement, Date value, String paramterType,
                                                 CriteriaHelper criteria) {
        if (paramterType.equalsIgnoreCase(START_DATE)) {
            criteria.add(roolElement, OperationEnum.GREATER_THAN_EQUAL_TO, value);
        } else if (paramterType.equalsIgnoreCase(END_DATE)) {
            criteria.add(roolElement, OperationEnum.LESS_THAN_EQUAL_TO, value);
        }
        return criteria;
    }

    public CriteriaHelper getSearchCriteria(String roolElement, String value, String paramterType, CriteriaHelper criteria) {
        if (paramterType.equalsIgnoreCase(LANGUAGE)) {
            if (StringUtils.isNotBlank(value)) {
                if (StringUtils.equals("F", value)) {
                    criteria.add(roolElement, OperationEnum.EQUALS, value.toUpperCase());
                } else {
                    criteria.add(roolElement, OperationEnum.NOT_EQUALS, "F");
                }
            }
        } else {
            if (StringUtils.isNotBlank(value)) {
                if (StringUtils.contains(value, "*")) {
                    criteria.add(roolElement, OperationEnum.STARTS_WITH_IGNORE_CASE, StringUtils.strip(value.toUpperCase(), "*"));
                } else {
                    criteria.add(roolElement, OperationEnum.EQUALS, value.toUpperCase());
                }
            }
        }

        return criteria;
    }
}
