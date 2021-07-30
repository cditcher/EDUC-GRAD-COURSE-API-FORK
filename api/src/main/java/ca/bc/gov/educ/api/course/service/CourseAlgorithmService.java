package ca.bc.gov.educ.api.course.service;

import ca.bc.gov.educ.api.course.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseAlgorithmService {
    private final StudentCourseService studentCourseService;
    private final CourseRequirementService courseRequirementService;
    private final CourseRestrictionService courseRestrictionService;

    public CourseAlgorithmService(StudentCourseService studentCourseService,
                                  CourseRequirementService courseRequirementService,
                                  CourseRestrictionService courseRestrictionService) {
        this.studentCourseService = studentCourseService;
        this.courseRequirementService = courseRequirementService;
        this.courseRestrictionService = courseRestrictionService;
    }

    public CourseAlgorithmData getCourseAlgorithmData(String pen, boolean sortForUI) {
        CourseAlgorithmData courseAlgorithmData = new CourseAlgorithmData();

        // Student Courses
        List<StudentCourse> studentCourses = studentCourseService.getStudentCourseList(pen, sortForUI);
        courseAlgorithmData.setStudentCourses(studentCourses);
        if (!studentCourses.isEmpty()) {
            List<String> courseCodes = studentCourses.stream()
                    .map(StudentCourse::getCourseCode)
                    .distinct()
                    .collect(Collectors.toList());
            CourseList courseList = new CourseList();
            courseList.setCourseCodes(courseCodes);

            // Course Requirements
            CourseRequirements courseRequirements = courseRequirementService.getCourseRequirementListByCourses(courseList);
            if (courseRequirements != null && !courseRequirements.getCourseRequirementList().isEmpty()) {
                courseAlgorithmData.setCourseRequirements(courseRequirements.getCourseRequirementList());
            }

            // Course Restrictions
            CourseRestrictions courseRestrictions = courseRestrictionService.getCourseRestrictionsListByCourses(courseList);
            if (courseRestrictions != null && !courseRestrictions.getCourseRestrictions().isEmpty()) {
                courseAlgorithmData.setCourseRestrictions(courseRestrictions.getCourseRestrictions());
            }
        }
        return courseAlgorithmData;
    }
}
