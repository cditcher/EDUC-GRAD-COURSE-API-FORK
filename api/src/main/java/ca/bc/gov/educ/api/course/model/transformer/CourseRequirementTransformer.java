package ca.bc.gov.educ.api.course.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.course.model.dto.CourseRequirement;
import ca.bc.gov.educ.api.course.model.entity.CourseRequirementEntity;

@Component
public class CourseRequirementTransformer {

    @Autowired
    ModelMapper modelMapper;

    public CourseRequirement transformToDTO (CourseRequirementEntity courseRequirementEntity) {
        return modelMapper.map(courseRequirementEntity, CourseRequirement.class);
    }

    public CourseRequirement transformToDTO ( Optional<CourseRequirementEntity> courseRequirementEntity ) {
        CourseRequirementEntity cae = new CourseRequirementEntity();

        if (courseRequirementEntity.isPresent())
            cae = courseRequirementEntity.get();

        return modelMapper.map(cae, CourseRequirement.class);
    }

	public List<CourseRequirement> transformToDTO (Iterable<CourseRequirementEntity> courseReqEntities ) {

        List<CourseRequirement> courseReqList = new ArrayList<>();

        for (CourseRequirementEntity courseReqEntity : courseReqEntities) {
            CourseRequirement courseRequirement = modelMapper.map(courseReqEntity, CourseRequirement.class);
            courseReqList.add(courseRequirement);
        }

        return courseReqList;
    }

    public CourseRequirementEntity transformToEntity(CourseRequirement courseRequirement) {
        return modelMapper.map(courseRequirement, CourseRequirementEntity.class);
    }
}
