package ca.bc.gov.educ.api.course.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;
import java.sql.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "FINE_ARTS_APPLIED_SKILLS_CODE")
public class FineArtsAppliedSkillsCodeEntity extends BaseEntity {

    @Id
    @Column(name = "FINE_ARTS_APPLIED_SKILLS_CODE", nullable = false)
    private String fineArtsAppliedSkillsCode;

    @Column(name = "LABEL", nullable = false, length = 50)
    private String label;

    @Column(name = "DESCRIPTION", nullable = false, length = 355)
    private String description;

    @Column(name = "DISPLAY_ORDER", nullable = false, precision = 0)
    private BigInteger displayOrder;

    @Column(name = "EFFECTIVE_DATE", nullable = false)
    private Date effectiveDate;

    @Column(name = "EXPIRY_DATE", nullable = true)
    private Date expiryDate;

}
