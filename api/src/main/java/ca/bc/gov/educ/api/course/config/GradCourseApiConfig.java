package ca.bc.gov.educ.api.course.config;

import ca.bc.gov.educ.api.course.model.dto.Course;
import ca.bc.gov.educ.api.course.model.dto.StudentCourse;
import ca.bc.gov.educ.api.course.model.entity.CourseEntity;
import ca.bc.gov.educ.api.course.model.entity.StudentCourseEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GradCourseApiConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(CourseEntity.class, Course.class);
        modelMapper.typeMap(Course.class, CourseEntity.class);

        modelMapper.typeMap(StudentCourseEntity.class, StudentCourse.class);
        modelMapper.typeMap(StudentCourse.class, StudentCourseEntity.class);
        return modelMapper;
    }
}
