package ca.bc.gov.educ.api.course.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

import static ca.bc.gov.educ.api.course.util.EducCourseApiConstants.DEFAULT_CREATED_BY;
import static ca.bc.gov.educ.api.course.util.EducCourseApiConstants.DEFAULT_UPDATED_BY;

@Data
@MappedSuperclass
public class BaseEntity {
	@Column(name = "CREATE_USER", nullable = false)
    private String createUser;
	
	@Column(name = "CREATE_DATE", nullable = false)
    private Date createDate;
	
	@Column(name = "UPDATE_USER", nullable = false)
    private String updateUser;
	
	@Column(name = "UPDATE_DATE", nullable = false)
    private Date updateDate;
	
	@PrePersist
	protected void onCreate() {
		this.updateUser = DEFAULT_UPDATED_BY;
		this.createUser = DEFAULT_CREATED_BY;
		this.createDate = new Date(System.currentTimeMillis());
		this.updateDate = new Date(System.currentTimeMillis());
	}

	@PreUpdate
	protected void onPersist() {
		this.updateDate = new Date(System.currentTimeMillis());
		this.updateUser = DEFAULT_UPDATED_BY;
		if (StringUtils.isBlank(createUser)) {
			createUser = DEFAULT_CREATED_BY;
		}
		if (this.createDate == null) {
			this.createDate = new Date(System.currentTimeMillis());
		}
	}
}
