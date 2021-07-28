package ca.bc.gov.educ.api.program.model.dto;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Component
public class OptionalProgramRequirementCode extends BaseModel {

	private String optProReqCode; 
	private String label; 
	private String description;
	private RequirementTypeCode requirementTypeCode;
	private String requiredCredits;
	private String notMetDesc;
	private String requiredLevel;
	private String languageOfInstruction;
	private String activeRequirement;
	private String requirementCategory;	
}
