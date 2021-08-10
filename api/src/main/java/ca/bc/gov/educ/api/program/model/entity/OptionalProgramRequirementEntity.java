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
@Table(name = "OPTIONAL_PROGRAM_REQUIREMENT")
@EqualsAndHashCode(callSuper=false)
public class OptionalProgramRequirementEntity  extends BaseEntity {
   
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "OPTIONAL_PROGRAM_RQMT_ID", nullable = false)
    private UUID optionalProgramRequirementID; 
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "OPTIONAL_PROGRAM_ID", referencedColumnName = "OPTIONAL_PROGRAM_ID")
    private OptionalProgramEntity optionalProgramID; 
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "OPTIONAL_PROGRAM_RQMT_CODE", referencedColumnName = "OPTIONAL_PROGRAM_RQMT_CODE")
    private OptionalProgramRequirementCodeEntity optionalProgramRequirementCode;
	
}