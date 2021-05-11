package ca.bc.gov.educ.api.course.model.transformer;

import ca.bc.gov.educ.api.course.model.dto.CourseRestriction;
import ca.bc.gov.educ.api.course.model.entity.CourseRestrictionsEntity;
import ca.bc.gov.educ.api.course.util.EducCourseApiUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class CourseRestrictionsTransformer {

    @Autowired
    ModelMapper modelMapper;

    public CourseRestriction transformToDTO(CourseRestrictionsEntity courseRestrictionsEntity) {
        CourseRestriction courseRestrictions = modelMapper.map(courseRestrictionsEntity, CourseRestriction.class);
        courseRestrictions.setRestrictionStartDate(
                EducCourseApiUtils.parseTraxDate(
                        courseRestrictionsEntity.getRestrictionStartDate() != null ?
                                courseRestrictionsEntity.getRestrictionStartDate().toString() : null));
        courseRestrictions.setRestrictionEndDate(
                EducCourseApiUtils.parseTraxDate(
                        courseRestrictionsEntity.getRestrictionEndDate() != null ?
                                courseRestrictionsEntity.getRestrictionEndDate().toString() : null));
        return courseRestrictions;
    }

    public CourseRestriction transformToDTO(Optional<CourseRestrictionsEntity> courseRestrictionsEntity) {
        CourseRestrictionsEntity cae = new CourseRestrictionsEntity();

        if (courseRestrictionsEntity.isPresent())
            cae = courseRestrictionsEntity.get();

        CourseRestriction courseRestrictions = modelMapper.map(cae, CourseRestriction.class);
        courseRestrictions.setRestrictionStartDate(
                EducCourseApiUtils.parseTraxDate(
                        cae.getRestrictionStartDate() != null ? cae.getRestrictionStartDate().toString() : null));
        courseRestrictions.setRestrictionEndDate(
                EducCourseApiUtils.parseTraxDate(
                        cae.getRestrictionEndDate() != null ? cae.getRestrictionEndDate().toString() : null));
        return courseRestrictions;
    }

    public List<CourseRestriction> transformToDTO(Iterable<CourseRestrictionsEntity> courseReqEntities) {

        List<CourseRestriction> courseReqList = new ArrayList<CourseRestriction>();

        for (CourseRestrictionsEntity courseReqEntity : courseReqEntities) {
            CourseRestriction courseRestrictions = new CourseRestriction();
            courseRestrictions = modelMapper.map(courseReqEntity, CourseRestriction.class);
            courseRestrictions.setRestrictionStartDate(
                    EducCourseApiUtils.parseTraxDate(
                            courseReqEntity.getRestrictionStartDate() != null ? courseReqEntity.getRestrictionStartDate().toString() : null));
            courseRestrictions.setRestrictionEndDate(
                    EducCourseApiUtils.parseTraxDate(
                            courseReqEntity.getRestrictionEndDate() != null ? courseReqEntity.getRestrictionEndDate().toString() : null));
            courseReqList.add(courseRestrictions);
        }

        return courseReqList;
    }

    public CourseRestrictionsEntity transformToEntity(CourseRestriction courseRestrictions) {
        CourseRestrictionsEntity courseRestrictionsEntity = modelMapper.map(courseRestrictions, CourseRestrictionsEntity.class);
        courseRestrictionsEntity.setRestrictionStartDate(
                courseRestrictions.getRestrictionStartDate() != null ? Date.valueOf(courseRestrictions.getRestrictionStartDate()) : null);
        courseRestrictionsEntity.setRestrictionEndDate(
                courseRestrictions.getRestrictionEndDate() != null ? Date.valueOf(courseRestrictions.getRestrictionEndDate()) : null);
        return courseRestrictionsEntity;
    }
}
