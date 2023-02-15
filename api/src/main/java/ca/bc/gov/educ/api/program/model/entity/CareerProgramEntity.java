package ca.bc.gov.educ.api.program.model.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TAB_PRGM")
public class CareerProgramEntity  {
   
	@Id
	@Column(name = "PRGM_CODE", nullable = false)
    private String code;
	
	@Column(name = "PRGM_NAME", nullable = true)
    private String description;

	@Column(name = "START_DATE", nullable = true)
    private Date startDate; 
	
	@Column(name = "END_DATE", nullable = true)
    private Date endDate;	
}