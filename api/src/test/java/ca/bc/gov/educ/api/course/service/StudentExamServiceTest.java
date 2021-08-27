package ca.bc.gov.educ.api.course.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import ca.bc.gov.educ.api.course.model.dto.Course;
import ca.bc.gov.educ.api.course.model.dto.StudentExam;
import ca.bc.gov.educ.api.course.model.entity.StudentExamEntity;
import ca.bc.gov.educ.api.course.model.entity.StudentExamId;
import ca.bc.gov.educ.api.course.repository.StudentExamRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class StudentExamServiceTest {

    @Autowired
    StudentExamService studentExamService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private StudentExamRepository studentExamRepo;

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
    public void testGetStudentExamList_whenGivenCourseCodeAndLevelWithRelatedCourse_thenReturnSuccess() {
        // ID
        StudentExamId studentExamId = new StudentExamId();
        studentExamId.setPen("123456789");
        studentExamId.setCourseCode("main");
        studentExamId.setCourseLevel("12");
        studentExamId.setSessionDate("2020-05");

        StudentExamEntity studentExamEntity = new StudentExamEntity();
        studentExamEntity.setCourseKey(studentExamId);

        Course course = new Course();
        course.setCourseCode("main");
        course.setCourseLevel("12");
        course.setCourseName("test main course");
        course.setGenericCourseType("gct");
        course.setLanguage("en");
        course.setWorkExpFlag("Y");

        when(studentExamRepo.findByPen(studentExamId.getPen())).thenReturn(Arrays.asList(studentExamEntity));
        when(courseService.getCourseDetails(course.getCourseCode(), course.getCourseLevel())).thenReturn(course);

        var result = studentExamService.getStudentExamList(studentExamId.getPen(), true);
        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isFalse();
        StudentExam responseStudentExam = result.get(0);
        assertThat(responseStudentExam.getCourseCode()).isEqualTo(course.getCourseCode());
    }

    @Test
    public void testGetStudentExamList_whenGivenCourseCodeWithoutRelatedCourse_thenReturnSuccess() {
        // ID
    	StudentExamId studentExamId = new StudentExamId();
        studentExamId.setPen("123456789");
        studentExamId.setCourseCode("main");
        studentExamId.setCourseLevel("");
        studentExamId.setSessionDate("2020-05");

        StudentExamEntity studentExamEntity = new StudentExamEntity();
        studentExamEntity.setCourseKey(studentExamId);

        Course course = new Course();
        course.setCourseCode("main");
        course.setCourseLevel("12");
        course.setCourseName("test main course");
        course.setGenericCourseType("gct");
        course.setLanguage("en");
        course.setWorkExpFlag("Y");

        when(studentExamRepo.findByPen(studentExamId.getPen())).thenReturn(Arrays.asList(studentExamEntity));
        when(courseService.getCourseDetails(course.getCourseCode(), " ")).thenReturn(course);

        var result = studentExamService.getStudentExamList(studentExamId.getPen(), true);
        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isFalse();
        StudentExam responseStudentExam = result.get(0);
        assertThat(responseStudentExam.getCourseCode()).isEqualTo(course.getCourseCode());
    }

    @Test
    public void testGetStudentExamList_whenGivenCourseCodeAndLevel_withsortforuifalse_thenReturnSuccess() {
        // ID
    	StudentExamId studentExamId = new StudentExamId();
        studentExamId.setPen("123456789");
        studentExamId.setCourseCode("main");
        studentExamId.setCourseLevel("12");
        studentExamId.setSessionDate("2020-05");

        StudentExamEntity studentExamEntity = new StudentExamEntity();
        studentExamEntity.setCourseKey(studentExamId);

        Course course = new Course();
        course.setCourseCode("main");
        course.setCourseLevel("12");
        course.setCourseName("test main course");
        course.setGenericCourseType("gct");
        course.setLanguage("en");
        course.setWorkExpFlag("Y");

        when(studentExamRepo.findByPen(studentExamId.getPen())).thenReturn(Arrays.asList(studentExamEntity));
        when(courseService.getCourseDetails(course.getCourseCode(), course.getCourseLevel())).thenReturn(course);

        var result = studentExamService.getStudentExamList(studentExamId.getPen(), false);
        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isFalse();
        StudentExam responseStudentExam = result.get(0);
        assertThat(responseStudentExam.getCourseCode()).isEqualTo(course.getCourseCode());
    }
}
