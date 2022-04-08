package ca.bc.gov.educ.api.course.service;

import ca.bc.gov.educ.api.course.model.dto.Course;
import ca.bc.gov.educ.api.course.model.dto.StudentCourse;
import ca.bc.gov.educ.api.course.model.entity.StudentCourseEntity;
import ca.bc.gov.educ.api.course.model.entity.StudentCourseId;
import ca.bc.gov.educ.api.course.repository.StudentCourseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class StudentCourseServiceTest {

    @Autowired
    StudentCourseService studentCourseService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private StudentCourseRepository studentCourseRepo;

    @MockBean
    WebClient webClient;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.RequestBodySpec requestBodyMock;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    @Mock
    private WebClient.ResponseSpec responseMock;

    @Test
    public void testGetStudentCourseList_whenGivenCourseCodeAndLevelWithRelatedCourse_thenReturnSuccess() {
        // ID
        StudentCourseId studentCourseId = new StudentCourseId();
        studentCourseId.setPen("123456789");
        studentCourseId.setCourseCode("main");
        studentCourseId.setCourseLevel("12");
        studentCourseId.setSessionDate("2020-05");

        StudentCourseEntity studentCourseEntity = new StudentCourseEntity();
        studentCourseEntity.setCourseKey(studentCourseId);
        studentCourseEntity.setRelatedCourse("rest");
        studentCourseEntity.setRelatedLevel("12");

        Course course = new Course();
        course.setCourseCode("main");
        course.setCourseLevel("12");
        course.setCourseName("test main course");
        course.setGenericCourseType("gct");
        course.setLanguage("en");
        course.setWorkExpFlag("Y");

        when(studentCourseRepo.findByPen(studentCourseId.getPen())).thenReturn(Arrays.asList(studentCourseEntity));
        when(courseService.getCourseDetails(course.getCourseCode(), course.getCourseLevel())).thenReturn(course);
        when(courseService.getCourseDetails(studentCourseEntity.getRelatedCourse(), studentCourseEntity.getRelatedLevel())).thenReturn(course);

        var result = studentCourseService.getStudentCourseList(studentCourseId.getPen(), true);
        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isFalse();
        StudentCourse responseStudentCourse = result.get(0);
        assertThat(responseStudentCourse.getCourseCode()).isEqualTo(course.getCourseCode());
        assertThat(responseStudentCourse.getGenericCourseType()).isEqualTo(course.getGenericCourseType());
    }

    @Test
    public void testGetStudentCourseList_whenGivenCourseCodeWithoutRelatedCourse_thenReturnSuccess() {
        // ID
        StudentCourseId studentCourseId = new StudentCourseId();
        studentCourseId.setPen("123456789");
        studentCourseId.setCourseCode("main");
        studentCourseId.setCourseLevel("");
        studentCourseId.setSessionDate("2020-05");

        StudentCourseEntity studentCourseEntity = new StudentCourseEntity();
        studentCourseEntity.setCourseKey(studentCourseId);

        Course course = new Course();
        course.setCourseCode("main");
        course.setCourseLevel("12");
        course.setCourseName("test main course");
        course.setGenericCourseType("gct");
        course.setLanguage("en");
        course.setWorkExpFlag("Y");

        when(studentCourseRepo.findByPen(studentCourseId.getPen())).thenReturn(Arrays.asList(studentCourseEntity));
        when(courseService.getCourseDetails(course.getCourseCode(), " ")).thenReturn(course);

        var result = studentCourseService.getStudentCourseList(studentCourseId.getPen(), true);
        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isFalse();
        StudentCourse responseStudentCourse = result.get(0);
        assertThat(responseStudentCourse.getCourseCode()).isEqualTo(course.getCourseCode());
        assertThat(responseStudentCourse.getGenericCourseType()).isEqualTo(course.getGenericCourseType());
    }

    @Test
    public void checkFrenchImmersionCourse() {
        String pen = "123456789";
        when(studentCourseRepo.countFrenchImmersionCourses(pen)).thenReturn(1L);

        var result = studentCourseService.checkFrenchImmersionCourse(pen);
        assertThat(result).isTrue();
    }

    @Test
    public void checkFrenchImmersionCourseByPenAndCourseLevel() {
        String pen = "123456789";
        String courseLevel = "11";
        when(studentCourseRepo.countFrenchImmersionCourses(pen, courseLevel)).thenReturn(1L);

        var result = studentCourseService.checkFrenchImmersionCourse(pen, courseLevel);
        assertThat(result).isTrue();
    }

    @Test
    public void checkFrenchImmersionCourseFor1986ENByPenAndCourseLevel() {
        String pen = "123456789";
        String courseLevel = "11";
        when(studentCourseRepo.countFrenchImmersionCourse(pen, courseLevel)).thenReturn(1L);

        var result = studentCourseService.checkFrenchImmersionCourseForEN(pen, courseLevel);
        assertThat(result).isTrue();
    }

    @Test
    public void checkBlankLanguageCourseByCourseCodeAndCourseLevel() {
        String courseCode = "SMA";
        String courseLevel = "12";
        when(studentCourseRepo.countTabCourses(courseCode, courseLevel, " ")).thenReturn(1L);

        var result = studentCourseService.hasBlankLanguageCourse(courseCode, courseLevel);
        assertThat(result).isTrue();
    }

    @Test
    public void checkFrenchLanguageCourseByCourseCodeAndCourseLevel() {
        String courseCode = "FRALP";
        String courseLevel = "12";
        when(studentCourseRepo.countTabCourses(courseCode, courseLevel, "F")).thenReturn(1L);

        var result = studentCourseService.hasFrenchLanguageCourse(courseCode, courseLevel);
        assertThat(result).isTrue();
    }


}

