package ca.bc.gov.educ.api.program.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import ca.bc.gov.educ.api.program.model.dto.GradRequirementTypes;
import ca.bc.gov.educ.api.program.model.dto.GraduationProgramCode;
import ca.bc.gov.educ.api.program.model.dto.ProgramRequirement;
import ca.bc.gov.educ.api.program.model.entity.GradSpecialProgramRulesEntity;
import ca.bc.gov.educ.api.program.model.entity.GraduationProgramCodeEntity;
import ca.bc.gov.educ.api.program.model.entity.ProgramRequirementEntity;
import ca.bc.gov.educ.api.program.model.transformer.GraduationProgramCodeTransformer;
import ca.bc.gov.educ.api.program.model.transformer.OptionalProgramRequirementTransformer;
import ca.bc.gov.educ.api.program.model.transformer.ProgramRequirementTransformer;
import ca.bc.gov.educ.api.program.repository.GraduationProgramCodeRepository;
import ca.bc.gov.educ.api.program.repository.OptionalProgramRequirementRepository;
import ca.bc.gov.educ.api.program.repository.ProgramRequirementCodeRepository;
import ca.bc.gov.educ.api.program.repository.ProgramRequirementRepository;
import ca.bc.gov.educ.api.program.util.EducGradProgramApiConstants;
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
    private OptionalProgramRequirementRepository optionalProgramRequirementRepository;  

    @Autowired
    private OptionalProgramRequirementTransformer optionalProgramRequirementTransformer;    
    
    @Autowired
	GradValidation validation;
    
    @Autowired
	EducGradProgramApiConstants educGradProgramManagementApiConstants;
    
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
	private static final String CREATE="create";
	private static final String UPDATE="update";
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    WebClient webClient;

    @SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ProgramService.class);

    
    public List<GraduationProgramCode> getAllProgramList() {
        return graduationProgramCodeTransformer.transformToDTO(graduationProgramCodeRepository.findAll()); 
    }
	
    /*
	public List<GradRuleDetails> getSpecificRuleDetails(String ruleCode) {
		List<GradRuleDetails> detailList = new ArrayList<>();
		List<ProgramRequirement> gradProgramRule = programRequirementTransformer.transformToDTO(programRequirementRepository.findByRuleCode(ruleCode));
		if(!gradProgramRule.isEmpty()) {
			gradProgramRule.forEach(gpR -> {
				GradRuleDetails details = new GradRuleDetails();
				details.setRuleCode(gpR.getRuleCode());
				details.setRequirementName(gpR.getRequirementName());			
				details.setProgramCode(gpR.getProgramCode());
				detailList.add(details);
			});			
		}
		List<GradSpecialProgramRule> gradSpecialProgramRule = gradSpecialProgramRulesTransformer.transformToDTO(gradSpecialProgramRulesRepository.findByRuleCode(ruleCode));
		if(!gradSpecialProgramRule.isEmpty()) {
			gradSpecialProgramRule.forEach(gpR -> {
				GradRuleDetails details = new GradRuleDetails();
				details.setRuleCode(gpR.getRuleCode());
				details.setRequirementName(gpR.getRequirementName());	
				GradSpecialProgram gradSpecialProgram = gradSpecialProgramTransformer.transformToDTO(gradSpecialProgramRepository.findById(gpR.getSpecialProgramID()));
				details.setProgramCode(gradSpecialProgram.getProgramCode());
				details.setSpecialProgramCode(gradSpecialProgram.getSpecialProgramCode());
				detailList.add(details);
			});			
		}
		return detailList;
	}
	*/
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
	
	public boolean validateRequirementType(String toBeSavedRequirementType, String existingRequirementType,String accessToken,String task) {
		if(task.equalsIgnoreCase(CREATE) || !existingRequirementType.equalsIgnoreCase(toBeSavedRequirementType)) {
			GradRequirementTypes reqTypes = webClient.get().uri(String.format(educGradProgramManagementApiConstants.getGradRequirementTypeByCode(),toBeSavedRequirementType)).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(GradRequirementTypes.class).block();
			return reqTypes != null;
		}
		return true;
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
	
	private boolean checkIfSpecialRuleCodeChanged(GradSpecialProgramRulesEntity gradRuleEnity, GradSpecialProgramRulesEntity sourceObject) {
		return !sourceObject.getRuleCode().equals(gradRuleEnity.getRuleCode());
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

	/*
	public GradSpecialProgram createGradSpecialProgram(@Valid GradSpecialProgram gradSpecialProgram) {
		GradSpecialProgramEntity toBeSavedObject = gradSpecialProgramTransformer.transformToEntity(gradSpecialProgram);
		Optional<GradSpecialProgramEntity> existingObjectCheck = gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(gradSpecialProgram.getProgramCode(),gradSpecialProgram.getSpecialProgramCode());
		if(existingObjectCheck.isPresent()) {
			validation.addErrorAndStop(String.format("Special Program Code [%s] and Program Code [%s] already exists",gradSpecialProgram.getSpecialProgramCode(),gradSpecialProgram.getProgramCode()));
			return gradSpecialProgram;			
		}else {
			return gradSpecialProgramTransformer.transformToDTO(gradSpecialProgramRepository.save(toBeSavedObject));
		}
	}
	
	@Transactional
	public GradSpecialProgram updateGradSpecialPrograms(GradSpecialProgram gradSpecialProgram) {
		Optional<GradSpecialProgramEntity> gradSpecialProgramOptional = gradSpecialProgramRepository.findById(gradSpecialProgram.getId());
		GradSpecialProgramEntity sourceObject = gradSpecialProgramTransformer.transformToEntity(gradSpecialProgram);
		if(gradSpecialProgramOptional.isPresent()) {			
			GradSpecialProgramEntity gradEnity = gradSpecialProgramOptional.get();
			if(checkIfProgramCodeandSpecialPogramCodeChanged(gradEnity,sourceObject)) {
				Optional<GradSpecialProgramEntity> existingObjectCheck = gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(gradSpecialProgram.getProgramCode(),gradSpecialProgram.getSpecialProgramCode());
				if(existingObjectCheck.isPresent()) {
					validation.addErrorAndStop(String.format("Special Program Code [%s] and Program Code [%s] already exists",gradSpecialProgram.getSpecialProgramCode(),gradSpecialProgram.getProgramCode()));
					return gradSpecialProgram;			
				}
			}
			BeanUtils.copyProperties(sourceObject,gradEnity,CREATED_BY,CREATED_TIMESTAMP);
			return gradSpecialProgramTransformer.transformToDTO(gradSpecialProgramRepository.save(gradEnity));
		}else {
			validation.addErrorAndStop(String.format("Special Program ID [%s] does not exists",gradSpecialProgram.getId()));
			return gradSpecialProgram;
		}
	}
	
	private boolean checkIfProgramCodeandSpecialPogramCodeChanged(GradSpecialProgramEntity gradSpecialProgramEntity, GradSpecialProgramEntity sourceObject) {
		return (!sourceObject.getProgramCode().equals(gradSpecialProgramEntity.getProgramCode()) || !sourceObject.getSpecialProgramCode().equals(gradSpecialProgramEntity.getSpecialProgramCode()));
				
	}

	public int deleteGradSpecialPrograms(UUID specialProgramID) {
		Optional<GradSpecialProgramEntity> gradSpecialProgramOptional = gradSpecialProgramRepository.findById(specialProgramID);
		if(gradSpecialProgramOptional.isPresent()) {
			gradSpecialProgramRepository.deleteById(specialProgramID);
			return 1;
		}else {
			validation.addErrorAndStop("This Special Program ID does not exists.");
			return 0;			
		}
	}

	public List<GradSpecialProgram> getAllSpecialProgramList(String programCode) {
		return gradSpecialProgramTransformer.transformToDTO(gradSpecialProgramRepository.findByProgramCode(programCode));
	}
	
	public List<GradSpecialProgramRule>  getAllSpecialProgramRuleList(UUID specialProgramID, String requirementType,String accessToken) {
		if(StringUtils.isNotBlank(requirementType)) {
    		GradRequirementTypes gradReqType = webClient.get().uri(String.format(educGradProgramManagementApiConstants.getGradRequirementTypeByCode(),requirementType)).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(GradRequirementTypes.class).block();
			if(gradReqType == null) {
				validation.addErrorAndStop(String.format(errorStringRequirementTypeInvalid,requirementType));
	    	}
        }
        List<GradSpecialProgramRule> programRuleList = gradSpecialProgramRulesTransformer.transformToDTO(gradSpecialProgramRulesRepository.findBySpecialProgramIDAndRequirementType(specialProgramID,requirementType));   
    	programRuleList.forEach(pR-> {
    		GradRequirementTypes reqType = webClient.get().uri(String.format(educGradProgramManagementApiConstants.getGradRequirementTypeByCode(),pR.getRequirementType())).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(GradRequirementTypes.class).block();
    		pR.setRequirementTypeDesc(reqType.getDescription());
    	});
        return programRuleList;
	}

	public GradSpecialProgramRule createGradSpecialProgramRules(@Valid GradSpecialProgramRule gradSpecialProgramRule,
			String accessToken) {
		GradSpecialProgramRulesEntity toBeSavedObject = gradSpecialProgramRulesTransformer.transformToEntity(gradSpecialProgramRule);
		UUID existingObjectCheck = gradSpecialProgramRulesRepository.findIdByRuleCode(gradSpecialProgramRule.getRuleCode(),gradSpecialProgramRule.getSpecialProgramID());
		if(existingObjectCheck != null) {
			GradSpecialProgramEntity optional = gradSpecialProgramRepository.getOne(gradSpecialProgramRule.getSpecialProgramID());
			validation.addErrorAndStop(String.format("This Rule Code [%s] is already associated to a Special Program Code [%s] and Program Code [%s] combination.",gradSpecialProgramRule.getRuleCode(),optional.getSpecialProgramCode(),optional.getProgramCode()));
			return gradSpecialProgramRule;			
		}else {	
			if(validateRequirementType(gradSpecialProgramRule.getRequirementType(),gradSpecialProgramRule.getRequirementType(),accessToken,CREATE)) {
					return gradSpecialProgramRulesTransformer.transformToDTO(gradSpecialProgramRulesRepository.save(toBeSavedObject));
			}else {
				validation.addErrorAndStop(String.format(errorStringRequirementTypeInvalid,gradSpecialProgramRule.getRequirementType()));
				return null;
			}	
		}
	}
	
	public GradSpecialProgramRule updateGradSpecialProgramRules(@Valid GradSpecialProgramRule gradSpecialProgramRule, String accessToken) {
		GradSpecialProgramRulesEntity sourceObject = gradSpecialProgramRulesTransformer.transformToEntity(gradSpecialProgramRule);
		Optional<GradSpecialProgramRulesEntity> gradSpecialProgramRulesOptional = gradSpecialProgramRulesRepository.findById(gradSpecialProgramRule.getId());
		if(gradSpecialProgramRulesOptional.isPresent()) {
			GradSpecialProgramRulesEntity gradRuleEnity = gradSpecialProgramRulesOptional.get();	
			if(checkIfSpecialRuleCodeChanged(gradRuleEnity,sourceObject)) {				
				UUID existingObjectCheck = gradSpecialProgramRulesRepository.findIdByRuleCode(sourceObject.getRuleCode(), sourceObject.getSpecialProgramID());
				if(existingObjectCheck != null) {
					GradSpecialProgramEntity optional = gradSpecialProgramRepository.getOne(gradSpecialProgramRule.getSpecialProgramID());
					validation.addErrorAndStop(String.format("This Rule Code [%s] is already associated to a Special Program Code [%s] and Program Code [%s] combination.",gradSpecialProgramRule.getRuleCode(),optional.getSpecialProgramCode(),optional.getProgramCode()));
					return gradSpecialProgramRule;			
				}
			}
			if(validateRequirementType(gradSpecialProgramRule.getRequirementType(),gradRuleEnity.getRequirementType(),accessToken,UPDATE)) {
				BeanUtils.copyProperties(sourceObject,gradRuleEnity,CREATED_BY,CREATED_TIMESTAMP);
				return gradSpecialProgramRulesTransformer.transformToDTO(gradSpecialProgramRulesRepository.save(gradRuleEnity));
			}else {
				validation.addErrorAndStop(String.format(errorStringRequirementTypeInvalid,gradSpecialProgramRule.getRequirementType()));
				return null;
			}			
		}else {
			validation.addErrorAndStop("Unique Identifier not found. Update Failed");
			return gradSpecialProgramRule;
		}
	}

	public int deleteGradSpecialProgramRules(UUID programRuleID) {
		Optional<GradSpecialProgramRulesEntity> gradSpecialProgramRuleOptional = gradSpecialProgramRulesRepository.findById(programRuleID);
		if(gradSpecialProgramRuleOptional.isPresent()) {
			programRequirementRepository.deleteById(programRuleID);
			return 1;
		}else {
			validation.addErrorAndStop("This Program Rule does not exists.");
			return 0;			
		}
	}

	public List<GradSpecialProgram> getAllSpecialProgramList() {
		return gradSpecialProgramTransformer.transformToDTO(gradSpecialProgramRepository.findAll());      
	}

	public GradSpecialProgram getSpecialProgramByID(UUID specialProgramID) {
		return gradSpecialProgramTransformer.transformToDTO(gradSpecialProgramRepository.findById(specialProgramID));
	}

	public List<GradSpecialProgramRule> getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(String programCode,
			String specialProgramCode,String requirementType,String accessToken) {
		Optional<GradSpecialProgramEntity> existingObjectCheck = gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode);
		if(existingObjectCheck.isPresent()) {
			return getAllSpecialProgramRuleList(existingObjectCheck.get().getId(),requirementType,accessToken);
		}else {
			validation.addErrorAndStop(String.format("Special Program Code [%s] and Program Code [%s] combination does not exist",specialProgramCode,programCode));
			return new ArrayList<>();
		}
	}

	public GradSpecialProgram getSpecialProgram(String programCode, String specialProgramCode) {
		Optional<GradSpecialProgramEntity> optionalRec = gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode);
		if(optionalRec.isPresent()) {
			return gradSpecialProgramTransformer.transformToDTO(optionalRec.get());
		}else {
			validation.addErrorAndStop(String.format("Special Program Code [%s] and Program Code [%s] combination does not exist",specialProgramCode,programCode));
			return null;
		}
	}
	*/
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
	
	/*
	public List<GradSpecialProgramRule>  getAllSpecialProgramRulesList(String accessToken) {
		List<GradSpecialProgramRule> programRuleList  = gradSpecialProgramRulesTransformer.transformToDTO(gradSpecialProgramRulesRepository.findAll());   
    	programRuleList.forEach(pR-> {
    		GradSpecialProgram gSp = gradSpecialProgramTransformer.transformToDTO(gradSpecialProgramRepository.findById(pR.getSpecialProgramID()));
    		GradRequirementTypes reqType = webClient.get().uri(String.format(educGradProgramManagementApiConstants.getGradRequirementTypeByCode(),pR.getRequirementType())).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(GradRequirementTypes.class).block();
    		pR.setRequirementTypeDesc(reqType.getDescription());
    		pR.setProgramCode(gSp.getProgramCode());
    		pR.setSpecialProgramCode(gSp.getSpecialProgramCode());
    	});
    	if(!programRuleList.isEmpty()) {
	    	Collections.sort(programRuleList, Comparator.comparing(GradSpecialProgramRule::getProgramCode)
	    			.thenComparing(GradSpecialProgramRule::getSpecialProgramCode)
	    			.thenComparing(GradSpecialProgramRule::getRuleCode));   
    	}
        return programRuleList;
	}
	*/
}
