package ca.bc.gov.educ.api.course.util;

public class PermissionsConstants {

	private PermissionsConstants() {}

	public static final String _PREFIX = "#oauth2.hasAnyScope('";
	public static final String _SUFFIX = "')";

	public static final String READ_GRAD_COURSE = _PREFIX + "READ_GRAD_COURSE_DATA', 'READ_GRAD_STUDENT_COURSE_DATA" + _SUFFIX;
	public static final String READ_GRAD_COURSE_REQUIREMENT = _PREFIX + "READ_GRAD_COURSE_REQUIREMENT_DATA" + _SUFFIX;
	public static final String READ_GRAD_COURSE_RESTRICTION = _PREFIX + "READ_GRAD_COURSE_RESTRICTION_DATA" + _SUFFIX;
	public static final String UPDATE_GRAD_COURSE_RESTRICTION = _PREFIX + "UPDATE_GRAD_COURSE_RESTRICTION_DATA" + _SUFFIX;
	public static final String READ_GRAD_STUDENT_EXAM = _PREFIX + "READ_GRAD_STUDENT_EXAM_DATA" + _SUFFIX;
}
