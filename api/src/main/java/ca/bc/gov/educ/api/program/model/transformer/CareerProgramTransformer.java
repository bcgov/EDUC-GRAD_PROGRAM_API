package ca.bc.gov.educ.api.program.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.program.model.dto.CareerProgram;
import ca.bc.gov.educ.api.program.model.entity.CareerProgramEntity;
import ca.bc.gov.educ.api.program.util.EducGradProgramManagementApiUtils;


@Component
public class CareerProgramTransformer {

    @Autowired
    ModelMapper modelMapper;

    public CareerProgram transformToDTO (CareerProgramEntity gradCareerProgramEntity) {
    	return modelMapper.map(gradCareerProgramEntity, CareerProgram.class);
    }

    public CareerProgram transformToDTO ( Optional<CareerProgramEntity> gradCareerProgramEntity ) {
    	CareerProgramEntity cae = new CareerProgramEntity();
        if (gradCareerProgramEntity.isPresent())
            cae = gradCareerProgramEntity.get();

        return modelMapper.map(cae, CareerProgram.class);
    }

	public List<CareerProgram> transformToDTO (Iterable<CareerProgramEntity> courseEntities ) {
		List<CareerProgram> programList = new ArrayList<>();
        for (CareerProgramEntity courseEntity : courseEntities) {
        	CareerProgram program = modelMapper.map(courseEntity, CareerProgram.class);   
        	program.setStartDate(EducGradProgramManagementApiUtils.parseDateFromString(program.getStartDate() != null ? program.getStartDate():null));
        	program.setEndDate(EducGradProgramManagementApiUtils.parseDateFromString(program.getEndDate() != null ? program.getEndDate():null));
            programList.add(program);
        }
        return programList;
    }

    public CareerProgramEntity transformToEntity(CareerProgram gradCareerProgram) {
        return modelMapper.map(gradCareerProgram, CareerProgramEntity.class);
    }
}
