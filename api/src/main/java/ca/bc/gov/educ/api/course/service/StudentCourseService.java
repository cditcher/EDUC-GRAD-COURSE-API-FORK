package ca.bc.gov.educ.api.course.service;

import ca.bc.gov.educ.api.course.model.dto.Course;
import ca.bc.gov.educ.api.course.model.dto.StudentCourse;
import ca.bc.gov.educ.api.course.model.transformer.StudentCourseTransformer;
import ca.bc.gov.educ.api.course.repository.StudentCourseRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class StudentCourseService {
    private static final Logger logger = LoggerFactory.getLogger(StudentCourseService.class);

    private final CourseService courseService;
    private final StudentCourseRepository studentCourseRepo;
    private final StudentCourseTransformer studentCourseTransformer;

    public StudentCourseService(StudentCourseRepository studentCourseRepo, StudentCourseTransformer studentCourseTransformer, CourseService courseService) {
        this.studentCourseRepo = studentCourseRepo;
        this.studentCourseTransformer = studentCourseTransformer;
        this.courseService = courseService;
    }

    /**
     * Get all student courses by PEN populated in Student Course DTO
     *
     * @param pen           PEN #
     * @param sortForUI     Sort For UI
     * @return Student Course
     */
    public List<StudentCourse> getStudentCourseList(String pen, boolean sortForUI) {
        List<StudentCourse> studentCourses  = new ArrayList<>();
        try {
        	studentCourses = studentCourseTransformer.transformToDTO(studentCourseRepo.findByPen(pen));
        	studentCourses.forEach(this::populate);
        } catch (Exception e) {
            logger.debug(String.format("Exception: %s",e));
        }
        getDataSorted(studentCourses,sortForUI);
        return studentCourses;
    }

    private void populate(StudentCourse studentCourse) {
		if(StringUtils.isNotBlank(studentCourse.getRelatedCourse())
			|| StringUtils.isNotBlank(studentCourse.getRelatedLevel())
			|| StringUtils.isNotBlank(studentCourse.getCustomizedCourseName())
			|| StringUtils.isNotBlank(studentCourse.getBestSchoolPercent() != null? studentCourse.getBestSchoolPercent().toString() : null)
			|| StringUtils.isNotBlank(studentCourse.getBestExamPercent() != null? studentCourse.getBestExamPercent().toString() : null)
			|| StringUtils.isNotBlank(studentCourse.getMetLitNumRequirement())) {
			studentCourse.setHasRelatedCourse("Y");
		}else {
			studentCourse.setHasRelatedCourse("N");
		}
		if(studentCourse.getCourseLevel() != null) {
			if(studentCourse.getCourseLevel().trim().equalsIgnoreCase("")) {
				getCourseDetails(studentCourse.getCourseCode()," ", studentCourse);
			}else {
				getCourseDetails(studentCourse.getCourseCode(), studentCourse.getCourseLevel(), studentCourse);
			}
		}
		if((StringUtils.isNotBlank(studentCourse.getRelatedCourse()) || StringUtils.isNotBlank(studentCourse.getRelatedLevel())) && studentCourse.getRelatedLevel() != null) {
			checkForMoreOptions(studentCourse);
		}
	}
    
    private void checkForMoreOptions(StudentCourse sC) {
		if(sC.getRelatedLevel().trim().equalsIgnoreCase("")) {
			Course course = courseService.getCourseDetails(sC.getRelatedCourse(), " ");
			if(course != null) {
				sC.setRelatedCourseName(course.getCourseName());
			}
		} else {
			Course course = courseService.getCourseDetails(sC.getRelatedCourse(), sC.getRelatedLevel());
			if(course != null) {
				sC.setRelatedCourseName(course.getCourseName());
			}
		}
	 }
    
    private void getDataSorted(List<StudentCourse> studentCourses, boolean sortForUI) {
    	if(sortForUI) {
        	Collections.sort(studentCourses, Comparator.comparing(StudentCourse::getPen)
                .thenComparing(StudentCourse::getCourseCode)
                .thenComparing(StudentCourse::getCourseLevel)
                .thenComparing(StudentCourse::getSessionDate));
        }else {
        	Collections.sort(studentCourses, Comparator.comparing(StudentCourse::getPen)
                .thenComparing(StudentCourse::getCompletedCoursePercentage).reversed()
                .thenComparing(StudentCourse::getCredits).reversed()
                .thenComparing(StudentCourse::getCourseLevel).reversed());        
        }
    }

	private void getCourseDetails(String courseCode, String courseLevel, StudentCourse sC) {
		Course course = courseService.getCourseDetails(courseCode, courseLevel);
    	if(course != null) {
			sC.setCourseName(course.getCourseName());
			sC.setGenericCourseType(course.getGenericCourseType());
			sC.setLanguage(course.getLanguage());
			sC.setWorkExpFlag(course.getWorkExpFlag());
			sC.setCourseDetails(course);
		  }
    }
}
