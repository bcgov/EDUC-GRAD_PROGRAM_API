package ca.bc.gov.educ.api.program.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.program.model.dto.ProgramRequirementCode;
import ca.bc.gov.educ.api.program.model.entity.ProgramRequirementCodeEntity;


@Component
public class ProgramRequirementCodeTransformer {

    @Autowired
    ModelMapper modelMapper;

    public ProgramRequirementCode transformToDTO (ProgramRequirementCodeEntity gradProgramEntity) {
    	return modelMapper.map(gradProgramEntity, ProgramRequirementCode.class);
    }

    public ProgramRequirementCode transformToDTO (Optional<ProgramRequirementCodeEntity> gradProgramRuleEntity ) {
    	ProgramRequirementCodeEntity cae = new ProgramRequirementCodeEntity();
        if (gradProgramRuleEntity.isPresent())
            cae = gradProgramRuleEntity.get();

        return modelMapper.map(cae, ProgramRequirementCode.class);
    }

	public List<ProgramRequirementCode> transformToDTO (Iterable<ProgramRequirementCodeEntity> gradProgramRuleEntities ) {
		List<ProgramRequirementCode> programRuleList = new ArrayList<>();
        for (ProgramRequirementCodeEntity gradProgramRuleEntity : gradProgramRuleEntities) {
        	ProgramRequirementCode programRule = modelMapper.map(gradProgramRuleEntity, ProgramRequirementCode.class);
        	programRuleList.add(programRule);
        }
        return programRuleList;
    }

    public ProgramRequirementCodeEntity transformToEntity(ProgramRequirementCode gradProgramRule) {
        return modelMapper.map(gradProgramRule, ProgramRequirementCodeEntity.class);
    }
}
