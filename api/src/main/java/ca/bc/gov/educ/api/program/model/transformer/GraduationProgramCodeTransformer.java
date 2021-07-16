package ca.bc.gov.educ.api.program.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.program.model.dto.GraduationProgramCode;
import ca.bc.gov.educ.api.program.model.entity.GraduationProgramCodeEntity;


@Component
public class GraduationProgramCodeTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GraduationProgramCode transformToDTO (GraduationProgramCodeEntity gradProgramEntity) {
        return modelMapper.map(gradProgramEntity, GraduationProgramCode.class);
    }

    public GraduationProgramCode transformToDTO ( Optional<GraduationProgramCodeEntity> gradProgramEntity ) {
    	GraduationProgramCodeEntity cae = new GraduationProgramCodeEntity();
        if (gradProgramEntity.isPresent())
            cae = gradProgramEntity.get();

        return modelMapper.map(cae, GraduationProgramCode.class);
    }

	public List<GraduationProgramCode> transformToDTO (Iterable<GraduationProgramCodeEntity> courseEntities ) {
		List<GraduationProgramCode> programList = new ArrayList<>();
        for (GraduationProgramCodeEntity courseEntity : courseEntities) {
        	GraduationProgramCode program = modelMapper.map(courseEntity, GraduationProgramCode.class);            
            programList.add(program);
        }
        return programList;
    }

    public GraduationProgramCodeEntity transformToEntity(GraduationProgramCode gradProgram) {
        return modelMapper.map(gradProgram, GraduationProgramCodeEntity.class);
    }
}
