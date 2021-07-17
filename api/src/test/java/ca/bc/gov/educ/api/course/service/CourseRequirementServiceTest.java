package ca.bc.gov.educ.api.course.service;

import ca.bc.gov.educ.api.course.model.dto.*;
import ca.bc.gov.educ.api.course.model.entity.CourseRequirementEntity;
import ca.bc.gov.educ.api.course.repository.CourseRequirementCriteriaQueryRepository;
import ca.bc.gov.educ.api.course.repository.CourseRequirementRepository;
import ca.bc.gov.educ.api.course.util.criteria.CriteriaHelper;
import ca.bc.gov.educ.api.course.util.EducCourseApiConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CourseRequirementServiceTest {

    @Autowired
    EducCourseApiConstants constants;

    @Autowired
    private CourseRequirementService courseRequirementService;

    @MockBean
    private CourseRequirementRepository courseRequirementRepository;

    @MockBean
    private CourseRequirementCriteriaQueryRepository courseRequirementCriteriaQueryRepository;

    @MockBean
    private CourseService courseService;

    @MockBean
    private WebClient webClient;

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

    @Before
    public void setUp() {
        openMocks(this);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetAllCourseRequirementList() {
        CourseRequirementEntity courseRequirementEntity = new CourseRequirementEntity();
        courseRequirementEntity.setCourseRequirementId(UUID.randomUUID());
        courseRequirementEntity.setCourseCode("MAIN");
        courseRequirementEntity.setCourseLevel("12");
        courseRequirementEntity.setCourseRequirementCode("RuleCd");

        Course course = new Course();
        course.setCourseCode("MAIN");
        course.setCourseLevel("12");
        course.setCourseName("MAIN Course");

        GradRuleDetails ruleDetails = new GradRuleDetails();
        ruleDetails.setCourseRequirementCode("RuleCd");
        ruleDetails.setProgramCode("2018-EN");
        ruleDetails.setRequirementName("Test");

        Pageable paging = PageRequest.of(1, 5);
        Page<CourseRequirementEntity> pagedResult = new PageImpl<>(Arrays.asList(courseRequirementEntity));

        when(courseRequirementRepository.findAll(any(Pageable.class))).thenReturn(pagedResult);
        when(courseService.getCourseDetails(eq("MAIN"), eq("12"))).thenReturn(course);

        ParameterizedTypeReference<List<GradRuleDetails>> responseType = new ParameterizedTypeReference<List<GradRuleDetails>>() {
        };

        when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
        when(this.requestHeadersUriMock.uri(String.format(constants.getRuleDetailProgramManagementApiUrl(), courseRequirementEntity.getCourseRequirementCode()))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(responseType)).thenReturn(Mono.just(Arrays.asList(ruleDetails)));

        var result = courseRequirementService.getAllCourseRequirementList(1,5, "accessToken");
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        AllCourseRequirements allCourseRequirements = result.get(0);
        assertThat(allCourseRequirements.getCourseName()).isEqualTo(course.getCourseName());
        assertThat(allCourseRequirements.getRequirementName()).isEqualTo(ruleDetails.getRequirementName());
        assertThat(allCourseRequirements.getRequirementProgram()).isEqualTo(courseRequirementService.getRequirementProgram(Arrays.asList(ruleDetails), allCourseRequirements).toString());
    }

    @Test
    public void testGetAllCourseRequirementListByRule() {
        String ruleCode = "RuleCd";

        CourseRequirementEntity courseRequirementEntity = new CourseRequirementEntity();
        courseRequirementEntity.setCourseRequirementId(UUID.randomUUID());
        courseRequirementEntity.setCourseCode("MAIN");
        courseRequirementEntity.setCourseLevel("12");
        courseRequirementEntity.setCourseRequirementCode(ruleCode);

        Course course = new Course();
        course.setCourseCode("MAIN");
        course.setCourseLevel("12");
        course.setCourseName("MAIN Course");

        GradRuleDetails ruleDetails = new GradRuleDetails();
        ruleDetails.setCourseRequirementCode(ruleCode);
        ruleDetails.setProgramCode("2018-EN");
        ruleDetails.setRequirementName("Test");

        Pageable paging = PageRequest.of(1, 5);
        Page<CourseRequirementEntity> pagedResult = new PageImpl<>(Arrays.asList(courseRequirementEntity));

        when(courseRequirementRepository.findByCourseRequirementCode(eq(ruleCode), any(Pageable.class))).thenReturn(pagedResult);
        when(courseService.getCourseDetails(eq("MAIN"), eq("12"))).thenReturn(course);

        var result = courseRequirementService.getAllCourseRequirementListByRule(ruleCode, 1, 5);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        CourseRequirement responseCourseRequirement = result.get(0);
        assertThat(responseCourseRequirement.getCourseRequirementId()).isEqualTo(courseRequirementEntity.getCourseRequirementId());
        assertThat(responseCourseRequirement.getCourseCode()).isEqualTo(courseRequirementEntity.getCourseCode());
        assertThat(responseCourseRequirement.getCourseName()).isEqualTo(course.getCourseName());
    }

    @Test
    public void testGetCourseRequirements() {
        CourseRequirementEntity courseRequirementEntity = new CourseRequirementEntity();
        courseRequirementEntity.setCourseRequirementId(UUID.randomUUID());
        courseRequirementEntity.setCourseCode("MAIN");
        courseRequirementEntity.setCourseLevel("12");
        courseRequirementEntity.setCourseRequirementCode("RuleCd");

        when(courseRequirementRepository.findAll()).thenReturn(Arrays.asList(courseRequirementEntity));
        var result = courseRequirementService.getCourseRequirements();
        assertThat(result).isNotNull();
        assertThat(result.getCourseRequirementList().size()).isEqualTo(1);
        CourseRequirement courseRequirement = result.getCourseRequirementList().get(0);
        assertThat(courseRequirement.getCourseRequirementId()).isEqualTo(courseRequirementEntity.getCourseRequirementId());
    }

    @Test
    public void testGetCourseRequirementsByCourseAndLevel() {
        CourseRequirementEntity courseRequirementEntity = new CourseRequirementEntity();
        courseRequirementEntity.setCourseRequirementId(UUID.randomUUID());
        courseRequirementEntity.setCourseCode("MAIN");
        courseRequirementEntity.setCourseLevel("12");
        courseRequirementEntity.setCourseRequirementCode("RuleCd");

        when(courseRequirementRepository.findByCourseCodeAndCourseLevel(eq("MAIN"), eq("12"))).thenReturn(Arrays.asList(courseRequirementEntity));
        var result = courseRequirementService.getCourseRequirements("MAIN", "12");
        assertThat(result).isNotNull();
        assertThat(result.getCourseRequirementList().size()).isEqualTo(1);
        CourseRequirement courseRequirement = result.getCourseRequirementList().get(0);
        assertThat(courseRequirement.getCourseRequirementId()).isEqualTo(courseRequirementEntity.getCourseRequirementId());
    }

    @Test
    public void testGetCourseRequirementListByCourses() {
        CourseRequirementEntity courseRequirementEntity = new CourseRequirementEntity();
        courseRequirementEntity.setCourseRequirementId(UUID.randomUUID());
        courseRequirementEntity.setCourseCode("MAIN");
        courseRequirementEntity.setCourseLevel("12");
        courseRequirementEntity.setCourseRequirementCode("RuleCd");

        CourseList courseList = new CourseList();
        courseList.setCourseCodes(Arrays.asList("MAIN"));

        when(courseRequirementRepository.findByCourseCodeIn(courseList.getCourseCodes())).thenReturn(Arrays.asList(courseRequirementEntity));
        var result = courseRequirementService.getCourseRequirementListByCourses(courseList);
        assertThat(result).isNotNull();
        assertThat(result.getCourseRequirementList().size()).isEqualTo(1);
        CourseRequirement courseRequirement = result.getCourseRequirementList().get(0);
        assertThat(courseRequirement.getCourseRequirementId()).isEqualTo(courseRequirementEntity.getCourseRequirementId());
    }

    @Test
    public void testGetCourseRequirementSearchList() {
        CourseRequirementEntity courseRequirementEntity = new CourseRequirementEntity();
        courseRequirementEntity.setCourseRequirementId(UUID.randomUUID());
        courseRequirementEntity.setCourseCode("MAIN");
        courseRequirementEntity.setCourseLevel("12");
        courseRequirementEntity.setCourseRequirementCode("RuleCd");

        Course course = new Course();
        course.setCourseCode("MAIN");
        course.setCourseLevel("12");
        course.setCourseName("MAIN Course");

        GradRuleDetails ruleDetails = new GradRuleDetails();
        ruleDetails.setCourseRequirementCode("RuleCd");
        ruleDetails.setProgramCode("2018-EN");
        ruleDetails.setRequirementName("Test");

        when(courseRequirementCriteriaQueryRepository.findByCriteria(any(CriteriaHelper.class), eq(CourseRequirementEntity.class))).thenReturn(Arrays.asList(courseRequirementEntity));
        when(courseService.getCourseDetails(eq("MAIN"), eq("12"))).thenReturn(course);

        ParameterizedTypeReference<List<GradRuleDetails>> responseType = new ParameterizedTypeReference<List<GradRuleDetails>>() {
        };

        when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
        when(this.requestHeadersUriMock.uri(String.format(constants.getRuleDetailProgramManagementApiUrl(), courseRequirementEntity.getCourseRequirementCode()))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(responseType)).thenReturn(Mono.just(Arrays.asList(ruleDetails)));

        var result = courseRequirementService.getCourseRequirementSearchList("MAIN", "12", "RuleCd", "accessToken");
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        AllCourseRequirements allCourseRequirements = result.get(0);
        assertThat(allCourseRequirements.getCourseName()).isEqualTo(course.getCourseName());
        assertThat(allCourseRequirements.getRequirementName()).isEqualTo(ruleDetails.getRequirementName());
        assertThat(allCourseRequirements.getRequirementProgram()).isEqualTo(courseRequirementService.getRequirementProgram(Arrays.asList(ruleDetails), allCourseRequirements).toString());
    }

}
