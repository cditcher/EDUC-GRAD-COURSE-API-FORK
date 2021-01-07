package ca.bc.gov.educ.api.course.model.entity;

import java.sql.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;

@Data
@Immutable
@Entity
@Table(name = "GRAD_COURSE_REQUIREMENT")
public class CourseRequirementEntity {
   
	@Id
	@Column(name = "ID", nullable = false)
    private UUID courseRequirementId;

    @Column(name = "CRSE_CODE", nullable = false)
    private String courseCode;   

    @Column(name = "CRSE_LVL", nullable = false)
    private String courseLevel;   
    
    @Column(name = "RULE_CODE", nullable = true)
    private String ruleCode;   
    
    @Column(name = "CREATED_BY", nullable = true)
    private String createdBy;
	
	@Column(name = "CREATED_TIMESTAMP", nullable = true)
    private Date createdTimestamp;
	
	@Column(name = "UPDATED_BY", nullable = true)
    private String updatedBy;
	
	@Column(name = "UPDATED_TIMESTAMP", nullable = true)
    private Date updatedTimestamp;
}
