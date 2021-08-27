package ca.bc.gov.educ.api.course.model.transformer;

import ca.bc.gov.educ.api.course.model.dto.StudentExam;
import ca.bc.gov.educ.api.course.model.entity.StudentExamEntity;
import ca.bc.gov.educ.api.course.util.EducCourseApiUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class StudentExamTransformer {

    @Autowired
    ModelMapper modelMapper;

    public StudentExam transformToDTO (StudentExamEntity studentExamEntity) {
       return modelMapper.map(studentExamEntity, StudentExam.class);
    }

    public StudentExam transformToDTO ( Optional<StudentExamEntity> courseAchievementEntity ) {
        StudentExamEntity cae = new StudentExamEntity();

        if (courseAchievementEntity.isPresent())
            cae = courseAchievementEntity.get();
        
        return modelMapper.map(cae, StudentExam.class);
    }

    public List<StudentExam> transformToDTO (Iterable<StudentExamEntity> courseAchievementEntities ) {

        List<StudentExam> courseAchievementList = new ArrayList<>();

        for (StudentExamEntity courseAchievementEntity : courseAchievementEntities) {
            StudentExam courseAchievement =  modelMapper.map(courseAchievementEntity, StudentExam.class);
           
            courseAchievement.setPen(courseAchievementEntity.getCourseKey().getPen());
            courseAchievement.setCourseCode(courseAchievementEntity.getCourseKey().getCourseCode());
            courseAchievement.setCourseLevel(courseAchievementEntity.getCourseKey().getCourseLevel());
            courseAchievement.setSessionDate(EducCourseApiUtils.parseTraxDate(courseAchievementEntity.getCourseKey().getSessionDate()));
            courseAchievementList.add(courseAchievement);
        }

        return courseAchievementList;
    }

    public StudentExamEntity transformToEntity(StudentExam studentExam) {
        return modelMapper.map(studentExam, StudentExamEntity.class);
    }
}
