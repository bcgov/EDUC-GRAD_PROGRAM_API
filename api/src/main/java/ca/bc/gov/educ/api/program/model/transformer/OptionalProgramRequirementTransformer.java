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

    public OptionalProgramRequirement transformToDTO (Optional<OptionalProgramRequirementEntity> gradSpecialProgramRuleEntity ) {
    	OptionalProgramRequirementEntity cae = new OptionalProgramRequirementEntity();
        if (gradSpecialProgramRuleEntity.isPresent())
            cae = gradSpecialProgramRuleEntity.get();

        return modelMapper.map(cae, OptionalProgramRequirement.class);
    }

	public List<OptionalProgramRequirement> transformToDTO (Iterable<OptionalProgramRequirementEntity> gradSpecialProgramRuleEntities ) {
		List<OptionalProgramRequirement> programRuleList = new ArrayList<>();
        for (OptionalProgramRequirementEntity gradSpecialProgramRuleEntity : gradSpecialProgramRuleEntities) {
        	OptionalProgramRequirement programRule =modelMapper.map(gradSpecialProgramRuleEntity, OptionalProgramRequirement.class);
        	programRuleList.add(programRule);
        }
        return programRuleList;
    }

    public OptionalProgramRequirementEntity transformToEntity(OptionalProgramRequirement gradSpecialProgramRule) {
        return modelMapper.map(gradSpecialProgramRule, OptionalProgramRequirementEntity.class);
    }
}
