package ca.bc.gov.educ.api.course.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ca.bc.gov.educ.api.course.model.dto.Course;
import ca.bc.gov.educ.api.course.model.dto.StudentExam;
import ca.bc.gov.educ.api.course.model.transformer.StudentExamTransformer;
import ca.bc.gov.educ.api.course.repository.StudentExamRepository;

@Service
public class StudentExamService {
    private static final Logger logger = LoggerFactory.getLogger(StudentExamService.class);

    private final CourseService courseService;
    private final StudentExamRepository studentExamRepo;
    private final StudentExamTransformer studentExamTransformer;

    public StudentExamService(StudentExamRepository studentExamRepo, StudentExamTransformer studentExamTransformer, CourseService courseService) {
        this.studentExamRepo = studentExamRepo;
        this.studentExamTransformer = studentExamTransformer;
        this.courseService = courseService;
    }

    /**
     * Get all student courses by PEN populated in Student Course DTO
     *
     * @param pen           PEN #
     * @param sortForUI     Sort For UI
     * @return Student Course
     */
    @Retry(name = "generalgetcall")
    public List<StudentExam> getStudentExamList(String pen, boolean sortForUI) {
        List<StudentExam> studentExams  = new ArrayList<>();
        try {
        	studentExams = studentExamTransformer.transformToDTO(studentExamRepo.findByPen(pen));
        	studentExams.forEach(sC -> {
        		if(sC.getCourseLevel() != null) {
	        		if(sC.getCourseLevel().trim().equalsIgnoreCase("")) {
	        			getCourseDetails(sC.getCourseCode()," ", sC);
	        		}else {
	        			getCourseDetails(sC.getCourseCode(), sC.getCourseLevel(), sC);
	        		}
        		}
        	});
        } catch (Exception e) {
            logger.debug(String.format("Exception: %s",e));
        }
        getDataSorted(studentExams,sortForUI);
        return studentExams;
    }
    
    private void getDataSorted(List<StudentExam> studentExams, boolean sortForUI) {
    	if(sortForUI) {
        	Collections.sort(studentExams, Comparator.comparing(StudentExam::getPen)
                .thenComparing(StudentExam::getCourseCode)
                .thenComparing(StudentExam::getCourseLevel)
                .thenComparing(StudentExam::getSessionDate));
        }else {
        	Collections.sort(studentExams, Comparator.comparing(StudentExam::getPen)
                .thenComparing(StudentExam::getCompletedCourseFinalPercentage).reversed()
                .thenComparing(StudentExam::getCourseLevel).reversed());        
        }
    }

	private void getCourseDetails(String courseCode, String courseLevel, StudentExam sC) {
		Course course = courseService.getCourseDetails(courseCode, courseLevel);
    	if(course != null) {
			sC.setCourseName(course.getCourseName());
			sC.setCourseDetails(course);
		  }
    }
}
