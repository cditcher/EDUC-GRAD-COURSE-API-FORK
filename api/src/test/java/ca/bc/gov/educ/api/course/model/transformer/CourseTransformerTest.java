package ca.bc.gov.educ.api.course.model.transformer;

import ca.bc.gov.educ.api.course.model.dto.Course;
import ca.bc.gov.educ.api.course.model.entity.CourseEntity;
import ca.bc.gov.educ.api.course.model.entity.CourseId;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class CourseTransformerTest {

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    CourseTransformer courseTransformer;

    @Test
    public void testTransformOptionalToDTO() {
        CourseId courseId = new CourseId();
        courseId.setCourseCode("main");
        courseId.setCourseLevel("12");

        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setCourseKey(courseId);
        courseEntity.setCourseName("main course");
        courseEntity.setStartDate(new Date(System.currentTimeMillis() - 10000L));
        courseEntity.setEndDate(new Date(System.currentTimeMillis() + 10000L));
        courseEntity.setLanguage("en");
        courseEntity.setGenericCourseType("generic");

        Course course = new Course();
        course.setCourseCode(courseId.getCourseCode());
        course.setCourseName(courseEntity.getCourseName());
        course.setCourseLevel(courseId.getCourseLevel());
        course.setLanguage(courseEntity.getLanguage());
        course.setStartDate(courseEntity.getStartDate());
        course.setEndDate(courseEntity.getEndDate());
        course.setGenericCourseType(courseEntity.getGenericCourseType());

        Mockito.when(modelMapper.map(courseEntity, Course.class)).thenReturn(course);
        var result = courseTransformer.transformToDTO(Optional.of(courseEntity));
        assertThat(result).isNotNull();
        assertThat(result.getCourseCode()).isEqualTo(courseId.getCourseCode());
        assertThat(result.getCourseLevel()).isEqualTo(courseId.getCourseLevel());
        assertThat(result.getCourseName()).isEqualTo(courseEntity.getCourseName());
        assertThat(result.getGenericCourseType()).isEqualTo(courseEntity.getGenericCourseType());
        assertThat(result.getStartDate()).isEqualTo(courseEntity.getStartDate());
        assertThat(result.getEndDate()).isEqualTo(courseEntity.getEndDate());
    }

    @Test
    public void testTransformToEntity() {
        CourseId courseId = new CourseId();
        courseId.setCourseCode("main");
        courseId.setCourseLevel("12");

        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setCourseKey(courseId);
        courseEntity.setCourseName("main course");
        courseEntity.setStartDate(new Date(System.currentTimeMillis() - 10000L));
        courseEntity.setEndDate(new Date(System.currentTimeMillis() + 10000L));
        courseEntity.setLanguage("en");
        courseEntity.setGenericCourseType("generic");

        Course course = new Course();
        course.setCourseCode(courseId.getCourseCode());
        course.setCourseName(courseEntity.getCourseName());
        course.setCourseLevel(courseId.getCourseLevel());
        course.setLanguage(courseEntity.getLanguage());
        course.setStartDate(courseEntity.getStartDate());
        course.setEndDate(courseEntity.getEndDate());
        course.setGenericCourseType(courseEntity.getGenericCourseType());

        Mockito.when(modelMapper.map(course, CourseEntity.class)).thenReturn(courseEntity);
        var result = courseTransformer.transformToEntity(course);
        assertThat(result).isNotNull();
        assertThat(result.getCourseKey().getCourseCode()).isEqualTo(course.getCourseCode());
        assertThat(result.getCourseKey().getCourseLevel()).isEqualTo(course.getCourseLevel());
        assertThat(result.getCourseName()).isEqualTo(course.getCourseName());
        assertThat(result.getGenericCourseType()).isEqualTo(course.getGenericCourseType());
        assertThat(result.getStartDate()).isEqualTo(course.getStartDate());
        assertThat(result.getEndDate()).isEqualTo(course.getEndDate());
    }
}
