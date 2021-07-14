package ca.bc.gov.educ.api.course.model.transformer;

import ca.bc.gov.educ.api.course.model.dto.StudentCourse;
import ca.bc.gov.educ.api.course.model.entity.StudentCourseEntity;
import ca.bc.gov.educ.api.course.model.entity.StudentCourseId;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class StudentCourseTransformerTest {
    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    StudentCourseTransformer studentCourseTransformer;

    @Test
    public void testTransformToDTO() {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setPen("123456789");
        studentCourse.setCourseCode("main");
        studentCourse.setCourseName("main course");
        studentCourse.setCourseLevel("12");
        studentCourse.setLanguage("en");

       StudentCourseId studentCourseId = new StudentCourseId();
       studentCourseId.setPen(studentCourse.getPen());
       studentCourseId.setCourseCode(studentCourse.getCourseCode());
       studentCourseId.setCourseLevel(studentCourse.getCourseLevel());

        StudentCourseEntity studentCourseEntity = new StudentCourseEntity();
        studentCourseEntity.setCourseKey(studentCourseId);

        Mockito.when(modelMapper.map(studentCourseEntity, StudentCourse.class)).thenReturn(studentCourse);
        var result = studentCourseTransformer.transformToDTO(studentCourseEntity);
        assertThat(result).isNotNull();
        assertThat(result.getPen()).isEqualTo(studentCourseEntity.getCourseKey().getPen());
        assertThat(result.getCourseCode()).isEqualTo(studentCourseEntity.getCourseKey().getCourseCode());
        assertThat(result.getCourseLevel()).isEqualTo(studentCourseEntity.getCourseKey().getCourseLevel());
    }

    @Test
    public void testTransformOptionalToDTO() {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setPen("123456789");
        studentCourse.setCourseCode("main");
        studentCourse.setCourseName("main course");
        studentCourse.setCourseLevel("12");
        studentCourse.setLanguage("en");

        StudentCourseId studentCourseId = new StudentCourseId();
        studentCourseId.setPen(studentCourse.getPen());
        studentCourseId.setCourseCode(studentCourse.getCourseCode());
        studentCourseId.setCourseLevel(studentCourse.getCourseLevel());

        StudentCourseEntity studentCourseEntity = new StudentCourseEntity();
        studentCourseEntity.setCourseKey(studentCourseId);

        Mockito.when(modelMapper.map(studentCourseEntity, StudentCourse.class)).thenReturn(studentCourse);
        var result = studentCourseTransformer.transformToDTO(Optional.of(studentCourseEntity));
        assertThat(result).isNotNull();
        assertThat(result.getPen()).isEqualTo(studentCourseEntity.getCourseKey().getPen());
        assertThat(result.getCourseCode()).isEqualTo(studentCourseEntity.getCourseKey().getCourseCode());
        assertThat(result.getCourseLevel()).isEqualTo(studentCourseEntity.getCourseKey().getCourseLevel());
    }

    @Test
    public void testTransformToEntity() {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setPen("123456789");
        studentCourse.setCourseCode("main");
        studentCourse.setCourseName("main course");
        studentCourse.setCourseLevel("12");
        studentCourse.setLanguage("en");

        StudentCourseId studentCourseId = new StudentCourseId();
        studentCourseId.setPen(studentCourse.getPen());
        studentCourseId.setCourseCode(studentCourse.getCourseCode());
        studentCourseId.setCourseLevel(studentCourse.getCourseLevel());

        StudentCourseEntity studentCourseEntity = new StudentCourseEntity();
        studentCourseEntity.setCourseKey(studentCourseId);

        Mockito.when(modelMapper.map(studentCourse, StudentCourseEntity.class)).thenReturn(studentCourseEntity);
        var result = studentCourseTransformer.transformToEntity(studentCourse);
        assertThat(result).isNotNull();
        assertThat(result.getCourseKey().getPen()).isEqualTo(studentCourse.getPen());
        assertThat(result.getCourseKey().getCourseCode()).isEqualTo(studentCourse.getCourseCode());
        assertThat(result.getCourseKey().getCourseLevel()).isEqualTo(studentCourse.getCourseLevel());
    }
}
