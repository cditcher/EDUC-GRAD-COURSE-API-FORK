package ca.bc.gov.educ.api.course.config;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.lang.Nullable;

import static ca.bc.gov.educ.api.course.util.EducCourseApiConstants.DEFAULT_DATE_FORMAT_INSTANCE;

public class GradDateEditor extends CustomDateEditor {

	public GradDateEditor() {
		super(DEFAULT_DATE_FORMAT_INSTANCE, true);
	} 

	@Override
	public void setAsText(@Nullable String text) throws IllegalArgumentException {
		
		if (StringUtils.isBlank(text)) {
			// Treat empty String as null value.
			setValue(null);
		}
		else {
			try {
				if (StringUtils.equalsIgnoreCase("now", text)) {
					setValue(new Date());
				} else if (text.length() == 10) {
					setValue(DEFAULT_DATE_FORMAT_INSTANCE.parse(text));
				} else if (text.length() > 10) {
					Calendar calendar = javax.xml.bind.DatatypeConverter.parseDateTime(text);
					setValue(calendar.getTime());
				}
			} catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
			}
		}
	}

}
