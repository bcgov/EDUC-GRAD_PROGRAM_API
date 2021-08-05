package ca.bc.gov.educ.api.program.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import ca.bc.gov.educ.api.program.model.dto.CareerProgram;
import ca.bc.gov.educ.api.program.model.dto.GradProgramAlgorithmData;
import ca.bc.gov.educ.api.program.model.dto.GradRuleDetails;
import ca.bc.gov.educ.api.program.model.dto.GraduationProgramCode;
import ca.bc.gov.educ.api.program.model.dto.OptionalProgram;
import ca.bc.gov.educ.api.program.model.dto.OptionalProgramRequirement;
import ca.bc.gov.educ.api.program.model.dto.OptionalProgramRequirementCode;
import ca.bc.gov.educ.api.program.model.dto.ProgramRequirement;
import ca.bc.gov.educ.api.program.model.dto.ProgramRequirementCode;
import ca.bc.gov.educ.api.program.model.dto.RequirementTypeCode;
import ca.bc.gov.educ.api.program.model.entity.CareerProgramEntity;
import ca.bc.gov.educ.api.program.model.entity.GraduationProgramCodeEntity;
import ca.bc.gov.educ.api.program.model.entity.OptionalProgramEntity;
import ca.bc.gov.educ.api.program.model.entity.OptionalProgramRequirementEntity;
import ca.bc.gov.educ.api.program.model.entity.ProgramRequirementEntity;
import ca.bc.gov.educ.api.program.model.entity.RequirementTypeCodeEntity;
import ca.bc.gov.educ.api.program.model.transformer.CareerProgramTransformer;
import ca.bc.gov.educ.api.program.model.transformer.GraduationProgramCodeTransformer;
import ca.bc.gov.educ.api.program.model.transformer.OptionalProgramRequirementCodeTransformer;
import ca.bc.gov.educ.api.program.model.transformer.OptionalProgramRequirementTransformer;
import ca.bc.gov.educ.api.program.model.transformer.OptionalProgramTransformer;
import ca.bc.gov.educ.api.program.model.transformer.ProgramRequirementCodeTransformer;
import ca.bc.gov.educ.api.program.model.transformer.ProgramRequirementTransformer;
import ca.bc.gov.educ.api.program.model.transformer.RequirementTypeCodeTransformer;
import ca.bc.gov.educ.api.program.repository.CareerProgramRepository;
import ca.bc.gov.educ.api.program.repository.GraduationProgramCodeRepository;
import ca.bc.gov.educ.api.program.repository.OptionalProgramRepository;
import ca.bc.gov.educ.api.program.repository.OptionalProgramRequirementCodeRepository;
import ca.bc.gov.educ.api.program.repository.OptionalProgramRequirementRepository;
import ca.bc.gov.educ.api.program.repository.ProgramRequirementCodeRepository;
import ca.bc.gov.educ.api.program.repository.ProgramRequirementRepository;
import ca.bc.gov.educ.api.program.repository.RequirementTypeCodeRepository;
import ca.bc.gov.educ.api.program.util.GradValidation;

@Service
public class ProgramService {

    @Autowired
    private GraduationProgramCodeRepository graduationProgramCodeRepository;  

    @Autowired
    private GraduationProgramCodeTransformer graduationProgramCodeTransformer;    
    
    @Autowired
    private ProgramRequirementRepository programRequirementRepository;     
    
    @Autowired
    private ProgramRequirementTransformer programRequirementTransformer;
    
    @Autowired
    private ProgramRequirementCodeRepository programRequirementCodeRepository; 
    
    @Autowired
    private OptionalProgramRequirementCodeRepository optionalProgramRequirementCodeRepository; 
    
    
    @Autowired
    private OptionalProgramRequirementRepository optionalProgramRequirementRepository;  

    @Autowired
    private OptionalProgramRequirementTransformer optionalProgramRequirementTransformer; 
    
    @Autowired
    private ProgramRequirementCodeTransformer programRequirementCodeTransformer; 
    
    @Autowired
    private OptionalProgramRequirementCodeTransformer optionalProgramRequirementCodeTransformer; 
    
    @Autowired
    private OptionalProgramRepository optionalProgramRepository;  

    @Autowired
    private OptionalProgramTransformer optionalProgramTransformer;  
    
    @Autowired
	private CareerProgramRepository gradCareerProgramRepository;

	@Autowired
	private CareerProgramTransformer gradCareerProgramTransformer;
	
	@Autowired
	private RequirementTypeCodeRepository requirementTypeCodeRepository;

	@Autowired
	private RequirementTypeCodeTransformer requirementTypeCodeTransformer;
    
    @Autowired
	GradValidation validation;
    
    
    @Value("${validation.value.requirementType}")
	String errorStringRequirementTypeInvalid;
    
    @Value("${validation.value.ruleCode_programCode}")
	String errorStringRuleCodeProgramCodeAssociated;
    
    @Value("${validation.value.programcode}")
	String errorStringProgramCodeExists;
    
    @Value("${validation.value.programcode_rule_check}")
   	String errorStringProgramCodeRuleCheck;
    
    @Value("${validation.value.programcode_specialprogram_check}")
   	String errorStringProgramCodeSpecialProgramCheck;    
    
	private static final String CREATE_USER="createUser";
	private static final String CREATE_DATE="createDate";
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    WebClient webClient;

    @SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ProgramService.class);

    
    public List<GraduationProgramCode> getAllProgramList() {
        return graduationProgramCodeTransformer.transformToDTO(graduationProgramCodeRepository.findAll()); 
    }
	
    
	public List<GradRuleDetails> getSpecificRuleDetails(String ruleCode) {
		List<GradRuleDetails> detailList = new ArrayList<>();
		List<ProgramRequirement> gradProgramRule = programRequirementTransformer.transformToDTO(programRequirementRepository.findByRuleCode(ruleCode));
		if(!gradProgramRule.isEmpty()) {
			gradProgramRule.forEach(gpR -> {
				GradRuleDetails details = new GradRuleDetails();
				details.setRuleCode(gpR.getProgramRequirementCode().getProReqCode());
				details.setRequirementName(gpR.getProgramRequirementCode().getLabel());			
				details.setProgramCode(gpR.getGraduationProgramCode());
				detailList.add(details);
			});			
		}
		List<OptionalProgramRequirement> gradSpecialProgramRule = optionalProgramRequirementTransformer.transformToDTO(optionalProgramRequirementRepository.findByRuleCode(ruleCode));
		if(!gradSpecialProgramRule.isEmpty()) {
			gradSpecialProgramRule.forEach(gpR -> {
				GradRuleDetails details = new GradRuleDetails();
				details.setRuleCode(gpR.getOptionalProgramRequirementCode().getOptProReqCode());
				details.setRequirementName(gpR.getOptionalProgramRequirementCode().getLabel());	
				OptionalProgram gradSpecialProgram = optionalProgramTransformer.transformToDTO(optionalProgramRepository.findById(gpR.getOptionalProgramID()));
				details.setProgramCode(gradSpecialProgram.getGraduationProgramCode());
				details.setSpecialProgramCode(gradSpecialProgram.getOptProgramCode());
				detailList.add(details);
			});			
		}
		return detailList;
	}
	
	public GraduationProgramCode createGradProgram(GraduationProgramCode gradProgram) {
		GraduationProgramCodeEntity toBeSavedObject = graduationProgramCodeTransformer.transformToEntity(gradProgram);
		toBeSavedObject.setDisplayOrder(0);
		try {
			toBeSavedObject.setExpiryDate(new SimpleDateFormat("yyyy/MM/dd").parse("2099/12/31"));
		} catch (ParseException e) {
			logger.debug(e.getMessage());
		}
		Optional<GraduationProgramCodeEntity> existingObjectCheck = graduationProgramCodeRepository.findById(gradProgram.getProgramCode());
		if(existingObjectCheck.isPresent()) {
			validation.addErrorAndStop(String.format(errorStringProgramCodeExists,gradProgram.getProgramCode()));
			return null;			
		}else {
			return graduationProgramCodeTransformer.transformToDTO(graduationProgramCodeRepository.save(toBeSavedObject));
		}		
	}
	
	@Transactional
	public GraduationProgramCode updateGradProgram(GraduationProgramCode gradProgram) {
		Optional<GraduationProgramCodeEntity> gradProgramOptional = graduationProgramCodeRepository.findById(gradProgram.getProgramCode());
		GraduationProgramCodeEntity sourceObject = graduationProgramCodeTransformer.transformToEntity(gradProgram);
		if(gradProgramOptional.isPresent()) {			
			GraduationProgramCodeEntity gradEnity = gradProgramOptional.get();			
			BeanUtils.copyProperties(sourceObject,gradEnity,CREATE_USER,CREATE_DATE,"effectiveDate","expiryDate");
			return graduationProgramCodeTransformer.transformToDTO(graduationProgramCodeRepository.save(gradEnity));
		}else {
			validation.addErrorAndStop(String.format(errorStringProgramCodeExists,gradProgram.getProgramCode()));
			return gradProgram;
		}
	}

	public int deleteGradPrograms(@Valid String programCode) {
		Optional<GraduationProgramCodeEntity> gradProgramOptional = graduationProgramCodeRepository.findIfChildRecordsExists(programCode);
		if(gradProgramOptional.isPresent()) {
			validation.addErrorAndStop(String.format(errorStringProgramCodeRuleCheck,gradProgramOptional.get().getProgramCode()));
			return 0;
		}else {
			Optional<GraduationProgramCodeEntity> gradSpecialProgramOptional = graduationProgramCodeRepository.findIfSpecialProgramsExists(programCode);
			if(gradSpecialProgramOptional.isPresent()) {
				validation.addErrorAndStop(String.format(errorStringProgramCodeSpecialProgramCheck,programCode));
				return 0;
			}else {
				graduationProgramCodeRepository.deleteById(programCode);
				return 1;
			}			
		}		
	}

	public ProgramRequirement createGradProgramRules(@Valid ProgramRequirement gradProgramRule) {
		ProgramRequirementEntity toBeSavedObject = programRequirementTransformer.transformToEntity(gradProgramRule);
		UUID existingObjectCheck = programRequirementRepository.findIdByRuleCode(gradProgramRule.getProgramRequirementCode().getProReqCode(),gradProgramRule.getGraduationProgramCode());
		if(existingObjectCheck != null) {
			validation.addErrorAndStop(String.format(errorStringRuleCodeProgramCodeAssociated,gradProgramRule.getProgramRequirementCode().getProReqCode(),gradProgramRule.getGraduationProgramCode()));
			return gradProgramRule;			
		}else {
			toBeSavedObject.setProgramRequirementCode(programRequirementCodeRepository.getOne(gradProgramRule.getProgramRequirementCode().getProReqCode()));
			return programRequirementTransformer.transformToDTO(programRequirementRepository.save(toBeSavedObject));					
		}
	}

	public ProgramRequirement updateGradProgramRules(@Valid ProgramRequirement gradProgramRule) {
		ProgramRequirementEntity sourceObject = programRequirementTransformer.transformToEntity(gradProgramRule);
		Optional<ProgramRequirementEntity> gradProgramRulesOptional = programRequirementRepository.findById(gradProgramRule.getProgramRequirementID());
		if(gradProgramRulesOptional.isPresent()) {
			ProgramRequirementEntity gradRuleEnity = gradProgramRulesOptional.get();			
			if(checkIfRuleCodeChanged(gradRuleEnity,sourceObject)) {				
				UUID existingObjectCheck = programRequirementRepository.findIdByRuleCode(sourceObject.getProgramRequirementCode().getProReqCode(), sourceObject.getGraduationProgramCode());
				if(existingObjectCheck != null) {
					validation.addErrorAndStop(String.format(errorStringRuleCodeProgramCodeAssociated,gradProgramRule.getProgramRequirementCode().getProReqCode(),gradProgramRule.getGraduationProgramCode()));
					return gradProgramRule;			
				}
			}
			BeanUtils.copyProperties(sourceObject,gradRuleEnity,CREATE_USER,CREATE_DATE);
			gradRuleEnity.setProgramRequirementCode(programRequirementCodeRepository.getOne(gradRuleEnity.getProgramRequirementCode().getProReqCode()));
			return programRequirementTransformer.transformToDTO(programRequirementRepository.save(gradRuleEnity));
		}else {
			validation.addErrorAndStop("Unique Identifier not found. Update Failed");
			return gradProgramRule;
		}
	}
	
	private boolean checkIfRuleCodeChanged(ProgramRequirementEntity gradRuleEnity, ProgramRequirementEntity sourceObject) {
		return !sourceObject.getProgramRequirementCode().getProReqCode().equals(gradRuleEnity.getProgramRequirementCode().getProReqCode());		
	}
	
	private boolean checkIfOptionalRuleCodeChanged(OptionalProgramRequirementEntity gradRuleEnity, OptionalProgramRequirementEntity sourceObject) {
		return !sourceObject.getOptionalProgramRequirementCode().getOptProReqCode().equals(gradRuleEnity.getOptionalProgramRequirementCode().getOptProReqCode());
	}

	public int deleteGradProgramRules(UUID programRuleID) {
		Optional<ProgramRequirementEntity> gradProgramRuleOptional = programRequirementRepository.findById(programRuleID);
		if(gradProgramRuleOptional.isPresent()) {
			programRequirementRepository.deleteById(programRuleID);
			return 1;
		}else {
			validation.addErrorAndStop("This Program Rule does not exists.");
			return 0;			
		}
	}

	public Boolean getRequirementByRequirementType(String typeCode) {
		List<ProgramRequirementEntity> gradList = programRequirementRepository.existsByRequirementTypeCode(typeCode);
		return !gradList.isEmpty();
	}

	public GraduationProgramCode getSpecificProgram(String programCode) {
		Optional<GraduationProgramCodeEntity> gradResponse = graduationProgramCodeRepository.findById(programCode); 
		if(gradResponse.isPresent()) {
			return graduationProgramCodeTransformer.transformToDTO(gradResponse.get());
		}
		return null;
	}

	
	public OptionalProgram createGradSpecialProgram(@Valid OptionalProgram optionalProgram) {
		OptionalProgramEntity toBeSavedObject = optionalProgramTransformer.transformToEntity(optionalProgram);
		Optional<OptionalProgramEntity> existingObjectCheck = optionalProgramRepository.findByGraduationProgramCodeAndOptProgramCode(optionalProgram.getGraduationProgramCode(),optionalProgram.getOptProgramCode());
		if(existingObjectCheck.isPresent()) {
			validation.addErrorAndStop(String.format("Optioanl Program Code [%s] and Program Code [%s] already exists",optionalProgram.getOptProgramCode(),optionalProgram.getGraduationProgramCode()));
			return optionalProgram;			
		}else {
			return optionalProgramTransformer.transformToDTO(optionalProgramRepository.save(toBeSavedObject));
		}
	}
	
	@Transactional
	public OptionalProgram updateGradSpecialPrograms(OptionalProgram optionalProgram) {
		Optional<OptionalProgramEntity> gradSpecialProgramOptional = optionalProgramRepository.findById(optionalProgram.getOptionalProgramID());
		OptionalProgramEntity sourceObject = optionalProgramTransformer.transformToEntity(optionalProgram);
		if(gradSpecialProgramOptional.isPresent()) {			
			OptionalProgramEntity gradEnity = gradSpecialProgramOptional.get();
			if(checkIfProgramCodeandOptionalPogramCodeChanged(gradEnity,sourceObject)) {
				Optional<OptionalProgramEntity> existingObjectCheck = optionalProgramRepository.findByGraduationProgramCodeAndOptProgramCode(optionalProgram.getGraduationProgramCode(),optionalProgram.getOptProgramCode());
				if(existingObjectCheck.isPresent()) {
					validation.addErrorAndStop(String.format("Special Program Code [%s] and Program Code [%s] already exists",optionalProgram.getOptProgramCode(),optionalProgram.getGraduationProgramCode()));
					return optionalProgram;			
				}
			}
			BeanUtils.copyProperties(sourceObject,gradEnity,CREATE_USER,CREATE_DATE);
			return optionalProgramTransformer.transformToDTO(optionalProgramRepository.save(gradEnity));
		}else {
			validation.addErrorAndStop(String.format("Special Program ID [%s] does not exists",optionalProgram.getOptionalProgramID()));
			return optionalProgram;
		}
	}
	
	private boolean checkIfProgramCodeandOptionalPogramCodeChanged(OptionalProgramEntity gradSpecialProgramEntity, OptionalProgramEntity sourceObject) {
		return (!sourceObject.getGraduationProgramCode().equals(gradSpecialProgramEntity.getGraduationProgramCode()) || !sourceObject.getOptProgramCode().equals(gradSpecialProgramEntity.getOptProgramCode()));
				
	}

	public int deleteGradSpecialPrograms(UUID specialProgramID) {
		Optional<OptionalProgramEntity> gradSpecialProgramOptional = optionalProgramRepository.findById(specialProgramID);
		if(gradSpecialProgramOptional.isPresent()) {
			optionalProgramRepository.deleteById(specialProgramID);
			return 1;
		}else {
			validation.addErrorAndStop("This Optional Program ID does not exists.");
			return 0;			
		}
	}
	
	public List<OptionalProgramRequirement>  getAllSpecialProgramRuleList(UUID specialProgramID) {
        return optionalProgramRequirementTransformer.transformToDTO(optionalProgramRequirementRepository.findByOptionalProgramID(specialProgramID));
	}
	
	public OptionalProgramRequirement createGradSpecialProgramRules(@Valid OptionalProgramRequirement optionalProgramRequirement) {
		OptionalProgramRequirementEntity toBeSavedObject = optionalProgramRequirementTransformer.transformToEntity(optionalProgramRequirement);
		UUID existingObjectCheck = optionalProgramRequirementRepository.findIdByRuleCode(optionalProgramRequirement.getOptionalProgramRequirementCode().getOptProReqCode(),optionalProgramRequirement.getOptionalProgramID());
		if(existingObjectCheck != null) {
			OptionalProgramEntity optional = optionalProgramRepository.getOne(optionalProgramRequirement.getOptionalProgramID());
			validation.addErrorAndStop(String.format("This Rule Code [%s] is already associated to a Optional Program Code [%s] and Program Code [%s] combination.",optionalProgramRequirement.getOptionalProgramRequirementCode().getOptProReqCode(),optional.getOptProgramCode(),optional.getGraduationProgramCode()));
			return optionalProgramRequirement;			
		}else {
			toBeSavedObject.setOptionalProgramRequirementCode(optionalProgramRequirementCodeRepository.getOne(optionalProgramRequirement.getOptionalProgramRequirementCode().getOptProReqCode()));
			return optionalProgramRequirementTransformer.transformToDTO(optionalProgramRequirementRepository.save(toBeSavedObject));	
		}
	}
	
	public OptionalProgramRequirement updateGradSpecialProgramRules(@Valid OptionalProgramRequirement optionalProgramRequirement) {
		OptionalProgramRequirementEntity sourceObject = optionalProgramRequirementTransformer.transformToEntity(optionalProgramRequirement);
		Optional<OptionalProgramRequirementEntity> gradSpecialProgramRulesOptional = optionalProgramRequirementRepository.findById(optionalProgramRequirement.getOptionalProgramID());
		if(gradSpecialProgramRulesOptional.isPresent()) {
			OptionalProgramRequirementEntity gradRuleEnity = gradSpecialProgramRulesOptional.get();	
			if(checkIfOptionalRuleCodeChanged(gradRuleEnity,sourceObject)) {				
				UUID existingObjectCheck = optionalProgramRequirementRepository.findIdByRuleCode(sourceObject.getOptionalProgramRequirementCode().getOptProReqCode(), sourceObject.getOptionalProgramID());
				if(existingObjectCheck != null) {
					OptionalProgramEntity optional = optionalProgramRepository.getOne(optionalProgramRequirement.getOptionalProgramID());
					validation.addErrorAndStop(String.format("This Rule Code [%s] is already associated to a Special Program Code [%s] and Program Code [%s] combination.",optionalProgramRequirement.getOptionalProgramRequirementCode().getOptProReqCode(),optional.getOptProgramCode(),optional.getGraduationProgramCode()));
					return optionalProgramRequirement;			
				}
			}
			BeanUtils.copyProperties(sourceObject,gradRuleEnity,CREATE_USER,CREATE_DATE);
			gradRuleEnity.setOptionalProgramRequirementCode(optionalProgramRequirementCodeRepository.getOne(optionalProgramRequirement.getOptionalProgramRequirementCode().getOptProReqCode()));
			return optionalProgramRequirementTransformer.transformToDTO(optionalProgramRequirementRepository.save(gradRuleEnity));			
		}else {
			validation.addErrorAndStop("Unique Identifier not found. Update Failed");
			return optionalProgramRequirement;
		}
	}

	public int deleteGradSpecialProgramRules(UUID programRuleID) {
		Optional<OptionalProgramRequirementEntity> gradSpecialProgramRuleOptional = optionalProgramRequirementRepository.findById(programRuleID);
		if(gradSpecialProgramRuleOptional.isPresent()) {
			programRequirementRepository.deleteById(programRuleID);
			return 1;
		}else {
			validation.addErrorAndStop("This Program Rule does not exists.");
			return 0;			
		}
	}
	
	public List<OptionalProgram> getAllSpecialProgramList() {
		return optionalProgramTransformer.transformToDTO(optionalProgramRepository.findAll());      
	}
	
	public OptionalProgram getSpecialProgramByID(UUID specialProgramID) {
		return optionalProgramTransformer.transformToDTO(optionalProgramRepository.findById(specialProgramID));
	}
	
	public List<OptionalProgramRequirement> getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(String programCode,
			String specialProgramCode) {
		Optional<OptionalProgramEntity> existingObjectCheck = optionalProgramRepository.findByGraduationProgramCodeAndOptProgramCode(programCode, specialProgramCode);
		if(existingObjectCheck.isPresent()) {
			return getAllSpecialProgramRuleList(existingObjectCheck.get().getOptionalProgramID());
		}else {
			validation.addErrorAndStop(String.format("Special Program Code [%s] and Program Code [%s] combination does not exist",specialProgramCode,programCode));
			return new ArrayList<>();
		}
	}
	
	public List<ProgramRequirement> getAllProgramRulesList() {
		List<ProgramRequirement> programRuleList  = programRequirementTransformer.transformToDTO(programRequirementRepository.findAll());   
    	if(!programRuleList.isEmpty()) {
	    	Collections.sort(programRuleList, Comparator.comparing(ProgramRequirement::getGraduationProgramCode)); 
    	}
        return programRuleList;
	}

	public List<ProgramRequirement> getAllProgramRuleList(String programCode) {
      	return programRequirementTransformer.transformToDTO(programRequirementRepository.findByGraduationProgramCode(programCode));
	}
	
	
	public List<OptionalProgramRequirement>  getAllSpecialProgramRulesList() {
        return optionalProgramRequirementTransformer.transformToDTO(optionalProgramRequirementRepository.findAll());
	}


	public OptionalProgram getSpecialProgram(String programCode, String specialProgramCode) {
		Optional<OptionalProgramEntity> optionalRec = optionalProgramRepository.findByGraduationProgramCodeAndOptProgramCode(programCode, specialProgramCode);
		if(optionalRec.isPresent()) {
			return optionalProgramTransformer.transformToDTO(optionalRec.get());
		}else {
			validation.addErrorAndStop(String.format("Optional Program Code [%s] and Program Code [%s] combination does not exist",specialProgramCode,programCode));
			return null;
		}
	}


	public List<ProgramRequirementCode> getAllProgramRequirementCodeList() {
		return programRequirementCodeTransformer.transformToDTO(programRequirementCodeRepository.findAll()); 
	}
	
	public List<OptionalProgramRequirementCode> getAllOptionalProgramRequirementCodeList() {
		return optionalProgramRequirementCodeTransformer.transformToDTO(optionalProgramRequirementCodeRepository.findAll()); 
	}


	public GradProgramAlgorithmData getAllAlgorithmData(String programCode, String optionalProgramCode) {
		GradProgramAlgorithmData data = new GradProgramAlgorithmData();
		List<ProgramRequirement> programRules = getAllProgramRuleList(programCode);
		data.setProgramRules(programRules);
		if(StringUtils.isNotBlank(optionalProgramCode)) {
			List<OptionalProgramRequirement> optionalProgramRules = getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode, optionalProgramCode);
			data.setOptionalProgramRules(optionalProgramRules);
		}
		return data;		
	}
	
	@Transactional
	public List<CareerProgram> getAllCareerProgramCodeList() {
		List<CareerProgram> gradCareerProgramList = gradCareerProgramTransformer.transformToDTO(gradCareerProgramRepository.findAll());
		Collections.sort(gradCareerProgramList, Comparator.comparing(CareerProgram::getCode));
		return gradCareerProgramList;
	}

	@Transactional
	public CareerProgram getSpecificCareerProgramCode(String cpc) {
		Optional<CareerProgramEntity> entity = gradCareerProgramRepository
				.findById(StringUtils.toRootUpperCase(cpc));
		if (entity.isPresent()) {
			return gradCareerProgramTransformer.transformToDTO(entity);
		} else {
			return null;
		}
	}
	
	
	@Transactional
	public List<RequirementTypeCode> getAllRequirementTypeCodeList() {
		return requirementTypeCodeTransformer.transformToDTO(requirementTypeCodeRepository.findAll());
	}

	@Transactional
	public RequirementTypeCode getSpecificRequirementTypeCode(String typeCode) {
		Optional<RequirementTypeCodeEntity> entity = requirementTypeCodeRepository
				.findById(StringUtils.toRootUpperCase(typeCode));
		if (entity.isPresent()) {
			return requirementTypeCodeTransformer.transformToDTO(entity.get());
		} else {
			return null;
		}
	}
	
	public RequirementTypeCode createRequirementTypeCode(@Valid RequirementTypeCode gradRequirementTypes) {
		RequirementTypeCodeEntity toBeSavedObject = requirementTypeCodeTransformer.transformToEntity(gradRequirementTypes);
		Optional<RequirementTypeCodeEntity> existingObjectCheck = requirementTypeCodeRepository.findById(gradRequirementTypes.getReqTypeCode());
		if(existingObjectCheck.isPresent()) {
			validation.addErrorAndStop(String.format("Requirement Type [%s] already exists",gradRequirementTypes.getReqTypeCode()));
			return gradRequirementTypes;			
		}else {
			return requirementTypeCodeTransformer.transformToDTO(requirementTypeCodeRepository.save(toBeSavedObject));
		}
	}

	public RequirementTypeCode updateRequirementTypeCode(@Valid RequirementTypeCode gradRequirementTypes) {
		Optional<RequirementTypeCodeEntity> gradRequirementTypesOptional = requirementTypeCodeRepository.findById(gradRequirementTypes.getReqTypeCode());
		RequirementTypeCodeEntity sourceObject = requirementTypeCodeTransformer.transformToEntity(gradRequirementTypes);
		if(gradRequirementTypesOptional.isPresent()) {
			RequirementTypeCodeEntity gradEnity = gradRequirementTypesOptional.get();			
			BeanUtils.copyProperties(sourceObject,gradEnity,CREATE_USER,CREATE_DATE);
    		return requirementTypeCodeTransformer.transformToDTO(requirementTypeCodeRepository.save(gradEnity));
		}else {
			validation.addErrorAndStop(String.format("Requirement Type [%s] does not exists",gradRequirementTypes.getReqTypeCode()));
			return gradRequirementTypes;
		}
	}

	public int deleteRequirementTypeCode(@Valid String programType) {
		Boolean isPresent = getRequirementByRequirementType(programType);
		if(isPresent) {
			validation.addErrorAndStop(
					String.format("This Requirement Type [%s] cannot be deleted as some rules are of this type.",programType));
			return 0;
		}else {
			requirementTypeCodeRepository.deleteById(programType);
			return 1;
		}
	}
	
}
