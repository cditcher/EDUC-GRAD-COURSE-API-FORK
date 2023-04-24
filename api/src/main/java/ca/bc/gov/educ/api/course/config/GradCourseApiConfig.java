package ca.bc.gov.educ.api.course.config;

import ca.bc.gov.educ.api.course.model.dto.Course;
import ca.bc.gov.educ.api.course.model.dto.StudentCourse;
import ca.bc.gov.educ.api.course.model.entity.CourseEntity;
import ca.bc.gov.educ.api.course.model.entity.StudentCourseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.TimeZone;

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

    @Bean
    ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.createXmlMapper(false)
                // Set timezone for JSON serialization as system timezone
                .timeZone(TimeZone.getDefault())
                .build();
    }

    @Bean
    Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder();
    }
}
