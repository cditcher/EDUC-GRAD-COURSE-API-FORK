package ca.bc.gov.educ.api.course.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.educ.api.course.model.dto.StudentExam;
import ca.bc.gov.educ.api.course.service.StudentCourseService;
import ca.bc.gov.educ.api.course.service.StudentExamService;
import ca.bc.gov.educ.api.course.util.EducCourseApiConstants;
import ca.bc.gov.educ.api.course.util.GradValidation;
import ca.bc.gov.educ.api.course.util.PermissionsConstants;
import ca.bc.gov.educ.api.course.util.ResponseHelper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin
@RestController
@RequestMapping(EducCourseApiConstants.STUDENT_EXAM_URL_MAPPING)
@EnableResourceServer
@OpenAPIDefinition(info = @Info(title = "API for Student Exam Data.", description = "This API is for Reading Student Exam data.", version = "1"),
        security = {@SecurityRequirement(name = "OAUTH2", scopes = {"READ_GRAD_STUDENT_COURSE_DATA"})})
public class StudentExamController {

    private static final Logger logger = LoggerFactory.getLogger(StudentExamController.class);

    @Autowired
    StudentCourseService studentCourseService;
    
    @Autowired
    StudentExamService studentExamService;

    @Autowired
    GradValidation validation;

    @Autowired
    ResponseHelper response;

    @GetMapping(EducCourseApiConstants.GET_STUDENT_EXAM_BY_PEN_MAPPING)
    @PreAuthorize(PermissionsConstants.READ_GRAD_STUDENT_EXAM)
    @Operation(summary = "Find All Student Exams by PEN", description = "Get All Student Exams by PEN", tags = {"Student Exam"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<List<StudentExam>> getStudentCourseByPEN(
            @PathVariable String pen, @RequestParam(value = "sortForUI", required = false, defaultValue = "false") boolean sortForUI) {
        validation.requiredField(pen, "Pen");
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            logger.debug("#Get All Student Exam by PEN: *****" + pen.substring(5));
            List<StudentExam> studentExamList = studentExamService.getStudentExamList(pen, sortForUI);
            if (studentExamList.isEmpty()) {
                return response.NO_CONTENT();
            }
            return response.GET(studentExamList);
        }
    }
}
