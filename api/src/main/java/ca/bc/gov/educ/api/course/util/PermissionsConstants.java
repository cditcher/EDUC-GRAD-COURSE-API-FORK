package ca.bc.gov.educ.api.course.util;

public interface PermissionsConstants {
	String _PREFIX = "#oauth2.hasAnyScope('";
	String _SUFFIX = "')";

	String READ_GRAD_COURSE = _PREFIX + "READ_GRAD_COURSE_DATA', 'READ_GRAD_STUDENT_COURSE_DATA" + _SUFFIX;
	String READ_GRAD_COURSE_REQUIREMENT = _PREFIX + "READ_GRAD_COURSE_REQUIREMENT_DATA" + _SUFFIX;
	String READ_GRAD_COURSE_RESTRICTION = _PREFIX + "READ_GRAD_COURSE_RESTRICTION_DATA" + _SUFFIX;
}
