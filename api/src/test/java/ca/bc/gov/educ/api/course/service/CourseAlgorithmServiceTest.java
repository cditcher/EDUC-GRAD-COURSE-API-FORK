package ca.bc.gov.educ.api.course.service;

import ca.bc.gov.educ.api.course.model.dto.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CourseAlgorithmServiceTest {

    @Autowired
    CourseAlgorithmService courseAlgorithmService;

    @MockBean
    private StudentCourseService studentCourseService;

    @MockBean
    private CourseRequirementService courseRequirementService;

    @MockBean
    private CourseRestrictionService courseRestrictionService;

    @Test
    public void testGetCourseAlgorithmData_whenGivenPenNumber_thenReturnSuccess() {

        // Student Course
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setPen("123456789");
        studentCourse.setCourseCode("MAIN");
        studentCourse.setCourseLevel("12");
        studentCourse.setCourseName("main test course");
        studentCourse.setLanguage("en");

        // Course Requirement
        CourseRequirement courseRequirement = new CourseRequirement();
        courseRequirement.setCourseRequirementId(UUID.randomUUID());
        courseRequirement.setCourseCode("MAIN");
        courseRequirement.setCourseLevel("12");
        courseRequirement.setCourseRequirementCode("RuleCd");

        // Course Restriction
        CourseRestriction courseRestriction = new CourseRestriction();
        courseRestriction.setCourseRestrictionId(UUID.randomUUID());
        courseRestriction.setMainCourseLevel("MAIN");
        courseRestriction.setMainCourseLevel("12");
        courseRestriction.setRestrictedCourse("REST");
        courseRestriction.setRestrictedCourseLevel("12");

        // Course Algorithm Data
        CourseAlgorithmData courseAlgorithmData = new CourseAlgorithmData();
        courseAlgorithmData.setStudentCourses(Arrays.asList(studentCourse));
        courseAlgorithmData.setCourseRequirements(Arrays.asList(courseRequirement));
        courseAlgorithmData.setCourseRestrictions(Arrays.asList(courseRestriction));

        CourseList courseList = new CourseList();
        courseList.setCourseCodes(Arrays.asList("MAIN"));

        CourseRequirements courseRequirements = new CourseRequirements();
        courseRequirements.setCourseRequirementList(Arrays.asList(courseRequirement));

        CourseRestrictions courseRestrictions = new CourseRestrictions();
        courseRestrictions.setCourseRestrictions(Arrays.asList(courseRestriction));

        when(studentCourseService.getStudentCourseList(studentCourse.getPen(), false)).thenReturn(Arrays.asList(studentCourse));
        when(courseRequirementService.getCourseRequirementListByCourses(courseList)).thenReturn(courseRequirements);
        when(courseRestrictionService.getCourseRestrictionsListByCourses(courseList)).thenReturn(courseRestrictions);

        var result = courseAlgorithmService.getCourseAlgorithmData(studentCourse.getPen(), false);

        assertThat(result).isNotNull();
        assertThat(result.getStudentCourses().isEmpty()).isFalse();
        assertThat(result.getCourseRequirements().isEmpty()).isFalse();
        assertThat(result.getCourseRestrictions().isEmpty()).isFalse();
    }
}
