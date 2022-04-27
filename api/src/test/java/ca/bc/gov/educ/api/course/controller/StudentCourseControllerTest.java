package ca.bc.gov.educ.api.course.controller;

import ca.bc.gov.educ.api.course.model.dto.StudentCourse;
import ca.bc.gov.educ.api.course.service.StudentCourseService;
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
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class StudentCourseControllerTest {

    @Mock
    private StudentCourseService studentCourseService;

    @InjectMocks
    StudentCourseController studentCourseController;

    @Mock
    ResponseHelper responseHelper;

    @Mock
    GradValidation validation;

    @Test
    public void testGetStudentCoursesByPEN() {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setPen("123456789");
        studentCourse.setCourseCode("main");
        studentCourse.setCourseLevel("12");
        studentCourse.setCourseName("main test course");
        studentCourse.setLanguage("en");

        Mockito.when(studentCourseService.getStudentCourseList(studentCourse.getPen(), true)).thenReturn(Arrays.asList(studentCourse));
        var result = studentCourseController.getStudentCourseByPEN(studentCourse.getPen(), true);
        Mockito.verify(studentCourseService).getStudentCourseList(studentCourse.getPen(), true);
    }

    @Test
    public void testValidationError() {
        Mockito.when(validation.hasErrors()).thenReturn(true);
        var result = studentCourseController.getStudentCourseByPEN("", true);
        Mockito.verify(validation).hasErrors();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void checkFrenchImmersionCourse() {
        String pen = "123456789";
        Mockito.when(studentCourseService.checkFrenchImmersionCourse(pen)).thenReturn(true);
        studentCourseController.checkFrenchImmersionCourse(pen);
        Mockito.verify(studentCourseService).checkFrenchImmersionCourse(pen);
    }

    @Test
    public void checkFrenchImmersionCourseByPenAndCourseLevel() {
        String pen = "123456789";
        String courseLevel = "11";
        Mockito.when(studentCourseService.checkFrenchImmersionCourse(pen, courseLevel)).thenReturn(true);
        studentCourseController.checkFrenchImmersionCourse(pen, courseLevel);
        Mockito.verify(studentCourseService).checkFrenchImmersionCourse(pen, courseLevel);
    }

    @Test
    public void checkFrenchImmersionCourseFor1986ENByPenAndCourseLevel() {
        String pen = "123456789";
        String courseLevel = "11";
        Mockito.when(studentCourseService.checkFrenchImmersionCourseForEN(pen, courseLevel)).thenReturn(true);
        studentCourseController.checkFrenchImmersionCourseForEN(pen, courseLevel);
        Mockito.verify(studentCourseService).checkFrenchImmersionCourseForEN(pen, courseLevel);
    }

}
