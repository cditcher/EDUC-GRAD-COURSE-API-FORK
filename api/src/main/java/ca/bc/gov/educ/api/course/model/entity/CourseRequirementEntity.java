package ca.bc.gov.educ.api.course.model.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "GRAD_COURSE_REQUIREMENT")
public class CourseRequirementEntity extends BaseEntity {
   
	@Id
	@Column(name = "ID", nullable = false)
    private UUID courseRequirementId;

    @Column(name = "CRSE_CODE", nullable = false)
    private String courseCode;   

    @Column(name = "CRSE_LVL", nullable = false)
    private String courseLevel;   
    
    @Column(name = "RULE_CODE", nullable = true)
    private String ruleCode;
}
