package ca.bc.gov.educ.api.program.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "OPTIONAL_PROGRAM_CODE")
public class OptionalProgramCodeEntity extends BaseEntity {
   
	@Id
	@Column(name = "OPTIONAL_PROGRAM_CODE", nullable = false)
    private String programCode;
	
	@Column(name = "LABEL", nullable = true)
    private String programName;
	
	@Column(name = "DESCRIPTION", nullable = true)
    private String description;
	
	@Column(name = "DISPLAY_ORDER", nullable = true)
    private int displayOrder; 
	
	@Column(name = "EFFECTIVE_DATE", nullable = true)
    private Date effectiveDate;
	
	@Column(name = "EXPIRY_DATE", nullable = true)
    private Date expiryDate;

	@Column(name = "ASSOCIATED_CREDENTIAL", nullable = true)
	private String associatedCredential;



}