package ca.bc.gov.educ.api.course.service;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ca.bc.gov.educ.api.course.model.dto.AllCourseRequirements;
import ca.bc.gov.educ.api.course.model.dto.Course;
import ca.bc.gov.educ.api.course.model.dto.CourseList;
import ca.bc.gov.educ.api.course.model.dto.CourseRequirement;
import ca.bc.gov.educ.api.course.model.dto.CourseRequirements;
import ca.bc.gov.educ.api.course.model.dto.GradRuleDetails;
import ca.bc.gov.educ.api.course.model.entity.CourseRequirementEntity;
import ca.bc.gov.educ.api.course.model.transformer.CourseRequirementTransformer;
import ca.bc.gov.educ.api.course.repository.CourseRequirementRepository;
import ca.bc.gov.educ.api.course.util.EducCourseApiConstants;

@Service
public class CourseRequirementService {

    @Autowired
    private CourseRequirementRepository courseRequirementRepository;

    @Autowired
    private CourseRequirementTransformer courseRequirementTransformer;
    
    @Autowired
    CourseRequirements courseRequirements;
    
    @Autowired
    CourseService courseService;
    
    @Value(EducCourseApiConstants.ENDPOINT_RULE_DETAIL_URL)
    private String getRuleDetails;
   
    
    @Autowired
    WebClient webClient;

    private static Logger logger = LoggerFactory.getLogger(CourseRequirementService.class);

     /**
     * Get all course requirements in Course Requirement DTO
     * @param pageSize 
     * @param pageNo 
     *
     * @return Course 
     * @throws java.lang.Exception
     */
    public List<AllCourseRequirements> getAllCourseRequirementList(Integer pageNo, Integer pageSize,String accessToken) {
        List<CourseRequirement> courseReqList  = new ArrayList<CourseRequirement>();
        List<AllCourseRequirements> allCourseRequiremntList = new ArrayList<AllCourseRequirements>();
        try {  
        	Pageable paging = PageRequest.of(pageNo, pageSize);        	 
            Page<CourseRequirementEntity> pagedResult = courseRequirementRepository.findAll(paging);        	
            courseReqList = courseRequirementTransformer.transformToDTO(pagedResult.getContent());
            courseReqList.forEach((cR) -> {
            	AllCourseRequirements obj = new AllCourseRequirements();
            	BeanUtils.copyProperties(cR, obj);
            	Course course = courseService.getCourseDetails(cR.getCourseCode(), cR.getCourseLevel());
        		if(course != null) {
        			obj.setCourseName(course.getCourseName());
        		}
            	List<GradRuleDetails> ruleList = webClient.get().uri(String.format(getRuleDetails,cR.getRuleCode())).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(new ParameterizedTypeReference<List<GradRuleDetails>>() {}).block();
            	String requirementProgram = "";
            	for(GradRuleDetails rL: ruleList) {
            		obj.setRequirementName(rL.getRequirementName());
            		if(rL.getProgramCode() != null) {
            			if("".equalsIgnoreCase(requirementProgram)) {
            				requirementProgram = rL.getProgramCode();
            			}else {
            				requirementProgram = requirementProgram + "|" + rL.getProgramCode();
            			}
            		}
            		if(rL.getSpecialProgramCode() != null) {
            			if("".equalsIgnoreCase(requirementProgram)) {
            				requirementProgram = requirementProgram + rL.getSpecialProgramCode();
            			}else {
            				requirementProgram = requirementProgram + "|" + rL.getSpecialProgramCode();
            			}
            			
            		}
            	}
            	obj.setRequirementProgram(requirementProgram);
            	allCourseRequiremntList.add(obj);
            });
            
        } catch (Exception e) {
            logger.debug("Exception:" + e);
        }

        return allCourseRequiremntList;
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
            courseReqList.forEach(cR -> {
            	Course course = courseService.getCourseDetails(cR.getCourseCode(), cR.getCourseLevel().equalsIgnoreCase("") ? " ":cR.getCourseLevel());
        		if(course != null) {
        			cR.setCourseName(course.getCourseName());
        		}
            });
        } catch (Exception e) {
            logger.debug("Exception:" + e);
        }

        return courseReqList;
    }

	public CourseRequirements getCourseRequirements() {
        courseRequirements.setCourseRequirementList(
                courseRequirementTransformer.transformToDTO(courseRequirementRepository.findAll()));
        return courseRequirements;
    }

    public CourseRequirements getCourseRequirements(String courseCode, String courseLevel) {
        courseRequirements.setCourseRequirementList(
                courseRequirementTransformer.transformToDTO(
                        courseRequirementRepository.findByCourseCodeAndCourseLevel(courseCode, courseLevel)));
        return courseRequirements;
    }

	public CourseRequirements getCourseRequirementListByCourses(CourseList courseList) {
		courseRequirements.setCourseRequirementList(
                courseRequirementTransformer.transformToDTO(
                        courseRequirementRepository.findByCourseCodeIn(courseList.getCourseCodes())));
        return courseRequirements;
	}
}
