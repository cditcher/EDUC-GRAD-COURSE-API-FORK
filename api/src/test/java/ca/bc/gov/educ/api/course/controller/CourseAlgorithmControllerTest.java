package ca.bc.gov.educ.api.course.controller;

import ca.bc.gov.educ.api.course.model.dto.*;
import ca.bc.gov.educ.api.course.service.CourseAlgorithmService;
import ca.bc.gov.educ.api.course.util.GradValidation;
import ca.bc.gov.educ.api.course.util.ResponseHelper;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class CourseAlgorithmControllerTest {
    @Mock
    private CourseAlgorithmService courseAlgorithmService;

    @InjectMocks
    CourseAlgorithmController courseAlgorithmController;

    @Mock
    ResponseHelper responseHelper;

    @Mock
    GradValidation validation;

    @Test
    public void testGetCourseAlgorithmDataByPen() {
        // Student Course
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setPen("123456789");
        studentCourse.setCourseCode("MAIN");
        studentCourse.setCourseLevel("12");
        studentCourse.setCourseName("main test course");
        studentCourse.setLanguage("en");

        // Course Requirement Code
        CourseRequirementCodeDTO courseRequirementCodeDTO = new CourseRequirementCodeDTO();
        courseRequirementCodeDTO.setCourseRequirementCode("RuleCd");
        courseRequirementCodeDTO.setDescription("RuleCd Description");
        courseRequirementCodeDTO.setEffectiveDate(new Date(System.currentTimeMillis() - 10000L));
        courseRequirementCodeDTO.setExpiryDate(new Date(System.currentTimeMillis() + 10000L));

        // Course Requirement
        CourseRequirement courseRequirement = new CourseRequirement();
        courseRequirement.setCourseRequirementId(UUID.randomUUID());
        courseRequirement.setCourseCode("MAIN");
        courseRequirement.setCourseLevel("12");
        courseRequirement.setRuleCode(courseRequirementCodeDTO);

        // Course Restriction
        CourseRestriction courseRestriction = new CourseRestriction();
        courseRestriction.setCourseRestrictionId(UUID.randomUUID());
        courseRestriction.setMainCourse("MAIN");
        courseRestriction.setMainCourseLevel("12");
        courseRestriction.setRestrictedCourse("REST");
        courseRestriction.setRestrictedCourseLevel("12");

        // Course Algorithm Data
        CourseAlgorithmData courseAlgorithmData = new CourseAlgorithmData();
        courseAlgorithmData.setStudentCourses(Arrays.asList(studentCourse));
        courseAlgorithmData.setCourseRequirements(Arrays.asList(courseRequirement));
        courseAlgorithmData.setCourseRestrictions(Arrays.asList(courseRestriction));

        Mockito.when(courseAlgorithmService.getCourseAlgorithmData(studentCourse.getPen(), false)).thenReturn(courseAlgorithmData);
        courseAlgorithmController.getCourseAlgorithmData(studentCourse.getPen());
        Mockito.verify(courseAlgorithmService).getCourseAlgorithmData(studentCourse.getPen(), false);
    }

}
