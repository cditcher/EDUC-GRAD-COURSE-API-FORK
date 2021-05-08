package ca.bc.gov.educ.api.course.service;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.bc.gov.educ.api.course.model.dto.CourseRestriction;
import ca.bc.gov.educ.api.course.model.dto.CourseRestrictions;
import ca.bc.gov.educ.api.course.model.transformer.CourseRestrictionsTransformer;
import ca.bc.gov.educ.api.course.repository.CourseRestrictionRepository;

@Service
public class CourseRestrictionService {

    @Autowired
    private CourseRestrictionRepository courseRestrictionRepository;

    @Autowired
    private CourseRestrictionsTransformer courseRestrictionTransformer;
    
    @Autowired
    CourseRestrictions courseRestrictions;

    private static Logger logger = LoggerFactory.getLogger(CourseRestrictionService.class);

     /**
     * Get all course requirements in Course Restriction DTO
     * @param pageSize 
     * @param pageNo 
     *
     * @return Course 
     * @throws java.lang.Exception
     */
    public List<CourseRestriction> getAllCourseRestrictionList(Integer pageNo, Integer pageSize) {
        List<CourseRestriction> courseReqList  = new ArrayList<CourseRestriction>();

        try {  
        	//Pageable paging = PageRequest.of(pageNo, pageSize);        	 
           // Page<CourseRestrictionsEntity> pagedResult = courseRestrictionRepository.findAll(paging);        	
            courseReqList = courseRestrictionTransformer.transformToDTO(courseRestrictionRepository.findAll()); 
        } catch (Exception e) {
            logger.debug("Exception:" + e);
        }

        return courseReqList;
    }
    
    public CourseRestrictions getCourseRestrictions() {
        courseRestrictions.setCourseRestrictions(
                courseRestrictionTransformer.transformToDTO(courseRestrictionRepository.findAll()));
        return courseRestrictions;
    }

    public CourseRestrictions getCourseRestrictions(String courseCode, String courseLevel) {
        courseRestrictions.setCourseRestrictions(
                courseRestrictionTransformer.transformToDTO(
                        courseRestrictionRepository.findByMainCourseAndMainCourseLevel(courseCode, courseLevel)));
        return courseRestrictions;
    }

	public List<CourseRestriction> getCourseRestrictionsSearchList(String mainCourseCode, String mainCourseLevel) {
		return courseRestrictionTransformer.transformToDTO(courseRestrictionRepository.searchForCourseRestriction(StringUtils.toRootUpperCase(StringUtils.strip(mainCourseCode, "*")),StringUtils.toRootUpperCase(StringUtils.strip(mainCourseLevel, "*"))));
	}
}
