package ca.bc.gov.educ.api.program.model.entity;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "PROGRAM_REQUIREMENT")
@EqualsAndHashCode(callSuper=false)
public class ProgramRequirementEntity  extends BaseEntity {
   
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "PROGRAM_REQUIREMENT_ID", nullable = false)
    private UUID programRequirementID; 
	
	@Column(name = "GRADUATION_PROGRAM_CODE", nullable = false)
    private String graduationProgramCode; 
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROGRAM_REQUIREMENT_CODE", referencedColumnName = "PROGRAM_REQUIREMENT_CODE")
    private ProgramRequirementCodeEntity programRequirementCode;
	
	
	
	
}