package ca.bc.gov.educ.api.course.model.entity;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Immutable
@Entity
@Table(name = "PROV_EXAM")
public class StudentExamEntity {
   
	@EmbeddedId
    private StudentCourseId courseKey;
    
    @Column(name = "BEST_SCHOOL_PCT", nullable = true)
    private Double bestSchoolPercentage;

    @Column(name = "PROV_SCHOOL_PCT", nullable = true)
    private Double completedSchoolPercentage;    
    
    @Column(name = "TO_WRITE_FLAG",nullable = true)
    private String optIn;
    
    @Column(name = "WROTE_FLAG", nullable = true)
    private String wroteFlag;
    
    @Column(name = "PROV_SPEC_CASE ", nullable = true)
    private String specialCase;

    @Column(name = "PROV_EXAM_PCT", nullable = true)
    private Double completedCourseExamPercentage;
    
    @Column(name = "PROV_FINAL_PCT", nullable = true)
    private Double completedCourseFinalPercentage;
    
    @Column(name = "FINAL_LG", nullable = true)
    private String completedCourseLetterGrade;
    
    @Column(name = "BEST_EXAM_PCT", nullable = true)
    private Double bestExamPercentage;
    
}
