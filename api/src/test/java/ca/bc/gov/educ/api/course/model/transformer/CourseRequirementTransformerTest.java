package ca.bc.gov.educ.api.course.model.transformer;

import ca.bc.gov.educ.api.course.model.dto.CourseRequirement;
import ca.bc.gov.educ.api.course.model.entity.CourseRequirementEntity;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class CourseRequirementTransformerTest {

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    CourseRequirementTransformer courseRequirementTransformer;

    @Test
    public void testTransformToDTO() {
        CourseRequirementEntity courseRequirementEntity = new CourseRequirementEntity();
        courseRequirementEntity.setCourseRequirementId(UUID.randomUUID());
        courseRequirementEntity.setCourseCode("main");
        courseRequirementEntity.setCourseLevel("12");
        courseRequirementEntity.setCourseRequirementCode("RuleCd");

        CourseRequirement courseRequirement = new CourseRequirement();
        courseRequirement.setCourseRequirementId(courseRequirementEntity.getCourseRequirementId());
        courseRequirement.setCourseCode(courseRequirementEntity.getCourseCode());
        courseRequirement.setCourseLevel(courseRequirementEntity.getCourseLevel());
        courseRequirement.setCourseRequirementCode(courseRequirementEntity.getCourseRequirementCode());

        Mockito.when(modelMapper.map(courseRequirementEntity, CourseRequirement.class)).thenReturn(courseRequirement);
        var result = courseRequirementTransformer.transformToDTO(courseRequirementEntity);
        assertThat(result).isNotNull();
        assertThat(result.getCourseCode()).isEqualTo(courseRequirementEntity.getCourseCode());
        assertThat(result.getCourseLevel()).isEqualTo(courseRequirementEntity.getCourseLevel());
        assertThat(result.getCourseRequirementCode()).isEqualTo(courseRequirementEntity.getCourseRequirementCode());
    }

    @Test
    public void testTransformOptionalToDTO() {
        CourseRequirementEntity courseRequirementEntity = new CourseRequirementEntity();
        courseRequirementEntity.setCourseRequirementId(UUID.randomUUID());
        courseRequirementEntity.setCourseCode("main");
        courseRequirementEntity.setCourseLevel("12");
        courseRequirementEntity.setCourseRequirementCode("RuleCd");

        CourseRequirement courseRequirement = new CourseRequirement();
        courseRequirement.setCourseRequirementId(courseRequirementEntity.getCourseRequirementId());
        courseRequirement.setCourseCode(courseRequirementEntity.getCourseCode());
        courseRequirement.setCourseLevel(courseRequirementEntity.getCourseLevel());
        courseRequirement.setCourseRequirementCode(courseRequirementEntity.getCourseRequirementCode());

        Mockito.when(modelMapper.map(courseRequirementEntity, CourseRequirement.class)).thenReturn(courseRequirement);
        var result = courseRequirementTransformer.transformToDTO(Optional.of(courseRequirementEntity));
        assertThat(result).isNotNull();
        assertThat(result.getCourseCode()).isEqualTo(courseRequirementEntity.getCourseCode());
        assertThat(result.getCourseLevel()).isEqualTo(courseRequirementEntity.getCourseLevel());
        assertThat(result.getCourseRequirementCode()).isEqualTo(courseRequirementEntity.getCourseRequirementCode());
    }

    @Test
    public void testTransformToEntity() {
        CourseRequirementEntity courseRequirementEntity = new CourseRequirementEntity();
        courseRequirementEntity.setCourseRequirementId(UUID.randomUUID());
        courseRequirementEntity.setCourseCode("main");
        courseRequirementEntity.setCourseLevel("12");
        courseRequirementEntity.setCourseRequirementCode("RuleCd");

        CourseRequirement courseRequirement = new CourseRequirement();
        courseRequirement.setCourseRequirementId(courseRequirementEntity.getCourseRequirementId());
        courseRequirement.setCourseCode(courseRequirementEntity.getCourseCode());
        courseRequirement.setCourseLevel(courseRequirementEntity.getCourseLevel());
        courseRequirement.setCourseRequirementCode(courseRequirementEntity.getCourseRequirementCode());

        Mockito.when(modelMapper.map(courseRequirement, CourseRequirementEntity.class)).thenReturn(courseRequirementEntity);
        var result = courseRequirementTransformer.transformToEntity(courseRequirement);
        assertThat(result).isNotNull();
        assertThat(result.getCourseCode()).isEqualTo(courseRequirement.getCourseCode());
        assertThat(result.getCourseLevel()).isEqualTo(courseRequirement.getCourseLevel());
        assertThat(result.getCourseRequirementCode()).isEqualTo(courseRequirement.getCourseRequirementCode());
    }
}
