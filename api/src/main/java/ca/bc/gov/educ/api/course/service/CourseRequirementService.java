package ca.bc.gov.educ.api.course.service;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ca.bc.gov.educ.api.course.model.dto.CourseRequirement;
import ca.bc.gov.educ.api.course.model.entity.CourseRequirementEntity;
import ca.bc.gov.educ.api.course.model.transformer.CourseRequirementTransformer;
import ca.bc.gov.educ.api.course.repository.CourseRequirementRepository;

@Service
public class CourseRequirementService {

    @Autowired
    private CourseRequirementRepository courseRequirementRepository;

    @Autowired
    private CourseRequirementTransformer courseRequirementTransformer;

    private static Logger logger = LoggerFactory.getLogger(CourseRequirementService.class);

     /**
     * Get all course requirements in Course Requirement DTO
     * @param pageSize 
     * @param pageNo 
     *
     * @return Course 
     * @throws java.lang.Exception
     */
    public List<CourseRequirement> getAllCourseRequirementList(Integer pageNo, Integer pageSize) {
        List<CourseRequirement> courseReqList  = new ArrayList<CourseRequirement>();

        try {  
        	Pageable paging = PageRequest.of(pageNo, pageSize);        	 
            Page<CourseRequirementEntity> pagedResult = courseRequirementRepository.findAll(paging);        	
            courseReqList = courseRequirementTransformer.transformToDTO(pagedResult.getContent()); 
        } catch (Exception e) {
            logger.debug("Exception:" + e);
        }

        return courseReqList;
    }
    
    /**
     * Get all course requirements in Course Requirement DTO by Rule
     * @param pageSize 
     * @param pageNo 
     *
     * @return Course 
     * @throws java.lang.Exception
     */
    public List<CourseRequirement> getAllCourseRequirementListByRule(String rule,Integer pageNo, Integer pageSize) {
        List<CourseRequirement> courseReqList  = new ArrayList<CourseRequirement>();

        try {  
        	Pageable paging = PageRequest.of(pageNo, pageSize);        	 
            Page<CourseRequirementEntity> pagedResult = courseRequirementRepository.findByRuleCode(rule,paging);        	
            courseReqList = courseRequirementTransformer.transformToDTO(pagedResult.getContent()); 
        } catch (Exception e) {
            logger.debug("Exception:" + e);
        }

        return courseReqList;
    }
}
