package ca.bc.gov.educ.api.program.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.program.model.dto.RequirementTypeCode;
import ca.bc.gov.educ.api.program.model.entity.RequirementTypeCodeEntity;


@Component
public class RequirementTypeCodeTransformer {

    @Autowired
    ModelMapper modelMapper;

    public RequirementTypeCode transformToDTO (RequirementTypeCodeEntity gradProgramEntity) { 
        return modelMapper.map(gradProgramEntity, RequirementTypeCode.class);
    }

    public RequirementTypeCode transformToDTO ( Optional<RequirementTypeCodeEntity> gradProgramEntity ) {
    	RequirementTypeCodeEntity cae = new RequirementTypeCodeEntity();
        if (gradProgramEntity.isPresent())
            cae = gradProgramEntity.get();

        return modelMapper.map(cae, RequirementTypeCode.class);
    }

	public List<RequirementTypeCode> transformToDTO (Iterable<RequirementTypeCodeEntity> gradRequirementTypesEntities ) {
		List<RequirementTypeCode> gradRequirementTypesList = new ArrayList<>();
        for (RequirementTypeCodeEntity gradRequirementTypesEntity : gradRequirementTypesEntities) {
        	RequirementTypeCode gradRequirementTypes = modelMapper.map(gradRequirementTypesEntity, RequirementTypeCode.class);            
        	gradRequirementTypesList.add(gradRequirementTypes);
        }
        return gradRequirementTypesList;
    }

    public RequirementTypeCodeEntity transformToEntity(RequirementTypeCode gradRequirementTypes) {
       return modelMapper.map(gradRequirementTypes, RequirementTypeCodeEntity.class);
    }
}
