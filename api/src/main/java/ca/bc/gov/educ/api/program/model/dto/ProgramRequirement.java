package ca.bc.gov.educ.api.program.model.dto;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Component
public class ProgramRequirement extends BaseModel {

	private UUID programRequirementID; 
	private String graduationProgramCode;
	private ProgramRequirementCode programRequirementCode;
}
