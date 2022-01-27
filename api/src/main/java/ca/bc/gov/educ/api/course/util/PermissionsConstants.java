package ca.bc.gov.educ.api.course.util;

public class PermissionsConstants {

	private PermissionsConstants() {}

	public static final String PREFIX = "#oauth2.hasAnyScope('";
	public static final String SUFFIX = "')";

	public static final String READ_GRAD_COURSE = PREFIX + "READ_GRAD_COURSE_DATA', 'READ_GRAD_STUDENT_COURSE_DATA" + SUFFIX;
	public static final String READ_GRAD_COURSE_REQUIREMENT = PREFIX + "READ_GRAD_COURSE_REQUIREMENT_DATA" + SUFFIX;
	public static final String READ_GRAD_COURSE_RESTRICTION = PREFIX + "READ_GRAD_COURSE_RESTRICTION_DATA" + SUFFIX;
	public static final String UPDATE_GRAD_COURSE_RESTRICTION = PREFIX + "UPDATE_GRAD_COURSE_RESTRICTION_DATA" + SUFFIX;
	public static final String READ_GRAD_STUDENT_EXAM = PREFIX + "READ_GRAD_STUDENT_EXAM_DATA" + SUFFIX;
}
