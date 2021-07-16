package ca.bc.gov.educ.api.program.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.program.model.dto.ProgramRequirement;
import ca.bc.gov.educ.api.program.model.entity.ProgramRequirementEntity;


@Component
public class ProgramRequirementTransformer {

    @Autowired
    ModelMapper modelMapper;

    public ProgramRequirement transformToDTO (ProgramRequirementEntity gradProgramEntity) {
    	return modelMapper.map(gradProgramEntity, ProgramRequirement.class);
    }

    public ProgramRequirement transformToDTO (Optional<ProgramRequirementEntity> gradProgramRuleEntity ) {
    	ProgramRequirementEntity cae = new ProgramRequirementEntity();
        if (gradProgramRuleEntity.isPresent())
            cae = gradProgramRuleEntity.get();

        return modelMapper.map(cae, ProgramRequirement.class);
    }

	public List<ProgramRequirement> transformToDTO (Iterable<ProgramRequirementEntity> gradProgramRuleEntities ) {
		List<ProgramRequirement> programRuleList = new ArrayList<>();
        for (ProgramRequirementEntity gradProgramRuleEntity : gradProgramRuleEntities) {
        	ProgramRequirement programRule = modelMapper.map(gradProgramRuleEntity, ProgramRequirement.class);
        	programRuleList.add(programRule);
        }
        return programRuleList;
    }

    public ProgramRequirementEntity transformToEntity(ProgramRequirement gradProgramRule) {
        return modelMapper.map(gradProgramRule, ProgramRequirementEntity.class);
    }
}
