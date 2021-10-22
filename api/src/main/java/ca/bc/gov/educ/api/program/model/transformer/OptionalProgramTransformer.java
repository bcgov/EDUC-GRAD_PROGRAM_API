package ca.bc.gov.educ.api.program.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.program.model.dto.OptionalProgram;
import ca.bc.gov.educ.api.program.model.entity.OptionalProgramEntity;


@Component
public class OptionalProgramTransformer {

    @Autowired
    ModelMapper modelMapper;

    public OptionalProgram transformToDTO (OptionalProgramEntity optionalProgramCodeEntity) {
    	return modelMapper.map(optionalProgramCodeEntity, OptionalProgram.class);
    }

    public OptionalProgram transformToDTO ( Optional<OptionalProgramEntity> optionalProgramCodeEntity ) {
    	OptionalProgramEntity cae = new OptionalProgramEntity();
        if (optionalProgramCodeEntity.isPresent())
            cae = optionalProgramCodeEntity.get();

        return modelMapper.map(cae, OptionalProgram.class);
    }

	public List<OptionalProgram> transformToDTO (Iterable<OptionalProgramEntity> courseEntities ) {
		List<OptionalProgram> programList = new ArrayList<>();
        for (OptionalProgramEntity courseEntity : courseEntities) {
        	OptionalProgram program = modelMapper.map(courseEntity, OptionalProgram.class);            
            programList.add(program);
        }
        return programList;
    }

    public OptionalProgramEntity transformToEntity(OptionalProgram gradOptionalProgram) {
        return modelMapper.map(gradOptionalProgram, OptionalProgramEntity.class);
    }
}
