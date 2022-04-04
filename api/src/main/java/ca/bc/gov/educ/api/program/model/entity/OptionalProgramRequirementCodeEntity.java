package ca.bc.gov.educ.api.program.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "OPTIONAL_PROGRAM_RQMT_CODE")
@EqualsAndHashCode(callSuper=false)
public class OptionalProgramRequirementCodeEntity  extends BaseEntity {
   
	@Id
	@Column(name = "OPTIONAL_PROGRAM_RQMT_CODE", nullable = false)
    private String optProReqCode;
	
	@Column(name = "LABEL", nullable = false)
    private String label;
	
	@Column(name = "DESCRIPTION", nullable = true)
    private String description;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "REQUIREMENT_TYPE_CODE", referencedColumnName = "REQUIREMENT_TYPE_CODE")
    private RequirementTypeCodeEntity requirementTypeCode;
	
	@Column(name = "REQUIRED_CREDITS", nullable = true)
    private String requiredCredits;
	
	@Column(name = "NOT_MET_DESC", nullable = true)
    private String notMetDesc;
	
	@Column(name = "REQUIRED_LEVEL", nullable = true)
    private String requiredLevel;
	
	@Column(name = "LANGUAGE_OF_INSTRUCTION", nullable = true)
    private String languageOfInstruction;
	
	@Column(name = "ACTIVE_REQUIREMENT", nullable = true)
    private String activeRequirement;
	
	@Column(name = "REQUIREMENT_CATEGORY", nullable = true)
    private String requirementCategory;

    @Column(name = "TRAX_REQ_NUMBER", nullable = false)
    private String traxReqNumber;
}