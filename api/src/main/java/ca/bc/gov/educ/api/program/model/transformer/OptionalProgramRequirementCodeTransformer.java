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

    public OptionalProgramRequirementCode transformToDTO (Optional<OptionalProgramRequirementCodeEntity> gradSpecialProgramRuleEntity ) {
    	OptionalProgramRequirementCodeEntity cae = new OptionalProgramRequirementCodeEntity();
        if (gradSpecialProgramRuleEntity.isPresent())
            cae = gradSpecialProgramRuleEntity.get();

        return modelMapper.map(cae, OptionalProgramRequirementCode.class);
    }

	public List<OptionalProgramRequirementCode> transformToDTO (Iterable<OptionalProgramRequirementCodeEntity> gradSpecialProgramRuleEntities ) {
		List<OptionalProgramRequirementCode> programRuleList = new ArrayList<>();
        for (OptionalProgramRequirementCodeEntity gradSpecialProgramRuleEntity : gradSpecialProgramRuleEntities) {
        	OptionalProgramRequirementCode programRule =modelMapper.map(gradSpecialProgramRuleEntity, OptionalProgramRequirementCode.class);
        	programRuleList.add(programRule);
        }
        return programRuleList;
    }

    public OptionalProgramRequirementCodeEntity transformToEntity(OptionalProgramRequirementCode gradSpecialProgramRule) {
        return modelMapper.map(gradSpecialProgramRule, OptionalProgramRequirementCodeEntity.class);
    }
}
