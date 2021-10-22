package ca.bc.gov.educ.api.program.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.program.model.dto.OptionalProgramRequirement;
import ca.bc.gov.educ.api.program.model.entity.OptionalProgramRequirementEntity;


@Component
public class OptionalProgramRequirementTransformer {

    @Autowired
    ModelMapper modelMapper;

    public OptionalProgramRequirement transformToDTO (OptionalProgramRequirementEntity optionalProgramRequirementEntity) {
    	return modelMapper.map(optionalProgramRequirementEntity, OptionalProgramRequirement.class);
    }

    public OptionalProgramRequirement transformToDTO (Optional<OptionalProgramRequirementEntity> gradOptionalProgramRuleEntity ) {
    	OptionalProgramRequirementEntity cae = new OptionalProgramRequirementEntity();
        if (gradOptionalProgramRuleEntity.isPresent())
            cae = gradOptionalProgramRuleEntity.get();

        return modelMapper.map(cae, OptionalProgramRequirement.class);
    }

	public List<OptionalProgramRequirement> transformToDTO (Iterable<OptionalProgramRequirementEntity> gradOptionalProgramRuleEntities ) {
		List<OptionalProgramRequirement> programRuleList = new ArrayList<>();
        for (OptionalProgramRequirementEntity gradOptionalProgramRuleEntity : gradOptionalProgramRuleEntities) {
        	OptionalProgramRequirement programRule =modelMapper.map(gradOptionalProgramRuleEntity, OptionalProgramRequirement.class);
        	programRuleList.add(programRule);
        }
        return programRuleList;
    }

    public OptionalProgramRequirementEntity transformToEntity(OptionalProgramRequirement gradOptionalProgramRule) {
        return modelMapper.map(gradOptionalProgramRule, OptionalProgramRequirementEntity.class);
    }
}
