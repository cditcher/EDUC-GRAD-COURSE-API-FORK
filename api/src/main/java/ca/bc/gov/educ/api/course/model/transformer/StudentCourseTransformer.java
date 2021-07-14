package ca.bc.gov.educ.api.course.model.transformer;

import ca.bc.gov.educ.api.course.model.dto.StudentCourse;
import ca.bc.gov.educ.api.course.model.entity.StudentCourseEntity;
import ca.bc.gov.educ.api.course.util.EducCourseApiUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class StudentCourseTransformer {

    @Autowired
    ModelMapper modelMapper;

    public StudentCourse transformToDTO (StudentCourseEntity studentCourseEntity) {
        StudentCourse studentCourse = modelMapper.map(studentCourseEntity, StudentCourse.class);
        return studentCourse;
    }

    public StudentCourse transformToDTO ( Optional<StudentCourseEntity> courseAchievementEntity ) {
        StudentCourseEntity cae = new StudentCourseEntity();

        if (courseAchievementEntity.isPresent())
            cae = courseAchievementEntity.get();

        StudentCourse courseAchievement = modelMapper.map(cae, StudentCourse.class);
        return courseAchievement;
    }

    public List<StudentCourse> transformToDTO (Iterable<StudentCourseEntity> courseAchievementEntities ) {

        List<StudentCourse> courseAchievementList = new ArrayList<StudentCourse>();

        for (StudentCourseEntity courseAchievementEntity : courseAchievementEntities) {
            StudentCourse courseAchievement = new StudentCourse();
            courseAchievement = modelMapper.map(courseAchievementEntity, StudentCourse.class);
           
            courseAchievement.setPen(courseAchievementEntity.getCourseKey().getPen());
            courseAchievement.setCourseCode(courseAchievementEntity.getCourseKey().getCourseCode());
            courseAchievement.setCourseLevel(courseAchievementEntity.getCourseKey().getCourseLevel());
            courseAchievement.setSessionDate(EducCourseApiUtils.parseTraxDate(courseAchievementEntity.getCourseKey().getSessionDate()));
            courseAchievementList.add(courseAchievement);
        }

        return courseAchievementList;
    }

    public StudentCourseEntity transformToEntity(StudentCourse studentCourse) {
        StudentCourseEntity courseAchievementEntity = modelMapper.map(studentCourse, StudentCourseEntity.class);
        return courseAchievementEntity;
    }
}
