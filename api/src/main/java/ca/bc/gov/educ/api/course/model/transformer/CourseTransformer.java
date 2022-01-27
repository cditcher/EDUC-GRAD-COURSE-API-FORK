package ca.bc.gov.educ.api.course.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.course.model.dto.Course;
import ca.bc.gov.educ.api.course.model.entity.CourseEntity;

@Component
public class CourseTransformer {

    @Autowired
    ModelMapper modelMapper;

    public Course transformToDTO (CourseEntity courseEntity) {
        Course studentCourse = null;
        if(courseEntity != null)
        	studentCourse= modelMapper.map(courseEntity, Course.class);
        return studentCourse;
    }

    public Course transformToDTO ( Optional<CourseEntity> courseAchievementEntity ) {
        CourseEntity cae = new CourseEntity();

        if (courseAchievementEntity.isPresent())
            cae = courseAchievementEntity.get();

        return modelMapper.map(cae, Course.class);
    }

	public List<Course> transformToDTO (Iterable<CourseEntity> courseEntities ) {

        List<Course> courseList = new ArrayList<>();

        for (CourseEntity courseEntity : courseEntities) {
            Course course = modelMapper.map(courseEntity, Course.class);
           
            course.setCourseCode(courseEntity.getCourseKey().getCourseCode());
            course.setCourseLevel(courseEntity.getCourseKey().getCourseLevel());
            
            courseList.add(course);
        }

        return courseList;
    }

    public CourseEntity transformToEntity(Course studentCourse) {
        return modelMapper.map(studentCourse, CourseEntity.class);
    }
}
