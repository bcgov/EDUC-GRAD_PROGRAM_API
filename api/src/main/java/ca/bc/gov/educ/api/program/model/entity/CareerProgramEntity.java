package ca.bc.gov.educ.api.program.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "CAREER_PROGRAM_CODE")
public class CareerProgramEntity extends BaseEntity {
   
	@Id
	@Column(name = "CAREER_PROGRAM_CODE", nullable = false)
    private String code;

	@Column(name = "LABEL", nullable = true)
	private String name;

	@Column(name = "DESCRIPTION", nullable = true)
    private String description;

	@Column(name = "DISPLAY_ORDER", nullable = true)
	private int displayOrder;

	@Column(name = "EFFECTIVE_DATE", nullable = true)
    private Date startDate; 
	
	@Column(name = "EXPIRY_DATE", nullable = true)
    private Date endDate;	
}