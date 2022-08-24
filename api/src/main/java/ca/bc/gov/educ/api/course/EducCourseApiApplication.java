package ca.bc.gov.educ.api.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EducCourseApiApplication {

	private static Logger logger = LoggerFactory.getLogger(EducCourseApiApplication.class);

	public static void main(String[] args) {
		logger.debug("########Starting API");
		SpringApplication.run(EducCourseApiApplication.class, args);
		logger.debug("########Started API");
	}

}