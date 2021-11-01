package ca.bc.gov.educ.api.program.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.program.model.dto.OptionalProgramRequirementCode;
import ca.bc.gov.educ.api.program.model.entity.OptionalProgramRequirementCodeEntity;


@Component
public class OptionalProgramRequirementCodeTransformer {

    @Autowired
    ModelMapper modelMapper;

    public OptionalProgramRequirementCode transformToDTO (OptionalProgramRequirementCodeEntity optionalProgramRequirementEntity) {
    	return modelMapper.map(optionalProgramRequirementEntity, OptionalProgramRequirementCode.class);
    }

    public OptionalProgramRequirementCode transformToDTO (Optional<OptionalProgramRequirementCodeEntity> gradOptionalProgramRuleEntity ) {
    	OptionalProgramRequirementCodeEntity cae = new OptionalProgramRequirementCodeEntity();
        if (gradOptionalProgramRuleEntity.isPresent())
            cae = gradOptionalProgramRuleEntity.get();

        return modelMapper.map(cae, OptionalProgramRequirementCode.class);
    }

	public List<OptionalProgramRequirementCode> transformToDTO (Iterable<OptionalProgramRequirementCodeEntity> gradOptionalProgramRuleEntities ) {
		List<OptionalProgramRequirementCode> programRuleList = new ArrayList<>();
        for (OptionalProgramRequirementCodeEntity gradOptionalProgramRuleEntity : gradOptionalProgramRuleEntities) {
        	OptionalProgramRequirementCode programRule =modelMapper.map(gradOptionalProgramRuleEntity, OptionalProgramRequirementCode.class);
        	programRuleList.add(programRule);
        }
        return programRuleList;
    }

    public OptionalProgramRequirementCodeEntity transformToEntity(OptionalProgramRequirementCode gradOptionalProgramRule) {
        return modelMapper.map(gradOptionalProgramRule, OptionalProgramRequirementCodeEntity.class);
    }
}
