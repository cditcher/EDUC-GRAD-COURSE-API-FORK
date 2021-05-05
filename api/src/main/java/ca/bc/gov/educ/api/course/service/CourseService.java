package ca.bc.gov.educ.api.course.service;


import java.util.ArrayList;
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

    @Autowired
    private CourseRepository courseRepo;
    
    @Autowired
    private CourseCriteriaQueryRepository courseCriteriaQueryRepository;

    Iterable<CourseEntity> courseEntities;

    @Autowired
    private CourseTransformer courseTransformer;

    private static Logger logger = LoggerFactory.getLogger(CourseService.class);

     /**
     * Get all courses in Course DTO
     * @param pageSize 
     * @param pageNo 
     *
     * @return Course 
     * @throws java.lang.Exception
     */
    public List<Course> getCourseList(Integer pageNo, Integer pageSize) {
        List<Course> course  = new ArrayList<Course>();

        try {  
        	//Pageable paging = PageRequest.of(pageNo, pageSize);        	 
           // Page<CourseEntity> pagedResult = courseRepo.findAll(paging);        	
        	course = courseTransformer.transformToDTO(courseRepo.findAll()); 
        } catch (Exception e) {
            logger.debug("Exception:" + e);
        }

        return course;
    }

	public Course getCourseDetails(String crseCode, String crseLvl) {
		CourseId key = new CourseId();
		key.setCourseCode(crseCode);
		key.setCourseLevel(crseLvl);
		return courseTransformer.transformToDTO(courseRepo.findByCourseKey(key));
	}

	public List<Course> getCourseSearchList(String courseCode, String courseLevel, String courseName,String language,Date startDate, Date endDate) {
		CriteriaHelper criteria = new CriteriaHelper();
		criteria = getSearchCriteria("courseKey.courseCode",courseCode,"courseCode",criteria);
		criteria = getSearchCriteria("courseKey.courseLevel",courseLevel,"courseLevel",criteria);
		criteria = getSearchCriteria("courseName",courseName,"courseName",criteria);
		criteria = getSearchCriteria("language",language,"language",criteria);
		
		if(startDate != null) {
			criteria = getSearchCriteriaDate("startDate",startDate,"startDate",criteria);					
		}
		if(endDate != null) {
			criteria = getSearchCriteriaDate("endDate",endDate,"endDate",criteria);	
		}
		
		List<Course> courseList = courseTransformer.transformToDTO(courseCriteriaQueryRepository.findByCriteria(criteria, CourseEntity.class));
		if(courseList.size() > 0) {
			Collections.sort(courseList, Comparator.comparing(Course::getCourseCode)
	                .thenComparing(Course::getCourseLevel));
		}
		return courseList;
	}
	private CriteriaHelper getSearchCriteriaDate(String roolElement, Date value, String paramterType,
			CriteriaHelper criteria) {
		if(paramterType.equalsIgnoreCase("startDate")) {
			criteria.add(roolElement, OperationEnum.GREATER_THAN_EQUAL_TO, value);			
		}else if (paramterType.equalsIgnoreCase("endDate") ) {
			criteria.add(roolElement, OperationEnum.LESS_THAN_EQUAL_TO, value);
		}
		return criteria;
	}

	public CriteriaHelper getSearchCriteria(String roolElement,String value,String paramterType,CriteriaHelper criteria) {
		if(paramterType.equalsIgnoreCase("language")) {
			if(StringUtils.isNotBlank(value)) {
				if(StringUtils.equals("F", value)) {
					criteria.add(roolElement, OperationEnum.EQUALS, value.toUpperCase());
				}else {
					criteria.add(roolElement, OperationEnum.NOT_EQUALS, "F");
				}
			}
		}else {
			if(StringUtils.isNotBlank(value)) {
				if(StringUtils.contains(value,"*")) {
					criteria.add(roolElement, OperationEnum.STARTS_WITH_IGNORE_CASE, StringUtils.strip(value.toUpperCase(),"*"));
				}else {
					criteria.add(roolElement, OperationEnum.EQUALS, value.toUpperCase());
				}
			}
		}
		
		return criteria;
	}
}
